package io.github.oshai.kotlinlogging.jul.internal

import io.github.oshai.kotlinlogging.AppenderWithWriter
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration
import io.github.oshai.kotlinlogging.addAppender
import io.github.oshai.kotlinlogging.removeAppender
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory
import java.util.logging.Level
import java.util.logging.Logger
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.bridge.SLF4JBridgeHandler

class JulLoggerWrapperTest {

  companion object {
    private lateinit var logger: KLogger
    private lateinit var warnLogger: KLogger
    private lateinit var errorLogger: KLogger
    private val appenderWithWriter: AppenderWithWriter = AppenderWithWriter()

    @BeforeAll
    @JvmStatic
    fun init() {
      // our jul test just forward the logs jul -> slf4j -> log4j
      SLF4JBridgeHandler.removeHandlersForRootLogger()
      SLF4JBridgeHandler.install()
      Logger.getLogger("").level = Level.FINEST
      System.setProperty("kotlin-logging-to-jul", "true")
      KotlinLoggingConfiguration.loggerFactory = JulLoggerFactory
      addAppender(appenderWithWriter.appender)
      logger = KotlinLogging.logger {}
      warnLogger = KotlinLogging.logger("warnLogger")
      Logger.getLogger("warnLogger").level = Level.WARNING
      errorLogger = KotlinLogging.logger("errorLogger")
      Logger.getLogger("errorLogger").level = Level.SEVERE
    }

    @AfterAll
    @JvmStatic
    fun teardown() {
      System.clearProperty("kotlin-logging-to-jul")
      KotlinLoggingConfiguration.loggerFactory = Slf4jLoggerFactory
      removeAppender(appenderWithWriter.appender)
    }
  }

  @Test
  fun testJulLogger() {
    assertTrue(logger is JulLoggerWrapper)
    assertTrue(warnLogger is JulLoggerWrapper)
    assertTrue(errorLogger is JulLoggerWrapper)
    logger.info { "simple jul info message" }
    warnLogger.warn { "simple jul warn message" }
    errorLogger.error { "simple jul error message" }
    appenderWithWriter.writer.flush()
    val lines =
      appenderWithWriter.writer
        .toString()
        .trim()
        .replace("\r", "\n")
        .replace("\n\n", "\n")
        .split("\n")
    assertEquals(
      "INFO  io.github.oshai.kotlinlogging.jul.internal.JulLoggerWrapperTest  - simple jul info message",
      lines[1],
    )
    assertEquals("WARN  warnLogger  - simple jul warn message", lines[2])
    assertEquals("ERROR errorLogger  - simple jul error message", lines[3])
  }
}
