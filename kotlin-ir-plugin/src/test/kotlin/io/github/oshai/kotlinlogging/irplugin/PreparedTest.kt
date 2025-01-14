package io.github.oshai.kotlinlogging.irplugin

import io.github.oshai.kotlinlogging.irplugin.PreparedTest.SourceCode

data class PreparedTest(
  val definition: TestDefinition,
  val uniqueTestNumber: Int,
  val testCode: PreparedTestCode,
) : TestLeaf by definition {

  data class SourceCode(val fileName: String, val text: String)

  fun compiled(
    classLoader: ClassLoader,
    expectationAdjusters: List<TestExecutionResultBuilder.() -> Unit>,
  ): CompiledTest {
    return CompiledTest(
      preparedTest = this,
      classLoader = classLoader,
      expectedExecutionResult =
        adjustExpectations(
          expectationAdjusters,
          definition.toExpectedTestExecutionResult(makeExpectedStackTraceElement()),
        ),
    )
  }

  private fun adjustExpectations(
    expectationAdjusters: List<TestExecutionResultBuilder.() -> Unit>,
    originalExpectedTestExecutionResult: TestExecutionResult,
  ): TestExecutionResult {
    val result = TestExecutionResultBuilder(originalExpectedTestExecutionResult)
    for (adjuster in expectationAdjusters) {
      adjuster.invoke(result)
    }
    return result.build()
  }

  private fun makeExpectedStackTraceElement(): StackTraceElement {
    return StackTraceElement(
      testCode.fqClassName,
      testCode.funName,
      testCode.fileName,
      testCode.logStatementLineNumber,
    )
  }

  fun prepareTransformed(expectedExecutionResult: TestExecutionResult): PreparedTestCode? {
    return definition.codeDescription.prepareTransformed(uniqueTestNumber, expectedExecutionResult)
  }
}

data class PreparedTestCode(
  val testName: String,
  val fileName: String,
  val packageName: String,
  val className: String,
  val classDeclareStart: String,
  val classDeclareEnd: String,
  val fqClassName: String,
  val funName: String,
  val logStatementLineNumber: Int,
  val needsInstance: Boolean,
  val sourceCode: SourceCode,
  val sourceCodeForDebugging: String = "",
)
