package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.OutputStreamAppender
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.ByteArrayOutputStream
import kotlin.test.fail
import net.logstash.logback.argument.StructuredArguments
import net.logstash.logback.composite.loggingevent.ArgumentsJsonProvider
import net.logstash.logback.composite.loggingevent.LoggingEventPatternJsonProvider
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogbackLoggerWrapperTest {

  companion object {
    private lateinit var logger: KLogger
    private lateinit var warnLogger: KLogger
    private lateinit var errorLogger: KLogger
    private lateinit var logOutputStream: ByteArrayOutputStream
    private lateinit var jsonLogOutputStream: ByteArrayOutputStream
    private lateinit var appender: OutputStreamAppender<ILoggingEvent>
    private lateinit var jsonAppender: OutputStreamAppender<ILoggingEvent>
    private lateinit var rootLogger: Logger

    @BeforeAll
    @JvmStatic
    fun init() {
      val loggerContext =
        LogbackLoggerFactory.getLoggerContext() ?: fail("Logback logger context not found")
      loggerContext.reset()
      System.setProperty("kotlin-logging-to-logback", "true")

      val encoder = PatternLayoutEncoder()
      encoder.context = loggerContext
      encoder.pattern = "%-5p %c %marker - %m%n"
      encoder.charset = Charsets.UTF_8
      encoder.start()

      val jsonEncoder = LoggingEventCompositeJsonEncoder()
      jsonEncoder.context = loggerContext
      val patternProvider = LoggingEventPatternJsonProvider()
      patternProvider.context = loggerContext
      patternProvider.pattern = """{"message": "%message"}"""
      jsonEncoder.providers.addProvider(patternProvider)
      val argumentsJsonProvider = ArgumentsJsonProvider()
      argumentsJsonProvider.isIncludeStructuredArguments = true
      argumentsJsonProvider.nonStructuredArgumentsFieldPrefix = ""
      jsonEncoder.providers.addProvider(argumentsJsonProvider)
      jsonEncoder.start()

      logOutputStream = ByteArrayOutputStream()
      appender = OutputStreamAppender<ILoggingEvent>()
      appender.context = loggerContext
      appender.encoder = encoder
      appender.outputStream = logOutputStream
      appender.start()

      jsonLogOutputStream = ByteArrayOutputStream()
      jsonAppender = OutputStreamAppender<ILoggingEvent>()
      jsonAppender.context = loggerContext
      jsonAppender.encoder = jsonEncoder
      jsonAppender.outputStream = jsonLogOutputStream
      jsonAppender.start()

      rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)
      rootLogger.addAppender(appender)
      rootLogger.addAppender(jsonAppender)
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
      LogbackLoggerFactory.getLoggerContext()?.reset()
    }
  }

  @BeforeEach
  fun resetTest() {
    logOutputStream.reset()
    jsonLogOutputStream.reset()
  }

  @Test
  fun testLogbackLogger() {
    assertTrue(logger.isLogbackLoggerWrapper())
    assertTrue(warnLogger.isLogbackLoggerWrapper())
    assertTrue(errorLogger.isLogbackLoggerWrapper())
    logger.info { "simple logback info message" }
    warnLogger.warn { "simple logback warn message" }
    errorLogger.error { "simple logback error message" }
    val lines = logOutputStream.toByteArray().toString(Charsets.UTF_8).trim().lines()
    val jsonLines = jsonLogOutputStream.toByteArray().toString(Charsets.UTF_8).trim().lines()
    assertEquals(
      "INFO  io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapperTest  - simple logback info message",
      lines[0],
    )
    assertEquals("""{"message":"simple logback info message"}""", jsonLines[0])
    assertEquals("WARN  warnLogger  - simple logback warn message", lines[1])
    assertEquals("""{"message":"simple logback warn message"}""", jsonLines[1])
    assertEquals("ERROR errorLogger  - simple logback error message", lines[2])
    assertEquals("""{"message":"simple logback error message"}""", jsonLines[2])
  }

  @Test
  fun testLogbackLoggerWithArguments() {
    logger.atInfo {
      message = "msg"
      arguments =
        arrayOf(
          StructuredArguments.keyValue("arg1", "val1"),
          StructuredArguments.keyValue("arg2", "val2"),
        )
    }
    val lines = logOutputStream.toByteArray().toString(Charsets.UTF_8).trim().lines()
    val jsonLines = jsonLogOutputStream.toByteArray().toString(Charsets.UTF_8).trim().lines()

    assertEquals(1, lines.size)
    assertEquals(1, jsonLines.size)
    assertEquals(
      "INFO  io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapperTest  - msg",
      lines[0],
    )
    assertEquals("""{"message":"msg","arg1":"val1","arg2":"val2"}""", jsonLines[0])
  }
}

fun KLogger.isLogbackLoggerWrapper() =
  javaClass.name == "io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapper"
