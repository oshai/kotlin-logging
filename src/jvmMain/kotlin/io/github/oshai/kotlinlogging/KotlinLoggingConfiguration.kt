package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.jul.internal.JulLoggerFactory
import io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory

public actual object KotlinLoggingConfiguration {
  /**
   * The global logger factory used by `KotlinLogging.logger`. Change this to swap the underlying
   * logging implementation (e.g., to [DirectLoggerFactory] on JVM/Darwin).
   */
  @Volatile public actual var loggerFactory: KLoggerFactory = detectLogger()

  public actual var logStartupMessage: Boolean = true

  public actual val direct: DirectLoggingConfiguration =
    object : DirectLoggingConfiguration {
      @Volatile
      override var logLevel: Level = Level.INFO
        set(value) {
          field = value
          internalCheckFactory("logLevel", loggerFactory)
        }

      @Volatile
      override var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
        set(value) {
          field = value
          internalCheckFactory("formatter", loggerFactory)
        }

      @Volatile
      override var appender: Appender = DefaultAppender
        set(value) {
          field = value
          internalCheckFactory("appender", loggerFactory)
        }
    }

  public actual interface DirectLoggingConfiguration {
    public actual var logLevel: Level
    public actual var formatter: Formatter
    public actual var appender: Appender
  }

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
