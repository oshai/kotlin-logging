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

  @Volatile public actual var loggerFactory: KLoggerFactory = detectLogger()

  private fun detectLogger(): KLoggerFactory {
    if (System.getProperty("kotlin-logging-to-android-native") != null) {
      return AndroidNativeLoggerFactory
    }
    // default to slf4j
    return Slf4jLoggerFactory
  }
}
