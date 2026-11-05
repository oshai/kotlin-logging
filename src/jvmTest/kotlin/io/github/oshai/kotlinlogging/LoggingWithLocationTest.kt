package io.github.oshai.kotlinlogging

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoggingWithLocationTest {
  private val appenderWithWriter: AppenderWithWriter = AppenderWithWriter("%p %C{1}.%M(%L) - %m%n")

  init {
    Configurator.setRootLevel(Level.TRACE)
  }

  @BeforeEach
  fun setupAppender() {
    addAppender(appenderWithWriter.appender)
  }

  @AfterEach
  fun removeAppender() {
    removeAppender(appenderWithWriter.appender)
  }

  @Test
  fun testLoggingWithLocation() {
    ClassWithLoggingForLocationTesting().log()
    assertEquals(
      "INFO ClassWithLoggingForLocationTesting.log(10) - test",
      appenderWithWriter.writer.toString().trim(),
    )
  }

  @Test
  fun testLazyLoggingWithLocation() {
    ClassWithLoggingForLocationTesting().logLazy()
    assertEquals(
      "INFO ClassWithLoggingForLocationTesting.logLazy(14) - test",
      appenderWithWriter.writer.toString().trim(),
    )
  }

  @Test
  fun testFluentLoggingWithLocation() {
    ClassWithLoggingForLocationTesting().logFluentWithPayload()
    assertEquals(
      "INFO ClassWithLoggingForLocationTesting.logFluentWithPayload(34) - test",
      appenderWithWriter.writer.toString().trim(),
    )
  }

  @Test
  fun testAtLoggingWithLocation() {
    ClassWithLoggingForLocationTesting().logAt()
    assertEquals(
      "INFO ClassWithLoggingForLocationTesting.logAt(41) - test",
      appenderWithWriter.writer.toString().trim(),
    )
  }

  @Test
  fun testNullLoggingWithLocation() {
    ClassWithLoggingForLocationTesting().logNull()
    assertEquals(
      "INFO ClassWithLoggingForLocationTesting.logNull(18) - null",
      appenderWithWriter.writer.toString().trim(),
    )
  }

  @Test
  fun testNullLoggingWithLocationEntryExit() {
    ClassWithLoggingForLocationTesting().logEntry()
    @Suppress("MaxLineLength")
    assertEquals(
      "TRACE ClassWithLoggingForLocationTesting.logEntry(22) - entry with (1, 2)" +
        System.lineSeparator() +
        "INFO " +
        "ClassWithLoggingForLocationTesting.logEntry(23) - log entry body" +
        System.lineSeparator() +
        "TRACE " +
        "ClassWithLoggingForLocationTesting.logEntry(24) - exit with ((2, 1))",
      appenderWithWriter.writer.toString().trim(),
    )
  }

  @Test
  fun testNullLoggingWithLocationEntryExitOpt() {
    ClassWithLoggingForLocationTesting().logExitOpt()
    assertEquals(
      "TRACE ClassWithLoggingForLocationTesting.logExitOpt(28) - entry with (1, 2)" +
        System.lineSeparator() +
        "INFO ClassWithLoggingForLocationTesting.logExitOpt(29) - log entry body" +
        System.lineSeparator() +
        "TRACE ClassWithLoggingForLocationTesting.logExitOpt(30) - exit with (null)",
      appenderWithWriter.writer.toString().trim(),
    )
  }
}
