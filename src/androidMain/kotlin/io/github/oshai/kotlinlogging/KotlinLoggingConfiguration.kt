package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.AndroidLoggerFactory

public actual object KotlinLoggingConfiguration {
  @Volatile public actual var logLevel: Level = Level.INFO
  @Volatile public actual var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
  @Volatile public actual var appender: Appender = DefaultAppender
  @Volatile public actual var loggerFactory: KLoggerFactory = AndroidLoggerFactory
}
