package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.JvmLoggerFactory as InternalKLoggerFactory

public actual object KotlinLoggingConfiguration {
  /**
   * The global logger factory used by `KotlinLogging.logger`. Change this to swap the underlying
   * logging implementation (e.g., to [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory] on JVM/Darwin).
   */
  @Volatile public actual var logFactory: KLoggerFactory = InternalKLoggerFactory

  @Volatile public actual var logLevel: Level = Level.INFO
  @Volatile public actual var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
  @Volatile public actual var appender: Appender = DefaultAppender
}
