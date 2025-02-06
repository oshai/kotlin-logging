package io.github.oshai.kotlinlogging.irplugin

import java.io.BufferedReader
import java.io.StringReader
import java.util.stream.Collectors
import kotlin.reflect.KClass
import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly

data class TestDefinition(
  override val skip: Boolean,
  val codeDescription: TestCodeDescription,
  val expectedResult: TestExecutionResult,
) : TestLeaf {

  fun prepare(uniqueTestNumber: Int): PreparedTest {
    return PreparedTest(
      definition = this,
      uniqueTestNumber = uniqueTestNumber,
      testCode = codeDescription.prepare(uniqueTestNumber),
    )
  }
}

class TestDefinitionBuilder {
  var skip: Boolean? = null
  private var codeDescription: TestCodeDescription? = null
  private var expectedResult: TestExecutionResult? = null

  fun code(init: TestCodeDescriptionBuilder.() -> Unit) {
    codeDescription = TestCodeDescriptionBuilder().apply(init).build()
  }

  fun expect(init: TestExecutionResultBuilder.() -> Unit) {
    expectedResult = TestExecutionResultBuilder().apply(init).build()
  }

  fun build(): TestDefinition {
    return TestDefinition(
      skip = skip ?: false,
      codeDescription = codeDescription!!,
      expectedResult = expectedResult!!,
    )
  }
}

data class TestCodeDescription(
  val useClass: Boolean,
  val useThrowable: Boolean,
  val useMarker: Boolean,
  val funName: String = "main",
  val funReturnType: KClass<*>?,
  val initCode: String,
  val logStatement: LogStatement,
  val throwReturnValueFromLogStatement: Boolean,
  val extraMethodCode: String,
) {
  fun prepare(uniqueTestNumber: Int) =
    prepare(uniqueTestNumber) { logStatement.makeSource(useMarker, useThrowable) }

  fun prepareTransformed(uniqueTestNumber: Int, expectedResult: TestExecutionResult) =
    if (expectedResult.loggedEvent != null)
      prepare(uniqueTestNumber) { logStatement.makeTransformedSource(expectedResult) }
    else null

  private fun prepare(
    uniqueTestNumber: Int,
    logStatementSourceCodeMaker: () -> String,
  ): PreparedTestCode {
    val fileName = "test${uniqueTestNumber}.kt"
    val packageName = "test${uniqueTestNumber}"
    val className: String
    val classDeclareStart: String
    val classDeclareEnd: String
    val needsInstance: Boolean
    val classIndent: String
    if (useClass) {
      className = "MainTest"
      classDeclareStart = "public class $className {"
      classDeclareEnd = "}"
      needsInstance = true
      classIndent = "  "
    } else {
      className = fileName.capitalizeAsciiOnly().substringBefore(".kt") + "Kt"
      classDeclareStart = ""
      classDeclareEnd = ""
      needsInstance = false
      classIndent = ""
    }
    // line number depends on the generated source code template (see below)
    val logStatementLineNumber = 10
    val fqClassName = "$packageName.$className"
    val initMarkerSourceCode =
      if (useMarker) "val $MARKER_VARIABLE_NAME = MyMarker(\"markerName\")" else ""
    val declareMarkerSourceCode =
      if (useMarker)
        "class MyMarker(private val name: String): Marker { override fun getName() = name }"
      else ""
    val initThrowableSourceCode =
      if (useThrowable) "val $THROWABLE_VARIABLE_NAME = Exception(\"$EXCEPTION_MESSAGE\")" else ""
    val logStatementPrefix: String
    val funReturnTypeSuffix: String
    if (funReturnType != null) {
      logStatementPrefix = "return "
      funReturnTypeSuffix = ": " + funReturnType.qualifiedName?.removePrefix("kotlin.")
    } else {
      funReturnTypeSuffix = ""
      if (throwReturnValueFromLogStatement) {
        logStatementPrefix = "throw "
      } else {
        logStatementPrefix = ""
      }
    }
    val logStatementSourceCode = logStatementSourceCodeMaker()
    val fullSourceCode =
      """
              package $packageName
              import io.github.oshai.kotlinlogging.*
              
              $classDeclareStart
              ${classIndent}fun ${funName}()$funReturnTypeSuffix {
              ${classIndent}  val logger = KotlinLogging.logger {}
              ${classIndent}  $initMarkerSourceCode
              ${classIndent}  $initThrowableSourceCode
              ${classIndent}  $initCode
              ${classIndent}  ${logStatementPrefix}logger.$logStatementSourceCode
              ${classIndent}}
              ${classIndent}$extraMethodCode
              $classDeclareEnd
              $declareMarkerSourceCode
        """
        .trimIndent()
        .trim()
    return PreparedTestCode(
      testName =
        " $logStatementSourceCode at $className.${funName}($fileName:$logStatementLineNumber)",
      fileName = fileName,
      packageName = packageName,
      className = className,
      classDeclareStart = classDeclareStart,
      classDeclareEnd = classDeclareEnd,
      fqClassName = fqClassName,
      funName = funName,
      needsInstance = needsInstance,
      logStatementLineNumber = logStatementLineNumber,
      sourceCode = PreparedTest.SourceCode(fileName, fullSourceCode),
      sourceCodeForDebugging = stringWithLineNumbers(fullSourceCode),
    )
  }

  private fun stringWithLineNumbers(source: String): String {
    val buffer = BufferedReader(StringReader(source))
    var lineNumber = 1
    return buffer.lines().map { line -> "${lineNumber++}: $line" }.collect(Collectors.joining("\n"))
  }
}

