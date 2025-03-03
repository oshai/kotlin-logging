package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.OutputStreamAppender
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KMarkerFactory
import io.github.oshai.kotlinlogging.KotlinLogging
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
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

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

  val mapper = ObjectMapper()

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
      expectedEvent {
        level = "INFO"
        name = "io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapperTest"
        message = "simple logback info message"
      },
      expectedEvent {
        level = "WARN"
        name = "warnLogger"
        message = "simple logback warn message"
      },
      expectedEvent {
        level = "ERROR"
        name = "errorLogger"
        message = "simple logback error message"
      },
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
      expectedEvent {
        level = "INFO"
        name = "io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapperTest"
        message = "msg"
        arg1 = "val1"
        arg2 = "val2"
      }
    )
  }

  private val loggerName = "io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerWrapperTest"
  private val levels = listOf(Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR)
  private val tests =
    tests("root") {
      levels.forEach { levelValue ->
        tests("$levelValue") {
          test("$levelValue simple lambda") {
            givenStatement {
              level = levelValue.toString()
              message = "simple lambda message"
            }
            whenCalling { given ->
              when (given.level) {
                "TRACE" -> logger.trace { given.message }
                "DEBUG" -> logger.debug { given.message }
                "INFO" -> logger.info { given.message }
                "WARN" -> logger.warn { given.message }
                "ERROR" -> logger.error { given.message }
              }
            }
            thenExpect { given ->
              level = given.level
              name = loggerName
              message = given.message
            }
          }
          test("$levelValue SLF4J API without arguments") {
            givenStatement {
              level = levelValue.toString()
              message = "SLF4J message"
            }
            whenCalling { given ->
              when (given.level) {
                "TRACE" -> logger.trace(given.message)
                "DEBUG" -> logger.debug(given.message)
                "INFO" -> logger.info(given.message)
                "WARN" -> logger.warn(given.message)
                "ERROR" -> logger.error(given.message)
              }
            }
            thenExpect { given ->
              level = given.level
              name = loggerName
              message = given.message
            }
          }
          test("$levelValue SLF4J API with arg1") {
            givenStatement {
              level = levelValue.toString()
              messagePrefix = "SLF4J message:"
              message = "$messagePrefix {}"
              arg1 = "val1"
            }
            whenCalling { given ->
              when (given.level) {
                "TRACE" -> logger.trace(given.message, given.arg1)
                "DEBUG" -> logger.debug(given.message, given.arg1)
                "INFO" -> logger.info(given.message, given.arg1)
                "WARN" -> logger.warn(given.message, given.arg1)
                "ERROR" -> logger.error(given.message, given.arg1)
              }
            }
            thenExpect { given ->
              level = given.level
              name = loggerName
              message = "${given.messagePrefix} ${given.arg1}"
            }
          }
          test("$levelValue SLF4J API with arg1 arg2") {
            givenStatement {
              level = levelValue.toString()
              messagePrefix = "SLF4J message:"
              message = "$messagePrefix {} {}"
              arg1 = "val1"
              arg2 = "val2"
            }
            whenCalling { given ->
              when (given.level) {
                "TRACE" -> logger.trace(given.message, given.arg1, given.arg2)
                "DEBUG" -> logger.debug(given.message, given.arg1, given.arg2)
                "INFO" -> logger.info(given.message, given.arg1, given.arg2)
                "WARN" -> logger.warn(given.message, given.arg1, given.arg2)
                "ERROR" -> logger.error(given.message, given.arg1, given.arg2)
              }
            }
            thenExpect { given ->
              level = given.level
              name = loggerName
              message = "${given.messagePrefix} ${given.arg1} ${given.arg2}"
            }
          }
          test("$levelValue SLF4J API with varargs") {
            givenStatement {
              level = levelValue.toString()
              messagePrefix = "SLF4J message:"
              message = "$messagePrefix {} {} {}"
              arguments = arrayOf("val1", "val2", "val3")
            }
            whenCalling { given ->
              when (given.level) {
                "TRACE" -> logger.trace(given.message, *given.arguments)
                "DEBUG" -> logger.debug(given.message, *given.arguments)
                "INFO" -> logger.info(given.message, *given.arguments)
                "WARN" -> logger.warn(given.message, *given.arguments)
                "ERROR" -> logger.error(given.message, *given.arguments)
              }
            }
            thenExpect { given ->
              level = given.level
              name = loggerName
              message =
                "${given.messagePrefix} ${given.arguments[0]} ${given.arguments[1]} ${given.arguments[2]}"
            }
          }
          test("$levelValue SLF4J API with marker and without arguments") {
            givenStatement {
              level = levelValue.toString()
              marker = "marker"
              message = "SLF4J message"
            }
            whenCalling { given ->
              val marker = KMarkerFactory.getMarker(given.marker)
              when (given.level) {
                "TRACE" -> logger.trace(marker, given.message)
                "DEBUG" -> logger.debug(marker, given.message)
                "INFO" -> logger.info(marker, given.message)
                "WARN" -> logger.warn(marker, given.message)
                "ERROR" -> logger.error(marker, given.message)
              }
            }
            thenExpect { given ->
              level = given.level
              marker = given.marker
              name = loggerName
              message = given.message
            }
          }
          test("$levelValue SLF4J API with marker and arg1") {
            givenStatement {
              level = levelValue.toString()
              marker = "marker"
              messagePrefix = "SLF4J message:"
              message = "$messagePrefix {}"
              arg1 = "val1"
            }
            whenCalling { given ->
              val marker = KMarkerFactory.getMarker(given.marker)
              when (given.level) {
                "TRACE" -> logger.trace(marker, given.message, given.arg1)
                "DEBUG" -> logger.debug(marker, given.message, given.arg1)
                "INFO" -> logger.info(marker, given.message, given.arg1)
                "WARN" -> logger.warn(marker, given.message, given.arg1)
                "ERROR" -> logger.error(marker, given.message, given.arg1)
              }
            }
            thenExpect { given ->
              level = given.level
              marker = given.marker
              name = loggerName
              message = "${given.messagePrefix} ${given.arg1}"
            }
          }
          test("$levelValue SLF4J API with marker and arg1 arg2") {
            givenStatement {
              level = levelValue.toString()
              marker = "marker"
              messagePrefix = "SLF4J message:"
              message = "$messagePrefix {} {}"
              arg1 = "val1"
              arg2 = "val2"
            }
            whenCalling { given ->
              val marker = KMarkerFactory.getMarker(given.marker)
              when (given.level) {
                "TRACE" -> logger.trace(marker, given.message, given.arg1, given.arg2)
                "DEBUG" -> logger.debug(marker, given.message, given.arg1, given.arg2)
                "INFO" -> logger.info(marker, given.message, given.arg1, given.arg2)
                "WARN" -> logger.warn(marker, given.message, given.arg1, given.arg2)
                "ERROR" -> logger.error(marker, given.message, given.arg1, given.arg2)
              }
            }
            thenExpect { given ->
              level = given.level
              marker = given.marker
              name = loggerName
              message = "${given.messagePrefix} ${given.arg1} ${given.arg2}"
            }
          }
          test("$levelValue SLF4J API with marker and varargs") {
            givenStatement {
              level = levelValue.toString()
              marker = "marker"
              messagePrefix = "SLF4J message:"
              message = "$messagePrefix {} {} {}"
              arguments = arrayOf("val1", "val2", "val3")
            }
            whenCalling { given ->
              val marker = KMarkerFactory.getMarker(given.marker)
              when (given.level) {
                "TRACE" -> logger.trace(marker, given.message, *given.arguments)
                "DEBUG" -> logger.debug(marker, given.message, *given.arguments)
                "INFO" -> logger.info(marker, given.message, *given.arguments)
                "WARN" -> logger.warn(marker, given.message, *given.arguments)
                "ERROR" -> logger.error(marker, given.message, *given.arguments)
              }
            }
            thenExpect { given ->
              level = given.level
              marker = given.marker
              name = loggerName
              message =
                "${given.messagePrefix} ${given.arguments[0]} ${given.arguments[1]} ${given.arguments[2]}"
            }
          }
        }
      }
    }

  @TestFactory
  fun testKLoggerFullApi(): Collection<DynamicNode> {
    return tests.toDynamicTests { test ->
      DynamicTest.dynamicTest(test.name) {
        // clean state
        logOutputStream.reset()
        jsonLogOutputStream.reset()
        // when
        test.callWithLogger(logger)
        // then
        assertEvents(test.buildExpected())
      }
    }
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
      { assertEquals(expected?.level, actual?.level) },
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
