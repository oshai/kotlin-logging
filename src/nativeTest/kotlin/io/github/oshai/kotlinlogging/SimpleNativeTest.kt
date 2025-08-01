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
    assertEquals("SimpleNativeTest", logger.name)
    logger.info { "info msg" }
    assertEquals("INFO: [SimpleNativeTest] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
    assertEquals("info", appender.lastLoggerName)
  }

  @Test
  fun offLevelNativeTest() {
    KotlinLoggingConfiguration.logLevel = Level.OFF
    assertTrue(logger.isLoggingOff())
    logger.error { "error msg" }
    assertEquals("NA", appender.lastMessage)
    assertEquals("NA", appender.lastLevel)
    assertEquals("NA", appender.lastLoggerName)
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
