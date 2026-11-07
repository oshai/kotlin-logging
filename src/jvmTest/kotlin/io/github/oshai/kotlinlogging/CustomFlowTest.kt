package io.github.oshai.kotlinlogging

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Verifies the "Custom Flow" for JVM logging.
 *
 * This test suite demonstrates and specifically tests the ability for users to customize:
 * 1. The [KLoggerFactory] used (e.g., swapping SLF4J for [DirectLoggerFactory] or a custom one).
 * 2. The [Appender] used when using [DirectLoggerFactory] (via
 *    [KotlinLoggingConfiguration.direct]).
 * 3. The [Formatter] used when using [DirectLoggerFactory] (via
 *    [KotlinLoggingConfiguration.direct]).
 */
class CustomFlowTest {

  private val originalFactory = KotlinLoggingConfiguration.loggerFactory
  private val originalAppender = KotlinLoggingConfiguration.direct.appender
  private val originalFormatter = KotlinLoggingConfiguration.direct.formatter
  private val originalLevel = KotlinLoggingConfiguration.direct.logLevel

  @BeforeEach
  fun setup() {
    KotlinLoggingConfiguration.loggerFactory = originalFactory
    KotlinLoggingConfiguration.direct.appender = originalAppender
    KotlinLoggingConfiguration.direct.formatter = originalFormatter
    KotlinLoggingConfiguration.direct.logLevel = originalLevel
  }

  @AfterEach
  fun tearDown() {
    KotlinLoggingConfiguration.loggerFactory = originalFactory
    KotlinLoggingConfiguration.direct.appender = originalAppender
    KotlinLoggingConfiguration.direct.formatter = originalFormatter
    KotlinLoggingConfiguration.direct.logLevel = originalLevel
  }

  /**
   * Tests that switching to [DirectLoggerFactory] allows using a custom [Appender] and [Formatter].
   */
  @Test
  fun testCustomAppenderAndFormatterWithDirectLogging() {
    KotlinLoggingConfiguration.loggerFactory = DirectLoggerFactory

    val capturedEvents = mutableListOf<String>()
    val customAppender =
      object : Appender {
        override fun log(loggingEvent: KLoggingEvent) {
          val formatted = KotlinLoggingConfiguration.direct.formatter.formatMessage(loggingEvent)
          capturedEvents.add(formatted)
        }
      }
    KotlinLoggingConfiguration.direct.appender = customAppender

    val customFormatter =
      object : Formatter {
        override fun formatMessage(loggingEvent: KLoggingEvent): String {
          return "[CUSTOM] ${loggingEvent.message}"
        }
      }
    KotlinLoggingConfiguration.direct.formatter = customFormatter

    val logger = KotlinLogging.logger("MyLogger")
    logger.info { "Hello Direct World" }

    assertEquals(1, capturedEvents.size)
    assertEquals("[CUSTOM] Hello Direct World", capturedEvents[0])
  }

  /**
   * Tests that a completely custom [KLoggerFactory] (and [KLogger] implementation) can be injected.
   */
  @Test
  fun testCustomFactory() {
    val customLogger = TestLogger("CustomLogger")
    val customFactory =
      object : KLoggerFactory {
        override fun logger(name: String): KLogger = customLogger
      }

    KotlinLoggingConfiguration.loggerFactory = customFactory

    val logger = KotlinLogging.logger("Test")
    logger.info { "Custom Factory Works" }

    assertEquals("Custom Factory Works", customLogger.lastMessage)
  }

  class TestLogger(override val name: String) : KLogger {
    var lastMessage: String? = null

    override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
      val builder = KLoggingEventBuilder()
      builder.block()
      lastMessage = builder.message
    }

    override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean = true
  }
}
