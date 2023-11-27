package io.github.oshai.kotlinlogging

public expect object KotlinLoggingConfiguration {
  public var logLevel: Level
  public var formatter: Formatter
  public var appender: Appender
}
