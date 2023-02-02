package io.github.oshai.jul.internal

import io.github.oshai.AppenderWithWriter
import io.github.oshai.KLogger
import io.github.oshai.KotlinLogging
import io.github.oshai.addAppender
import io.github.oshai.removeAppender
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
    private val appenderWithWriter: AppenderWithWriter = AppenderWithWriter()

    @BeforeAll
    @JvmStatic
    fun init() {
      // our jul test just forward the logs jul -> slf4j -> log4j
      SLF4JBridgeHandler.removeHandlersForRootLogger()
      SLF4JBridgeHandler.install()
      Logger.getLogger("").level = Level.FINEST
      System.setProperty("kotlin-logging-to-jul", "true")
      addAppender(appenderWithWriter.appender)
      logger = KotlinLogging.logger {}
    }

    @AfterAll
    @JvmStatic
    fun teardown() {
      System.clearProperty("kotlin-logging-to-jul")
      removeAppender(appenderWithWriter.appender)
    }
  }

  @Test
  fun testJulLogger() {
    assertTrue(logger is JulLoggerWrapper)
    logger.info("simple jul message")
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.jul.internal.JulLoggerWrapperTest  - simple jul message",
        appenderWithWriter.writer
            .toString()
            .trim()
            .replace("\r", "\n")
            .replace("\n\n", "\n")
            .split("\n")[1])
  }
}
