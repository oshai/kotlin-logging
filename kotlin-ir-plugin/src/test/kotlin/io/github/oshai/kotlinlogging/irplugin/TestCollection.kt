package io.github.oshai.kotlinlogging.irplugin

import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicTest

data class TestCollection<T : TestLeaf>(
  val name: String,
  private val childCollections: List<TestCollection<T>>,
  private val tests: List<T>,
  private var expectationAdjustersPerTest: List<FeatureFlagExpectationAdjuster>,
) {

  fun <TT : TestLeaf> map(mapper: (T) -> TT): TestCollection<TT> {
    return TestCollection(
      name,
      childCollections.map { it.map(mapper) },
      tests.map { mapper(it) },
      expectationAdjustersPerTest,
    )
  }

  fun <TT : TestLeaf> mapWithExpectationAdjusters(
    featureFlag: FeatureFlag,
    parentExpectationAdjusters: List<TestExecutionResultBuilder.() -> Unit>,
    mapper: (List<TestExecutionResultBuilder.() -> Unit>, T) -> TT,
  ): TestCollection<TT> {
    val expectationAdjusters =
      parentExpectationAdjusters +
        expectationAdjustersPerTest
          .filter { it.applicableFeatureFlags.contains(featureFlag) }
          .map { it.expectationAdjuster }
    return TestCollection(
      name,
      childCollections.map {
        it.mapWithExpectationAdjusters(featureFlag, expectationAdjusters, mapper)
      },
      tests.map { mapper(expectationAdjusters, it) },
      expectationAdjustersPerTest,
    )
  }

  fun forEach(
    indent: String = "",
    collectionVisitor: (indent: String, TestCollection<T>) -> Unit,
    testVisitor: (indent: String, T) -> Unit,
  ) {
    collectionVisitor(indent, this)
    tests.forEach { testVisitor(indent, it) }
    childCollections.forEach { it.forEach("  $indent", collectionVisitor, testVisitor) }
  }

  fun <M> flatMapAndExtract(mapper: (T) -> M): List<M> {
    return childCollections.flatMap { it.flatMapAndExtract(mapper) } + tests.map(mapper)
  }

  fun toDynamicTests(testMaker: T.() -> DynamicTest): DynamicContainer {
    val children =
      childCollections.map { it.toDynamicTests(testMaker) } +
        tests.filter { !it.skip }.map { it.testMaker() }
    return DynamicContainer.dynamicContainer(name, children)
  }

  fun toMarkDownDocument(title: String): String {
    val children =
      childCollections.map { it.toMarkDownDocument(it.name) } +
        tests.filter { !it.skip }.map { it.toMarkDownDocument() }
    return "<details><summary><b>$title</b></summary>\n\n${children.joinToString("\n\n")}\n\n</details>"
  }
}

interface TestLeaf {
  val skip: Boolean

  fun toMarkDownDocument(): String {
    TODO("Not implemented")
  }
}

fun rootCollection(init: TestCollectionBuilder.() -> Unit) =
  TestCollectionBuilder().apply(init).build()

class TestCollectionBuilder {
  var name: String? = null
  private var childCollections: MutableList<TestCollection<TestDefinition>> = mutableListOf()
  private var tests: MutableList<TestDefinition> = mutableListOf()
  private var expectationAdjustersPerTest: MutableList<FeatureFlagExpectationAdjuster> =
    mutableListOf()

  fun collection(init: TestCollectionBuilder.() -> Unit) {
    val element = TestCollectionBuilder().apply(init).build()
    childCollections.add(element)
  }

  fun test(init: TestDefinitionBuilder.() -> Unit) {
    tests.add(TestDefinitionBuilder().apply(init).build())
  }

  fun featureFlagExpectationAdjuster(init: FeatureFlagExpectationAdjusterBuilder.() -> Unit) {
    expectationAdjustersPerTest.add(FeatureFlagExpectationAdjusterBuilder().apply(init).build())
  }

  fun build() = TestCollection(name!!, childCollections, tests, expectationAdjustersPerTest)
}
