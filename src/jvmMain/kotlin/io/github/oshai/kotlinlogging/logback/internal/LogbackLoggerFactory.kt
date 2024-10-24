package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.LogbackServiceProvider
import io.github.oshai.kotlinlogging.KLogger

internal object LogbackLoggerFactory {

  private val logbackServiceProvider = createLogbackServiceProvider()

  private fun createLogbackServiceProvider(): LogbackServiceProvider {
    val logbackServiceProvider = LogbackServiceProvider()
    logbackServiceProvider.initialize()
    return logbackServiceProvider
  }

  /** get a java logger by name. Logback relies on SLF4J logger factory */
  internal fun jLogger(name: String): Logger = logbackServiceProvider.loggerFactory.getLogger(name) as Logger

  /** wrap java logger based on location awareness */
  internal fun wrapJLogger(jLogger: Logger): KLogger = LogbackLoggerWrapper(jLogger, logbackServiceProvider)

  fun getLoggerContext() = logbackServiceProvider.loggerFactory as LoggerContext

}
