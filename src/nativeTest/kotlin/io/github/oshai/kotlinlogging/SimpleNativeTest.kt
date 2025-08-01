package io.github.oshai.kotlinlogging

import kotlin.test.*

private val logger = KotlinLogging.logger {}

class SimpleNativeTest {
  private lateinit var appender: SimpleAppender

  @BeforeTest
  fun setup() {
    appender = createAppender()
    KotlinLoggingConfiguration.appender = appender
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.appender = ConsoleOutputAppender
    KotlinLoggingConfiguration.logLevel = Level.INFO
  }

  @Test
  fun simpleNativeTest() {
    assertEquals(
      "SimpleNativeTest",
      logger.name,
      "Expected logger name to be 'SimpleNativeTest', but was '${logger.name}'",
    )
    logger.info { "info msg" }
    assertEquals(
      "info msg",
      appender.lastMessage,
      "Expected last message to be 'info msg', but was '${appender.lastMessage}'",
    )
    assertEquals(
      "info",
      appender.lastLevel,
      "Expected last level to be 'info', but was '${appender.lastLevel}'",
    )
    assertEquals(
      "SimpleNativeTest",
      appender.lastLoggerName,
      "Expected last logger name to be 'SimpleNativeTest', but was '${appender.lastLoggerName}'",
    )
  }

  @Test
  fun offLevelNativeTest() {
    KotlinLoggingConfiguration.logLevel = Level.OFF
    val isLoggingOff = logger.isLoggingOff()
    assertTrue(isLoggingOff, "Expected logging to be off, but was '$isLoggingOff'")
    logger.error { "error msg" }
    assertEquals(
      "NA",
      appender.lastMessage,
      "Expected last message to be 'NA' when logging is off, but was '${appender.lastMessage}'",
    )
    assertEquals(
      "NA",
      appender.lastLevel,
      "Expected last level to be 'NA' when logging is off, but was '${appender.lastLevel}'",
    )
    assertEquals(
      "NA",
      appender.lastLoggerName,
      "Expected last logger name to be 'NA' when logging is off, but was '${appender.lastLoggerName}'",
    )
  }

  private fun createAppender(): SimpleAppender = SimpleAppender()

  class SimpleAppender : Appender {
    var lastMessage: String? = "NA"
    var lastLevel: String = "NA"
    var lastLoggerName: String = "NA"

    override fun log(loggingEvent: KLoggingEvent) {
      lastMessage = loggingEvent.message
      lastLevel = loggingEvent.level.name.lowercase()
      lastLoggerName = loggingEvent.loggerName
    }
  }
}
