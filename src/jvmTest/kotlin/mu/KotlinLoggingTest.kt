package mu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory

private val logger = KotlinLogging.logger {}
private val loggerFromSlf4j = KotlinLogging.logger(LoggerFactory.getLogger("mu.slf4jLogger"))
private val loggerFromSlf4jExtension =
    LoggerFactory.getLogger("mu.slf4jLoggerExtension").toKLogger()

class ForKotlinLoggingTest {
  val loggerInClass = KotlinLogging.logger {}

  companion object {
    val loggerInCompanion = KotlinLogging.logger {}
  }
}

class KotlinLoggingTest {

  @Test
  fun testLoggerName() {
    assertAll(
        { assertEquals("mu.KotlinLoggingTest", logger.name) },
        { assertEquals("mu.ForKotlinLoggingTest", ForKotlinLoggingTest().loggerInClass.name) },
        { assertEquals("mu.ForKotlinLoggingTest", ForKotlinLoggingTest.loggerInCompanion.name) },
        { assertEquals("mu.slf4jLogger", loggerFromSlf4j.name) },
        { assertEquals("mu.slf4jLoggerExtension", loggerFromSlf4jExtension.name) },
    )
  }
}
