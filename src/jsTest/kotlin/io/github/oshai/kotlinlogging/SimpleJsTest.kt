package io.github.oshai.kotlinlogging

import kotlin.test.Test
import kotlin.test.assertEquals

private val logger = KotlinLogging.logger("SimpleJsTest")

class SimpleJsTest {

  @Test
  fun simpleJsTest() {
    val appender = createAppender()
    KotlinLoggingConfiguration.APPENDER = appender
    assertEquals("SimpleJsTest", logger.name)
    logger.info { "info msg" }
    assertEquals("INFO: [SimpleJsTest] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
    KotlinLoggingConfiguration.APPENDER = ConsoleOutputAppender
  }

  @Test
  fun loggerNameTest() {
    assertEquals("MyClass", MyClass().logger2.name)
  }

  private fun createAppender(): SimpleAppender = SimpleAppender()

  class SimpleAppender : Appender {
    var lastMessage: String = "NA"
    var lastLevel: String = "NA"

    override fun trace(message: Any?) {
      lastMessage = message.toString()
      lastLevel = "trace"
    }

    override fun debug(message: Any?) {
      lastMessage = message.toString()
      lastLevel = "debug"
    }

    override fun info(message: Any?) {
      lastMessage = message.toString()
      lastLevel = "info"
    }

    override fun warn(message: Any?) {
      lastMessage = message.toString()
      lastLevel = "warn"
    }

    override fun error(message: Any?) {
      lastMessage = message.toString()
      lastLevel = "error"
    }
  }
}

class MyClass {
  val logger2 by KotlinLogging.logger()
}
