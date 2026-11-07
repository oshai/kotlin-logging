package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.OutputStreamAppender
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KMarkerFactory
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.Marker
import java.io.ByteArrayOutputStream
import kotlin.test.assertTrue
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

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
      encoder.pattern = "%level %logger %marker - %m%n%ex"
      encoder.charset = Charsets.UTF_8
      encoder.start()

      val jsonEncoder = LoggingEventCompositeJsonEncoder()
      jsonEncoder.context = loggerContext
      val patternProvider = LoggingEventPatternJsonProvider()
      patternProvider.context = loggerContext
      patternProvider.pattern =
        """{"level": "%level", "name": "%logger", "marker": "%marker", "message": "%message"}"""
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

  private val mapper = jacksonObjectMapper {}
  private val loggerName = "io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapperTest"
  private val messagePrefix = "SLF4J message:"

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

    assertEvents(
      ExpectedLogEvent(
        level = LogLevel.INFO,
        name = loggerName,
        message = "simple logback info message",
      ),
      ExpectedLogEvent(
        level = LogLevel.WARN,
        name = "warnLogger",
        message = "simple logback warn message",
      ),
      ExpectedLogEvent(
        level = LogLevel.ERROR,
        name = "errorLogger",
        message = "simple logback error message",
      ),
    )
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
    assertEvents(
      ExpectedLogEvent(
        level = LogLevel.INFO,
        name = loggerName,
        message = "msg",
        arg1 = "val1",
        arg2 = "val2",
      )
    )
  }

  @ParameterizedTest(name = "{0} simple lambda")
  @EnumSource(LogLevel::class)
  fun simpleLambda(levelValue: LogLevel) {
    val given = GivenLogStatement(level = levelValue, message = "simple lambda message")
    when (levelValue) {
      LogLevel.TRACE -> logger.trace { given.message }
      LogLevel.DEBUG -> logger.debug { given.message }
      LogLevel.INFO -> logger.info { given.message }
      LogLevel.WARN -> logger.warn { given.message }
      LogLevel.ERROR -> logger.error { given.message }
    }
    assertEvents(ExpectedLogEvent(level = given.level, name = loggerName, message = given.message))
  }

  @ParameterizedTest(name = "{0} marker lambda")
  @EnumSource(LogLevel::class)
  fun markerLambda(levelValue: LogLevel) {
    val given =
      GivenLogStatement(
        level = levelValue,
        marker = KMarkerFactory.getMarker("marker"),
        message = "marker lambda message",
      )
    when (levelValue) {
      LogLevel.TRACE -> logger.trace(given.marker) { given.message }
      LogLevel.DEBUG -> logger.debug(given.marker) { given.message }
      LogLevel.INFO -> logger.info(given.marker) { given.message }
      LogLevel.WARN -> logger.warn(given.marker) { given.message }
      LogLevel.ERROR -> logger.error(given.marker) { given.message }
    }
    assertEvents(
      ExpectedLogEvent(
        level = given.level,
        marker = given.marker?.getName(),
        name = loggerName,
        message = given.message,
      )
    )
  }

  private fun assertEvents(vararg expectedEvents: ExpectedLogEvent?) {
    val lines = logOutputStream.toByteArray().toString(Charsets.UTF_8).trim().lines()
    val jsonLines = jsonLogOutputStream.toByteArray().toString(Charsets.UTF_8).trim().lines()
    assertEquals(expectedEvents.size, lines.size, "Number of log events")
    expectedEvents.forEachIndexed { eventIndex, expectedEvent ->
      val actual = mapper.readValue(jsonLines[eventIndex], ExpectedLogEvent::class.java)
      assertEventEquals(expectedEvent, actual)
      assertEquals(
        "${expectedEvent?.level} ${expectedEvent?.name} ${expectedEvent?.marker} - ${expectedEvent?.message}",
        lines[eventIndex],
      )
    }
  }

  private fun assertEventEquals(expected: ExpectedLogEvent?, actual: ExpectedLogEvent?) {
    assertNotNull(expected)
    assertNotNull(actual)
    assertAll(
      "log event",
      { assertEquals(expected?.level?.toString(), actual?.level?.toString()) },
      { assertEquals(expected?.message, actual?.message) },
      { assertEquals(expected?.arg1, actual?.arg1) },
      { assertEquals(expected?.arg2, actual?.arg2) },
      { assertEquals(expected?.arguments, actual?.arguments) },
      { assertEquals(expected?.marker, actual?.marker) },
    )
  }
}

fun KLogger.isLogbackLoggerWrapper() =
  javaClass.name == "io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapper"

enum class LogLevel {
  TRACE,
  DEBUG,
  INFO,
  WARN,
  ERROR
}

data class GivenLogStatement(
  val level: LogLevel,
  val marker: Marker? = null,
  val message: String,
  val arg1: String = "",
  val arg2: String = "",
  val arguments: Array<Any> = emptyArray(),
)

data class ExpectedLogEvent(
  val level: LogLevel,
  val marker: String? = "",
  val name: String,
  val message: String,
  val arg1: String = "",
  val arg2: String = "",
  val arguments: List<Any> = emptyList(),
)
