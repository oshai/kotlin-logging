package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.LoggerContext
import io.github.oshai.kotlinlogging.KLogger
import org.slf4j.LoggerFactory

public object LogbackLoggerFactory {

  /** Get a Logback logger by name. Logback relies on SLF4J logger factory */
  internal fun logbackLogger(name: String): KLogger {
    val factory = LoggerFactory.getILoggerFactory()
    return if (factory is LoggerContext) {
      LogbackLoggerWrapper(factory.getLogger(name))
    } else {
      error("Logger factory is not a Logback LoggerContext")
    }
  }

  public fun getLoggerContext(): LoggerContext? =
    LoggerFactory.getILoggerFactory() as? LoggerContext
}
