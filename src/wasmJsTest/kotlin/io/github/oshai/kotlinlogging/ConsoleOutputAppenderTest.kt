package io.github.oshai.kotlinlogging

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConsoleOutputAppenderTest {
  private lateinit var defaultLogLevel: Level
  private lateinit var defaultFormatter: Formatter
  private lateinit var defaultAppender: Appender

  private lateinit var testAppender: ConsoleOutputAppender

  @BeforeTest
  fun setup() {
    defaultLogLevel = KotlinLoggingConfiguration.direct.logLevel
    defaultFormatter = KotlinLoggingConfiguration.direct.formatter
    defaultAppender = KotlinLoggingConfiguration.direct.appender

    testAppender = ConsoleOutputAppender()

    KotlinLoggingConfiguration.direct.logLevel = Level.TRACE
    KotlinLoggingConfiguration.direct.formatter = TestFormatter()
    KotlinLoggingConfiguration.direct.appender = testAppender

    setupConsole()
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.direct.logLevel = defaultLogLevel
    KotlinLoggingConfiguration.direct.formatter = defaultFormatter
    KotlinLoggingConfiguration.direct.appender = defaultAppender

    cleanupConsole()
  }

  @Test
  fun logTraceTest() {
    testAppender.log(createTestEvent(Level.TRACE))

    assertEquals(expected = "testing... TRACE", actual = getTestLog())
    assertEquals("", getTestInfo())
    assertEquals("", getTestWarn())
    assertEquals("", getTestError())
  }

  @Test
  fun logDebugTest() {
    testAppender.log(createTestEvent(Level.DEBUG))

    assertEquals(expected = "testing... DEBUG", actual = getTestLog())
    assertEquals("", getTestInfo())
    assertEquals("", getTestWarn())
    assertEquals("", getTestError())
  }

  @Test
  fun logInfoTest() {
    testAppender.log(createTestEvent(Level.INFO))

    assertEquals("", getTestLog())
    assertEquals(expected = "testing... INFO", actual = getTestInfo())
    assertEquals("", getTestWarn())
    assertEquals("", getTestError())
  }

  @Test
  fun logWarnTest() {
    testAppender.log(createTestEvent(Level.WARN))

    assertEquals("", getTestLog())
    assertEquals("", getTestInfo())
    assertEquals(expected = "testing... WARN", actual = getTestWarn())
    assertEquals("", getTestError())
  }

  @Test
  fun logErrorTest() {
    testAppender.log(createTestEvent(Level.ERROR))

    assertEquals("", getTestLog())
    assertEquals("", getTestInfo())
    assertEquals("", getTestWarn())
    assertEquals(expected = "testing... ERROR", actual = getTestError())
  }

  @Test
  fun logOffTest() {
    testAppender.log(createTestEvent(Level.OFF))

    assertEquals("", getTestLog())
    assertEquals("", getTestInfo())
    assertEquals("", getTestWarn())
    assertEquals("", getTestError())
  }

  class TestFormatter : Formatter {
    override fun formatMessage(loggingEvent: KLoggingEvent): String =
      "testing... ${loggingEvent.level}"
  }

  private fun createTestEvent(level: Level) =
    KLoggingEvent(
      level = level,
      marker = null,
      loggerName = "test logger",
      message = "test message",
      cause = null,
      payload = null,
    )
}

// Access intercepted console.* test messages

private fun getTestLog(): String = js("""window.__testLog.toString()""")

private fun getTestInfo(): String = js("""window.__testInfo.toString()""")

private fun getTestWarn(): String = js("""window.__testWarn.toString()""")

private fun getTestError(): String = js("""window.__testError.toString()""")

private fun setupConsole() {
  js(
    """
    {
      // Save standard console.*
      window.__stdLog = console.log;
      window.__stdInfo = console.info;
      window.__stdWarn = console.warn;
      window.__stdError = console.error;

      // Define list containers for the intercepted messages
      window.__testLog = [];
      window.__testInfo = [];
      window.__testWarn = [];
      window.__testError = [];

      // Intercept console.* calls and
      // save all intercepted messages to respectful list containers
      console.log = function (msg) {
        window.__testLog.push(msg);
        window.__stdLog.apply(console, arguments);
      };
      console.info = function (msg) {
        window.__testInfo.push(msg);
        window.__stdInfo.apply(console, arguments);
      };
      console.warn = function (msg) {
        window.__testWarn.push(msg);
        window.__stdWarn.apply(console, arguments);
      };
      console.error = function (msg) {
        window.__testError.push(msg);
        window.__stdError.apply(console, arguments);
      };
    }"""
  )
}

private fun cleanupConsole() {
  js(
    """
    {
      // Reset console.*
      console.log = window.__stdLog;
      console.info = window.__stdInfo;
      console.warn = window.__stdWarn;
      console.error = window.__stdError;

      // Clear list containers
      window.__testLog = [];
      window.__testInfo = [];
      window.__testWarn = [];
      window.__testError = [];
    }"""
  )
}
