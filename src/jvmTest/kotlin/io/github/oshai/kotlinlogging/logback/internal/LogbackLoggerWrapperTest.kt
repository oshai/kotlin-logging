package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.OutputStreamAppender
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.ByteArrayOutputStream
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class LogbackLoggerWrapperTest {

  companion object {
    private lateinit var logger: KLogger
    private lateinit var warnLogger: KLogger
    private lateinit var errorLogger: KLogger
    private lateinit var logOutputStream: ByteArrayOutputStream
    private lateinit var appender: OutputStreamAppender<ILoggingEvent>
    private lateinit var rootLogger: Logger

    @BeforeAll
    @JvmStatic
    fun init() {
      val loggerContext = LogbackLoggerFactory.getLoggerContext()
      loggerContext.reset()
      System.setProperty("kotlin-logging-to-logback", "true")

      val encoder = PatternLayoutEncoder()
      encoder.context = loggerContext
      encoder.pattern = "%-5p %c %marker - %m%n"
      encoder.charset = Charsets.UTF_8
      encoder.start()

      logOutputStream = ByteArrayOutputStream()
      appender = OutputStreamAppender<ILoggingEvent>()
      appender.context = loggerContext
      appender.encoder = encoder
      appender.outputStream = logOutputStream
      appender.start()

      rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)
      rootLogger.addAppender(appender)
      rootLogger.level = Level.TRACE

      logger = KotlinLogging.logger {}
      warnLogger = KotlinLogging.logger("warnLogger")
      loggerContext.getLogger("warnLogger").level = Level.WARN
      errorLogger = KotlinLogging.logger("errorLogger")
      loggerContext.getLogger("errorLogger").level = Level.ERROR
    }

    @AfterAll
    @JvmStatic
    fun teardown() {
      System.clearProperty("kotlin-logging-to-logback")
      val loggerContext = LogbackLoggerFactory.getLoggerContext()
      loggerContext.reset()
    }
  }

  @Test
  fun testLogbackLogger() {
    assertTrue(logger is LogbackLoggerWrapper)
    assertTrue(warnLogger is LogbackLoggerWrapper)
    assertTrue(errorLogger is LogbackLoggerWrapper)
    logger.info { "simple logback info message" }
    warnLogger.warn { "simple logback warn message" }
    errorLogger.error { "simple logback error message" }
    val lines =
      logOutputStream
        .toByteArray()
        .toString(Charsets.UTF_8)
        .trim()
        .replace("\r", "\n")
        .replace("\n\n", "\n")
        .split("\n")
    assertEquals(
      "INFO  io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapperTest  - simple logback info message",
      lines[0],
    )
    assertEquals("WARN  warnLogger  - simple logback warn message", lines[1])
    assertEquals("ERROR errorLogger  - simple logback error message", lines[2])
  }
}