private fun LogStatement.makeSource(useMarker: Boolean, useThrowable: Boolean): String {
  return "${funName}${makeArgumentList(useMarker, useThrowable)}${makeLastArgumentLambda(useThrowable)}"
}

private fun LogStatement.makeTransformedSource(expectedResult: TestExecutionResult): String {
  val loggedEvent: TestLoggingEvent = expectedResult.loggedEvent!!
  val useMarker = loggedEvent.hasMarker
  val useThrowable = loggedEvent.hasThrowable
  val compilerDataValues = makeCompilerDataValues(loggedEvent)
  if (compilerDataValues.isEmpty()) {
    return makeSource(useMarker, useThrowable)
  }

  if (funName in listOf("entry", "exit", "throwing", "catching")) {
    val arguments = mutableListOf<String>()
    arguments.add(
      "KLoggingEventBuilder.InternalCompilerData(${compilerDataValues.joinToString(", ")})"
    )
    if (useThrowable) arguments.add(THROWABLE_VARIABLE_NAME)
    return "${funName}WithCompilerData(${arguments.joinToString(", ")})"
  } else {
    val arguments = mutableListOf<String>()
    arguments.add("Level.${loggedEvent.level.name}")
    if (useMarker) arguments.add(MARKER_VARIABLE_NAME)

    val lambdaValues = mutableListOf<String>()
    lambdaValues.add("message = \"${loggedEvent.formattedMessage}\"")
    if (useThrowable) lambdaValues.add("cause = $THROWABLE_VARIABLE_NAME")
    lambdaValues.add(
      "internalCompilerData = KLoggingEventBuilder.InternalCompilerData(${compilerDataValues.joinToString(", ")})"
    )

    return "at(${arguments.joinToString(", ")}) { ${lambdaValues.joinToString("; ")}"
  }
}

private fun makeCompilerDataValues(loggedEvent: TestLoggingEvent): MutableList<String> {
  val compilerDataValues = mutableListOf<String>()
  if (loggedEvent.message != loggedEvent.formattedMessage) {
    compilerDataValues.add("messageTemplate = \"${loggedEvent.message}\"")
  }
  val callerDataFirstElement = loggedEvent.callerDataFirstElement
  if (callerDataFirstElement != null) {
    compilerDataValues.add("className = \"${callerDataFirstElement.className}\"")
    compilerDataValues.add("methodName = \"${callerDataFirstElement.methodName}\"")
    compilerDataValues.add("fileName = \"${callerDataFirstElement.fileName}\"")
    compilerDataValues.add("lineNumber = ${callerDataFirstElement.lineNumber}")
  }
  return compilerDataValues
}

private fun LogStatement.makeArgumentList(useMarker: Boolean, useThrowable: Boolean): String {
  val arguments =
    arguments
      .map {
        when (it) {
          MARKER_PLACEHOLDER -> if (useMarker) MARKER_VARIABLE_NAME else ""
          THROWABLE_PLACEHOLDER -> if (useThrowable) THROWABLE_VARIABLE_NAME else ""
          else -> it
        }
      }
      .filter { it.isNotEmpty() }
  return "(${arguments.joinToString(", ")})"
}

private fun LogStatement.makeLastArgumentLambda(useThrowable: Boolean): String {
  val throwableValue = if (useThrowable) THROWABLE_VARIABLE_NAME else "null"
  return if (lastArgumentLambda != null)
    " {${lastArgumentLambda.replace(THROWABLE_PLACEHOLDER, throwableValue)}}"
  else ""
}

class TestCodeDescriptionBuilder {
  var useClass: Boolean? = null
  var useThrowable: Boolean? = null
  var useMarker: Boolean? = null
  var initCode: String = ""
  var funReturnType: KClass<*>? = null
  var logStatement: LogStatement? = null
  var throwReturnValueFromLogStatement: Boolean? = null
  var extraMethodCode: String = ""

  fun build(): TestCodeDescription {
    return TestCodeDescription(
      useClass = useClass ?: false,
      useThrowable = useThrowable ?: false,
      useMarker = useMarker ?: false,
      initCode = initCode,
      funReturnType = funReturnType,
      logStatement = logStatement!!,
      throwReturnValueFromLogStatement = throwReturnValueFromLogStatement ?: false,
      extraMethodCode = extraMethodCode,
    )
  }
}
