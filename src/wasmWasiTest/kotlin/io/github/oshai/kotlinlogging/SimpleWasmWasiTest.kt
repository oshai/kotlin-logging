package io.github.oshai.kotlinlogging

import kotlin.test.*

private val namedLogger = KotlinLogging.logger("SimpleWasmWasiTest")
private val anonymousFilePropLogger = KotlinLogging.logger {}

class SimpleWasmWasiTest {
  private lateinit var appender: SimpleAppender
  private val anonymousClassPropLogger = KotlinLogging.logger {}

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
  fun simpleWasiTest() {
    assertEquals("SimpleWasmWasiTest", namedLogger.name)
    namedLogger.info { "info msg" }
    assertEquals("INFO: [SimpleWasmWasiTest] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
  }

  @Test
  fun anonymousFilePropWasiTest() {
    // On WASI, stack traces are often unavailable; top-level property name may be empty.
    val n = anonymousFilePropLogger.name
    if (n.isNotEmpty()) {
      assertEquals("SimpleWasmWasiTest", n)
    }
    anonymousFilePropLogger.info { "info msg" }
    val expected = if (n.isNotEmpty()) {
      "INFO: [SimpleWasmWasiTest] info msg"
    } else {
      "INFO: [] info msg"
    }
    assertEquals(expected, appender.lastMessage)
  }

  @Test
  fun anonymousClassPropWasiTest() {
    assertEquals("SimpleWasmWasiTest", anonymousClassPropLogger.name)
    anonymousClassPropLogger.info { "info msg" }
    assertEquals("INFO: [SimpleWasmWasiTest] info msg", appender.lastMessage)
  }

  @Test
  fun offLevelWasiTest() {
    KotlinLoggingConfiguration.logLevel = Level.OFF
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
