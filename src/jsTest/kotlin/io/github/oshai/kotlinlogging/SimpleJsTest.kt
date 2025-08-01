package io.github.oshai.kotlinlogging

import kotlin.test.*

val topLevelNamedLogger = KotlinLogging.logger("topLevelNamedLogger")
val topLevelLambdaLogger = KotlinLogging.logger {}

class MyClass {
  val classNamedLogger = KotlinLogging.logger("MyClass")
  val classLambdaLogger = KotlinLogging.logger {}
  val classRefLogger = KotlinLogging.logger(this)

  // check with non default "Companion" name also
  companion object MyCompanion {
    val companionNamedLogger = KotlinLogging.logger("MyClassCompanion")
    val companionLambdaLogger = KotlinLogging.logger {}
    val companionRefLogger = KotlinLogging.logger(this)
  }
}

@Suppress("DEPRECATION")
class SimpleJsTest {
  private lateinit var appender: SimpleAppender

  @BeforeTest
  fun setup() {
    appender = createAppender()
    KotlinLoggingConfiguration.APPENDER = appender
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.APPENDER = ConsoleOutputAppender()
    KotlinLoggingConfiguration.LOG_LEVEL = Level.INFO
  }

  // TODO: use parameterized test?

  // TopLevelNamedLogger
  @Test
  fun checkTopLevelNamedLoggerName() {
    checkLoggerName(topLevelNamedLogger, "topLevelNamedLogger")
  }

  @Test
  fun checkTopLevelNamedLoggerInfoMessage() {
    checkLoggerInfoMessage(topLevelNamedLogger)
  }

  @Test
  fun checkTopLevelNamedLoggerErrorMessage() {
    checkLoggerErrorMessage(topLevelNamedLogger)
  }

  @Test
  fun checkTopLevelNamedLoggerOffLevel() {
    checkLoggerOffLevel(topLevelNamedLogger)
  }

  // TopLevelLambdaLogger
  @Test
  fun checkTopLevelLambdaLoggerName() {
    checkLoggerName(topLevelLambdaLogger, "SimpleJsTest")
  }

  @Test
  fun checkTopLevelLambdaLoggerInfoMessage() {
    checkLoggerInfoMessage(topLevelLambdaLogger)
  }

  @Test
  fun checkTopLevelLambdaLoggerErrorMessage() {
    checkLoggerErrorMessage(topLevelLambdaLogger)
  }

  @Test
  fun checkTopLevelLambdaLoggerOffLevel() {
    checkLoggerOffLevel(topLevelLambdaLogger)
  }

  // ClassNamedLogger
  @Test
  fun checkClassNamedLoggerName() {
    checkLoggerName(MyClass().classNamedLogger, "MyClass")
  }

  @Test
  fun checkClassNamedLoggerInfoMessage() {
    checkLoggerInfoMessage(MyClass().classNamedLogger)
  }

  @Test
  fun checkClassNamedLoggerErrorMessage() {
    checkLoggerErrorMessage(MyClass().classNamedLogger)
  }

  @Test
  fun checkClassNamedLoggerOffLevel() {
    checkLoggerOffLevel(MyClass().classNamedLogger)
  }

  // ClassLambdaLogger
  @Test
  fun checkClassLambdaLoggerName() {
    checkLoggerName(MyClass().classLambdaLogger, "MyClass")
  }

  @Test
  fun checkClassLambdaLoggerInfoMessage() {
    checkLoggerInfoMessage(MyClass().classLambdaLogger)
  }

  @Test
  fun checkClassLambdaLoggerErrorMessage() {
    checkLoggerErrorMessage(MyClass().classLambdaLogger)
  }

  @Test
  fun checkClassLambdaLoggerOffLevel() {
    checkLoggerOffLevel(MyClass().classLambdaLogger)
  }

  // ClassRefLogger
  @Test
  fun checkClassRefLoggerName() {
    checkLoggerName(MyClass().classRefLogger, "MyClass")
  }

  @Test
  fun checkClassRefLoggerInfoMessage() {
    checkLoggerInfoMessage(MyClass().classRefLogger)
  }

  @Test
  fun checkClassRefLoggerErrorMessage() {
    checkLoggerErrorMessage(MyClass().classRefLogger)
  }

  @Test
  fun checkClassRefLoggerOffLevel() {
    checkLoggerOffLevel(MyClass().classRefLogger)
  }

  // CompanionNamedLogger
  @Test
  fun checkCompanionNamedLoggerName() {
    checkLoggerName(MyClass.MyCompanion.companionNamedLogger, "MyClassCompanion")
  }

  @Test
  fun checkCompanionNamedLoggerInfoMessage() {
    checkLoggerInfoMessage(MyClass.MyCompanion.companionNamedLogger)
  }

  @Test
  fun checkCompanionNamedLoggerErrorMessage() {
    checkLoggerErrorMessage(MyClass.MyCompanion.companionNamedLogger)
  }

  @Test
  fun checkCompanionNamedLoggerOffLevel() {
    checkLoggerOffLevel(MyClass.MyCompanion.companionNamedLogger)
  }

  // CompanionLambdaLogger
  @Test
  fun checkCompanionLambdaLoggerName() {
    checkLoggerName(MyClass.MyCompanion.companionLambdaLogger, "MyClass")
  }

  @Test
  fun checkCompanionLambdaLoggerInfoMessage() {
    checkLoggerInfoMessage(MyClass.MyCompanion.companionLambdaLogger)
  }

  @Test
  fun checkCompanionLambdaLoggerErrorMessage() {
    checkLoggerErrorMessage(MyClass.MyCompanion.companionLambdaLogger)
  }

  @Test
  fun checkCompanionLambdaLoggerOffLevel() {
    checkLoggerOffLevel(MyClass.MyCompanion.companionLambdaLogger)
  }

  // CompanionRefLogger
  @Test
  fun checkCompanionRefLoggerName() {
    checkLoggerName(MyClass.MyCompanion.companionRefLogger, "MyClass")
  }

  @Test
  fun checkCompanionRefLoggerInfoMessage() {
    checkLoggerInfoMessage(MyClass.MyCompanion.companionRefLogger)
  }

  @Test
  fun checkCompanionRefLoggerErrorMessage() {
    checkLoggerErrorMessage(MyClass.MyCompanion.companionRefLogger)
  }

  @Test
  fun checkCompanionRefLoggerOffLevel() {
    checkLoggerOffLevel(MyClass.MyCompanion.companionRefLogger)
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
    KotlinLoggingConfiguration.LOG_LEVEL = Level.OFF
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
