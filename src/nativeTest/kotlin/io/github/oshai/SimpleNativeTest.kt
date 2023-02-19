package io.github.oshai

import kotlin.test.Test
import kotlin.test.assertEquals

private val logger = KotlinLogging.logger {}

class SimpleNativeTest {

  @Test
  fun simpleNativeTest() {
    val appender = createAppender()
    KotlinLoggingConfiguration.appender = appender
    assertEquals("SimpleNativeTest", logger.name)
    logger.info { "info msg" }
    assertEquals("INFO: [SimpleNativeTest] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
    assertEquals("info", appender.lastLoggerName)
    KotlinLoggingConfiguration.appender = ConsoleOutputAppender
  }

  private fun createAppender(): SimpleAppender = SimpleAppender()

  class SimpleAppender : Appender {
    var lastMessage: String = "NA"
    var lastLevel: String = "NA"
    var lastLoggerName: String = "NA"

    override fun trace(loggerName: String, message: String) {
      lastMessage = message
      lastLevel = "trace"
      lastLoggerName = loggerName
    }

    override fun debug(loggerName: String, message: String) {
      lastMessage = message
      lastLevel = "debug"
      lastLoggerName = loggerName
    }

    override fun info(loggerName: String, message: String) {
      lastMessage = message
      lastLevel = "info"
      lastLoggerName = loggerName
    }

    override fun warn(loggerName: String, message: String) {
      lastMessage = message
      lastLevel = "warn"
      lastLoggerName = loggerName
    }

    override fun error(loggerName: String, message: String) {
      lastMessage = message
      lastLevel = "error"
      lastLoggerName = loggerName
    }

    override val includePrefix: Boolean
      get() = true
  }
}
