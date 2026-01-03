package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.AndroidNativeLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory

public actual object KotlinLoggingConfiguration {
  @Volatile public actual var logLevel: Level = Level.INFO
  @Volatile public actual var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
  @Volatile public actual var appender: Appender = DefaultAppender
  @Volatile public actual var loggerFactory: KLoggerFactory = detectLogger()

  private fun detectLogger(): KLoggerFactory {
    if (System.getProperty("kotlin-logging-to-android-native") != null) {
      return AndroidNativeLoggerFactory
    }
    // default to slf4j
    return Slf4jLoggerFactory
  }
}
