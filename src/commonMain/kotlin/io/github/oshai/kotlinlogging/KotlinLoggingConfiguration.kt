package io.github.oshai.kotlinlogging

public expect object KotlinLoggingConfiguration {
  public var logLevel: Level
  public var formatter: Formatter
  public var appender: Appender
  public var LOG_FACTORY: KLoggerFactory
  // TODO: Add LOG_FACTORY here once we confirm KLoggerFactory availability
}
