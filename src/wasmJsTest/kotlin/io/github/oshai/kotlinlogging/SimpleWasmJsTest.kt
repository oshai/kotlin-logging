package io.github.oshai.kotlinlogging

import kotlin.test.*

private val logger = KotlinLogging.logger("SimpleWasmJsTest")

class SimpleWasmJsTest {
  private lateinit var appender: SimpleAppender

  @BeforeTest
  fun setup() {
    appender = createAppender()
    KotlinLoggingConfiguration.appender = appender
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.appender = ConsoleOutputAppender()
    KotlinLoggingConfiguration.logLevel = Level.INFO
  }

  @Test
  fun simpleWasmJsTest() {
    assertEquals("SimpleWasmJsTest", logger.name)
    logger.info { "info msg" }
    assertEquals("INFO: [SimpleWasmJsTest] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
  }

  @Test
  fun offLevelWasmJsTest() {
    KotlinLoggingConfiguration.logLevel = Level.OFF
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
      lastLevel = loggingEvent.level.name.lowercase()
    }
  }
}
