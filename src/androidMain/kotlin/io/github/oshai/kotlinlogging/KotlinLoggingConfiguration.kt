package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.AndroidNativeLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory

public actual object KotlinLoggingConfiguration {
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
        if (KotlinLoggingConfiguration.loggerFactory != DirectLoggerFactory) {
          println(
            "kotlin-logging: [WARN] configuring 'direct.$name' but the active logger factory is not 'DirectLoggerFactory' (active: ${KotlinLoggingConfiguration.loggerFactory::class.simpleName}). This config might be ignored."
          )
        }
      }
    }

  public actual interface DirectLoggingConfiguration {
    public actual var logLevel: Level
    public actual var formatter: Formatter
    public actual var appender: Appender
  }

  @Volatile public actual var loggerFactory: KLoggerFactory = detectLogger()

  init {
    println(
      "kotlin-logging: initializing... active logger factory: ${loggerFactory::class.simpleName}"
    )
  }

  private fun detectLogger(): KLoggerFactory {
    if (System.getProperty("kotlin-logging-to-android-native") != null) {
      return AndroidNativeLoggerFactory
    }
    // default to slf4j
    return Slf4jLoggerFactory
  }
}
