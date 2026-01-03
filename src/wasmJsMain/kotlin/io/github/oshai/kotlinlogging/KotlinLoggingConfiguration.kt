package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.DirectLoggerFactory

public actual object KotlinLoggingConfiguration {
  public actual var logLevel: Level = Level.INFO
  public actual var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
  public actual var appender: Appender = ConsoleOutputAppender()
  public actual var loggerFactory: KLoggerFactory = DirectLoggerFactory
}
