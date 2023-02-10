package io.github.oshai

public object KotlinLoggingConfiguration {
  public var LOG_LEVEL: io.github.oshai.Level = io.github.oshai.Level.INFO
  public var APPENDER: Appender = ConsoleOutputAppender
  public var FORMATTER: Formatter = DefaultMessageFormatter
}
