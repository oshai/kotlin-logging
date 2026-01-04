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

  public actual val direct: DirectLoggingConfiguration =
    object : DirectLoggingConfiguration {
      @Volatile
      override var logLevel: Level = Level.INFO
        set(value) {
          field = value
          checkFactory("logLevel")
        }

      @Volatile
      override var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
        set(value) {
          field = value
          checkFactory("formatter")
        }

      @Volatile
      override var appender: Appender = DefaultAppender
        set(value) {
          field = value
          checkFactory("appender")
        }

      private fun checkFactory(name: String) {
        if (loggerFactory != DirectLoggerFactory) {
          println(
            "kotlin-logging: [WARN] configuring 'direct.$name' but the active logger factory is not 'DirectLoggerFactory' (active: ${loggerFactory::class.simpleName}). This config might be ignored."
          )
        }
      }
    }

  public actual interface DirectLoggingConfiguration {
    public actual var logLevel: Level
    public actual var formatter: Formatter
    public actual var appender: Appender
  }

  init {
    println(
      "kotlin-logging: initializing... active logger factory: ${loggerFactory::class.simpleName}"
    )
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
