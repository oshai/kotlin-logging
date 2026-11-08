package io.github.oshai.kotlinlogging

import kotlin.test.*

val topLevelNamedLogger = KotlinLogging.logger("topLevelNamedLogger")
val topLevelLambdaLogger = KotlinLogging.logger {}

class MyClass {
  val classNamedLogger = KotlinLogging.logger("MyClass")
  val classLambdaLogger = KotlinLogging.logger {}
  val classPropertyDelegateLogger by KotlinLogging.logger()

  // check with non default "Companion" name also
  companion object MyCompanion {
    val companionNamedLogger = KotlinLogging.logger("MyClassCompanion")
    val companionLambdaLogger = KotlinLogging.logger {}
  }
}

@Suppress("DEPRECATION")
class SimpleJsTest {
  private val loggers = listOf(
    topLevelNamedLogger to "topLevelNamedLogger",
    topLevelLambdaLogger to "SimpleJsTest",
    MyClass().classNamedLogger to "MyClass",
    MyClass().classLambdaLogger to "MyClass",
    MyClass().classPropertyDelegateLogger to "MyClass",
    MyClass.companionNamedLogger to "MyClassCompanion",
    MyClass.companionLambdaLogger to "MyClass"
  )

  private lateinit var appender: SimpleAppender

  @BeforeTest
  fun setup() {
    appender = createAppender()
    KotlinLoggingConfiguration.direct.appender = appender
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.direct.appender = ConsoleOutputAppender()
    KotlinLoggingConfiguration.direct.logLevel = Level.INFO
  }

  @Test
  fun checkLoggerName() {
    loggers.forEach { (logger, name) ->
      checkLoggerName(logger, name)
    }
  }

  @Test
  fun checkLoggerInfoMessage() {
    loggers.forEach { (logger, _) ->
      checkLoggerInfoMessage(logger)
    }
  }

  @Test
  fun checkLoggerErrorMessage() {
    loggers.forEach { (logger, _) ->
      checkLoggerErrorMessage(logger)
    }
  }

  @Test
  fun checkLoggerOffLevel() {
    loggers.forEach { (logger, _) ->
      checkLoggerOffLevel(logger)
    }
  }

  // use cases
  private fun checkLoggerName(logger: KLogger, expected: String) {
    assertEquals(expected, logger.name)
  }

  private fun checkLoggerInfoMessage(logger: KLogger) {
    logger.info { "info msg" }
    assertEquals("INFO: [${logger.name}] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
  }

  private fun checkLoggerErrorMessage(logger: KLogger) {
    val errorLog = "Something Bad Happened"
    val outerMessage = "Outer Message"
    val innerMessage = "Inner Message"
    val throwable = Throwable(message = outerMessage, cause = Throwable(message = innerMessage))
    logger.error(throwable) { errorLog }
    assertEquals(
      "ERROR: [${logger.name}] $errorLog, Caused by: '$outerMessage', Caused by: '$innerMessage'",
      appender.lastMessage,
    )
  }

  private fun checkLoggerOffLevel(logger: KLogger) {
    KotlinLoggingConfiguration.direct.logLevel = Level.OFF
    assertTrue(logger.isLoggingOff())
    logger.error { "error msg" }
    assertEquals("NA", appender.lastMessage)
    assertEquals("NA", appender.lastLevel)
  }

  private fun createAppender(): SimpleAppender = SimpleAppender()

  class SimpleAppender : Appender {
    var lastMessage: String = "NA"
    var lastLevel: String = "NA"

    override fun log(loggingEvent: KLoggingEvent) {
      lastMessage = DefaultMessageFormatter(includePrefix = true).formatMessage(loggingEvent)
      lastLevel = loggingEvent.level.name.toLowerCase()
    }
  }
}
