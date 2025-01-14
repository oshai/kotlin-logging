package io.github.oshai.kotlinlogging.irplugin

data class TestExecutionResult(
  val returnedValue: Any? = null,
  val thrownExceptionToString: String? = null,
  val loggedEvent: TestLoggingEvent? = null,
)

class TestExecutionResultBuilder(sourceTestExecutionResult: TestExecutionResult? = null) {
  var returnedValue: Any? = sourceTestExecutionResult?.returnedValue
  var thrownExceptionToString: String? = sourceTestExecutionResult?.thrownExceptionToString
  private var loggedEvent: TestLoggingEvent? = sourceTestExecutionResult?.loggedEvent

  fun loggedEvent(block: (TestLoggingEventBuilder.() -> Unit)?) {
    loggedEvent =
      if (block != null) TestLoggingEventBuilder(loggedEvent).apply(block).build() else null
  }

  fun build(): TestExecutionResult {
    return TestExecutionResult(
      returnedValue = returnedValue,
      thrownExceptionToString = thrownExceptionToString,
      loggedEvent = loggedEvent,
    )
  }
}

fun TestDefinition.toExpectedTestExecutionResult(
  expectedStackTraceElement: StackTraceElement?
): TestExecutionResult {
  return expectedResult.copy(
    loggedEvent =
      expectedResult.loggedEvent?.copy(callerDataFirstElement = expectedStackTraceElement)
  )
}

data class TestLoggingEvent(
  val level: TestLoggingLevel,
  val message: String,
  val formattedMessage: String,
  val hasMarker: Boolean,
  val hasThrowable: Boolean,
  val callerDataFirstElement: StackTraceElement?,
)

class TestLoggingEventBuilder(sourceEvent: TestLoggingEvent? = null) {
  var level: TestLoggingLevel? = sourceEvent?.level
  var message: String? = sourceEvent?.message
  var formattedMessage: String? = sourceEvent?.formattedMessage
  var hasMarker: Boolean? = sourceEvent?.hasMarker
  var hasThrowable: Boolean? = sourceEvent?.hasThrowable
  var callerDataFirstElement: StackTraceElement? = sourceEvent?.callerDataFirstElement

  fun build(): TestLoggingEvent {
    return TestLoggingEvent(
      level = level!!,
      message = message!!,
      formattedMessage = formattedMessage!!,
      hasMarker = hasMarker!!,
      hasThrowable = hasThrowable!!,
      callerDataFirstElement = callerDataFirstElement,
    )
  }
}
