package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.DirectLoggerFactory
import io.github.oshai.kotlinlogging.jul.internal.JulLoggerFactory
import io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory

public actual object KotlinLoggingConfiguration {
  /**
   * The global logger factory used by `KotlinLogging.logger`. Change this to swap the underlying
   * logging implementation (e.g., to [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory]
   * on JVM/Darwin).
   */
  @Volatile public actual var loggerFactory: KLoggerFactory = detectLogger()

  @Volatile public actual var logLevel: Level = Level.INFO
  @Volatile public actual var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
  @Volatile public actual var appender: Appender = DefaultAppender

  private fun detectLogger(): KLoggerFactory {
    if (System.getProperty("kotlin-logging-to-jul") != null) {
      return JulLoggerFactory
    } else if (System.getProperty("kotlin-logging-to-logback") == "true") {
      return LogbackLoggerFactory
    } else if (System.getProperty("kotlin-logging-to-direct") == "true") {
      return DirectLoggerFactory
    }
    // default to SLF4J
    return Slf4jLoggerFactory
  }
}
