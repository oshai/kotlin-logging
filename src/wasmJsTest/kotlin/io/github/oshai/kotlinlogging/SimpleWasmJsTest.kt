package io.github.oshai.kotlinlogging

import kotlin.test.*

private val namedLogger = KotlinLogging.logger("SimpleWasmJsTest")
private val anonymousFilePropLogger = KotlinLogging.logger {}

class SimpleWasmJsTest {
  private lateinit var appender: SimpleAppender
  private val anonymousClassPropLogger = KotlinLogging.logger {}

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
  fun simpleWasmJsTest() {
    assertEquals("SimpleWasmJsTest", namedLogger.name)
    namedLogger.info { "info msg" }
    assertEquals("INFO: [SimpleWasmJsTest] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
  }

  @Test
  fun anonymousFilePropWasmJsTest() {
    assertEquals("SimpleWasmJsTest", anonymousFilePropLogger.name)
    anonymousFilePropLogger.info { "info msg" }
    assertEquals("INFO: [SimpleWasmJsTest] info msg", appender.lastMessage)
  }

  @Test
  fun anonymousClassPropWasmJsTest() {
    assertEquals("SimpleWasmJsTest", anonymousClassPropLogger.name)
    anonymousFilePropLogger.info { "info msg" }
    assertEquals("INFO: [SimpleWasmJsTest] info msg", appender.lastMessage)
  }

  @Test
  fun offLevelWasmJsTest() {
    KotlinLoggingConfiguration.direct.logLevel = Level.OFF
    assertTrue(namedLogger.isLoggingOff())
    namedLogger.error { "error msg" }
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
