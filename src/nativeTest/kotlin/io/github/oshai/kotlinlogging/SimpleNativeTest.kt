package io.github.oshai.kotlinlogging

import kotlin.test.*

private val logger = KotlinLogging.logger {}

class SimpleNativeTest {
  private lateinit var appender: SimpleAppender

  @BeforeTest
  fun setup() {
    appender = createAppender()
    KotlinLoggingConfiguration.direct.appender = appender
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.direct.appender = DefaultAppender
    KotlinLoggingConfiguration.direct.logLevel = Level.INFO
  }

  @Test
  fun simpleNativeTest() {
    println("Asserting logger name: Expected 'SimpleNativeTest', was '${logger.name}'")
    assertEquals(
      "io.github.oshai.kotlinlogging.SimpleNativeTest",
      logger.name,
      "Expected logger name to be 'io.github.oshai.kotlinlogging.SimpleNativeTest', was '${logger.name}'",
    )
    logger.info { "info msg" }
    println("Asserting last message: Expected 'info msg', was '${appender.lastMessage}'")
    assertEquals(
      "info msg",
      appender.lastMessage,
      "Expected last message to be 'info msg', was '${appender.lastMessage}'",
    )
    println("Asserting last level: Expected 'info', was '${appender.lastLevel}'")
    assertEquals(
      "info",
      appender.lastLevel,
      "Expected last level to be 'info', was '${appender.lastLevel}'",
    )
    println(
      "Asserting last logger name: Expected 'io.github.oshai.kotlinlogging.SimpleNativeTest', was '${appender.lastLoggerName}'"
    )
    assertEquals(
      "io.github.oshai.kotlinlogging.SimpleNativeTest",
      appender.lastLoggerName,
      "Expected last logger name to be 'io.github.oshai.kotlinlogging.SimpleNativeTest', was '${appender.lastLoggerName}'",
    )
  }

  @Test
  fun offLevelNativeTest() {
    KotlinLoggingConfiguration.direct.logLevel = Level.OFF
    val isLoggingOff = logger.isLoggingOff()
    println("Asserting logging is off: Expected true, was '$isLoggingOff'")
    assertTrue(isLoggingOff, "Expected logging to be off, was '$isLoggingOff'")
    logger.error { "error msg" }
    println("Asserting last message is 'NA': Expected 'NA', was '${appender.lastMessage}'")
    assertEquals(
      "NA",
      appender.lastMessage,
      "Expected last message to be 'NA' when logging is off, was '${appender.lastMessage}'",
    )
    println("Asserting last level is 'NA': Expected 'NA', was '${appender.lastLevel}'")
    assertEquals(
      "NA",
      appender.lastLevel,
      "Expected last level to be 'NA' when logging is off, was '${appender.lastLevel}'",
    )
    println("Asserting last logger name is 'NA': Expected 'NA', was '${appender.lastLoggerName}'")
    assertEquals(
      "NA",
      appender.lastLoggerName,
      "Expected last logger name to be 'NA' when logging is off, was '${appender.lastLoggerName}'",
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
