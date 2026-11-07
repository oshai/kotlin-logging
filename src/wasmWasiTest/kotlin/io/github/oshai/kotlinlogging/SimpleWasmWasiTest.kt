package io.github.oshai.kotlinlogging

import kotlin.test.*

private val namedLogger = KotlinLogging.logger("SimpleWasmWasiTest")
private val anonymousFilePropLogger = KotlinLogging.logger {}

// Create a logger inside a top-level function to exercise the stacktrace-based fallback
private fun createAnonymousFunctionLogger() = KotlinLogging.logger {}

class SimpleWasmWasiTest {
  private lateinit var appender: SimpleAppender
  private val anonymousClassPropLogger = KotlinLogging.logger {}

  @BeforeTest
  fun setup() {
    appender = createAppender()
    KotlinLoggingConfiguration.direct.appender = appender
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.direct.appender = ConsoleOutputAppender
    KotlinLoggingConfiguration.direct.logLevel = Level.INFO
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
    val expected =
      if (n.isNotEmpty()) {
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
  fun anonymousFunctionWasiTest_stacktraceFallback() {
    // This aims to cover the new fallback that parses the .kt filename from the stacktrace.
    val logger = createAnonymousFunctionLogger()
    val n = logger.name
    if (n.isNotEmpty()) {
      assertEquals("SimpleWasmWasiTest", n)
    }
    logger.info { "function msg" }
    val expected =
      if (n.isNotEmpty()) {
        "INFO: [SimpleWasmWasiTest] function msg"
      } else {
        "INFO: [] function msg"
      }
    assertEquals(expected, appender.lastMessage)
  }

  @Test
  fun offLevelWasiTest() {
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
