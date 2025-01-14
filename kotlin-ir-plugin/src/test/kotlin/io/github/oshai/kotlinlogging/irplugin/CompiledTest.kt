package io.github.oshai.kotlinlogging.irplugin

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerFactory
import java.lang.reflect.InvocationTargetException

data class CompiledTest(
  val preparedTest: PreparedTest,
  val classLoader: ClassLoader,
  val expectedExecutionResult: TestExecutionResult,
) : TestLeaf by preparedTest {

  override fun toMarkDownDocument(): String {
    val transformed = prepareTransformed()
    return """
      |### ${preparedTest.testCode.testName} test
      |
      |User code:
      |```kotlin
      |${preparedTest.testCode.sourceCode.text}
      |```

      |${if (preparedTest.testCode.sourceCode.text == transformed?.sourceCode?.text || transformed == null) "Remains as-is" else "Transformed into"}:
      |```kotlin
      |${transformed?.sourceCode?.text ?: preparedTest.testCode.sourceCode.text}
      |```
    """
      .trimMargin()
  }

  fun execute(): ExecutedTest {
    val compiledClass = classLoader.loadClass(preparedTest.testCode.fqClassName)
    var returnedValue: Any? = null
    var thrownException: Throwable? = null

    val appender = configureLogging()
    val instance =
      if (preparedTest.testCode.needsInstance) compiledClass.getDeclaredConstructor().newInstance()
      else null
    try {
      returnedValue = compiledClass.getMethod(preparedTest.testCode.funName).invoke(instance)
    } catch (ite: InvocationTargetException) {
      thrownException = ite.cause
    }

    return ExecutedTest(
      compiledTest = this,
      actualExecutionResult =
        TestExecutionResult(
          returnedValue = returnedValue,
          thrownExceptionToString = thrownException?.toString(),
          loggedEvent = if (appender.events.isNotEmpty()) appender.events.single() else null,
        ),
    )
  }

  private fun configureLogging(): EventCollectingLogbackAppender<ILoggingEvent> {
    System.setProperty("kotlin-logging-to-logback", "true")
    val loggerContext = LogbackLoggerFactory.getLoggerContext()
    loggerContext.reset()
    val appender = EventCollectingLogbackAppender<ILoggingEvent>()
    appender.name = "EventCollectingLogbackAppender"
    appender.context = loggerContext
    appender.start()

    val rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)
    rootLogger.addAppender(appender)
    rootLogger.level = Level.TRACE
    return appender
  }

  fun prepareTransformed(): PreparedTestCode? {
    return preparedTest.prepareTransformed(expectedExecutionResult)
  }
}
