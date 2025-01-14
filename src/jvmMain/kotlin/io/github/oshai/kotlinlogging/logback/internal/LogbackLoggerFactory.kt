package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.LogbackServiceProvider
import io.github.oshai.kotlinlogging.KLogger

public object LogbackLoggerFactory {

  private val logbackServiceProvider = createLogbackServiceProvider()

  private fun createLogbackServiceProvider(): LogbackServiceProvider {
    val logbackServiceProvider = LogbackServiceProvider()
    logbackServiceProvider.initialize()
    return logbackServiceProvider
  }

  /** Get a Logback logger by name. Logback relies on SLF4J logger factory */
  internal fun logbackLogger(name: String): Logger =
    logbackServiceProvider.loggerFactory.getLogger(name) as Logger

  internal fun wrapLogbackLogger(logbackLogger: Logger): KLogger =
    LogbackLoggerWrapper(logbackLogger, logbackServiceProvider)

  public fun getLoggerContext(): LoggerContext =
    logbackServiceProvider.loggerFactory as LoggerContext
}
