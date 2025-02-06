package io.github.oshai.kotlinlogging.irplugin

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import kotlin.enums.EnumEntries
import kotlin.test.assertEquals
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
fun compileTests(
  featureFlagsToUse: EnumEntries<FeatureFlag>,
  preparedTests: TestCollection<PreparedTest>,
): List<FeatureFlagCompilationResult> {
  val sources = preparedTests.flatMapAndExtract { it.testCode.sourceCode }
  return featureFlagsToUse.map { featureFlag ->
    val result =
      compile(sources.map { SourceFile.kotlin(it.fileName, it.text) }, featureFlag.configurer)
    if (result.exitCode != KotlinCompilation.ExitCode.OK) {
      preparedTests.forEach(
        collectionVisitor = { indent, collection -> println(indent + collection.name) },
        testVisitor = { indent, test ->
          println("${indent}Source file: ${test.testCode.fileName}")
          println("${indent}Test definition: ${test.definition}")
          println(indent + test.testCode.sourceCode.text)
          println()
        },
      )
      println(result.messages)
    }
    assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    FeatureFlagCompilationResult(
      featureFlag = featureFlag,
      compilationResult = result,
      compiledTests =
        preparedTests.mapWithExpectationAdjusters(
          featureFlag,
          listOf(featureFlag.expectationAdjuster),
        ) { expectationAdjusters, preparedTest ->
          preparedTest.compiled(result.classLoader, expectationAdjusters)
        },
    )
  }
}

@OptIn(ExperimentalCompilerApi::class)
private fun compile(
  sourceFiles: List<SourceFile>,
  pluginConfigFactory: () -> KotlinLoggingIrPluginConfig,
): JvmCompilationResult {
  return KotlinCompilation()
    .apply {
      sources = sourceFiles
      compilerPluginRegistrars =
        listOf(KotlinLoggingCompilerRegistrar(pluginConfigFactory.invoke()))
      inheritClassPath = true
    }
    .compile()
}

data class FeatureFlagCompilationResult
@OptIn(ExperimentalCompilerApi::class)
constructor(
  val featureFlag: FeatureFlag,
  val compilationResult: JvmCompilationResult,
  val compiledTests: TestCollection<CompiledTest>,
)
