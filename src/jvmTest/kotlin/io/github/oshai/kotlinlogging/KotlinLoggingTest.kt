package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.slf4j.logger
import io.github.oshai.kotlinlogging.slf4j.toKLogger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory

private val logger = KotlinLogging.logger {}
private val loggerFromSlf4j =
  KotlinLogging.logger(LoggerFactory.getLogger("io.github.oshai.kotlinlogging.slf4jLogger"))
private val loggerFromSlf4jExtension =
  LoggerFactory.getLogger("io.github.oshai.kotlinlogging.slf4jLoggerExtension").toKLogger()

class ForKotlinLoggingTest {
  val loggerInClass = KotlinLogging.logger {}

  companion object {
    val loggerInCompanion = KotlinLogging.logger {}
  }

  class InnerClass {
    val loggerInInnerClass = KotlinLogging.logger {}

    private companion object {
      val loggerInInnerCompanion = KotlinLogging.logger {}
    }

    val innerCompanionLoggerName: String = loggerInInnerCompanion.name
  }
}

class KotlinLoggingTest {

  @Test
  fun testLoggerName() {
    assertAll(
      { assertEquals("io.github.oshai.kotlinlogging.KotlinLoggingTest", logger.name) },
      {
        assertEquals(
          "io.github.oshai.kotlinlogging.ForKotlinLoggingTest",
          ForKotlinLoggingTest().loggerInClass.name,
        )
      },
      {
        assertEquals(
          "io.github.oshai.kotlinlogging.ForKotlinLoggingTest",
          ForKotlinLoggingTest.loggerInCompanion.name,
        )
      },
      {
        assertEquals(
          "io.github.oshai.kotlinlogging.ForKotlinLoggingTest",
          ForKotlinLoggingTest.InnerClass().loggerInInnerClass.name,
        )
      },
      {
        assertEquals(
          "io.github.oshai.kotlinlogging.ForKotlinLoggingTest",
          ForKotlinLoggingTest.InnerClass().innerCompanionLoggerName,
        )
      },
      { assertEquals("io.github.oshai.kotlinlogging.slf4jLogger", loggerFromSlf4j.name) },
      {
        assertEquals(
          "io.github.oshai.kotlinlogging.slf4jLoggerExtension",
          loggerFromSlf4jExtension.name,
        )
      },
    )
  }
}
