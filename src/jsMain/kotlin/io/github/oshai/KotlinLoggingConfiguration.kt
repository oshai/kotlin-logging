package io.github.oshai

public object KotlinLoggingConfiguration {
  public var LOG_LEVEL: Level = Level.INFO
  public var APPENDER: Appender = ConsoleOutputAppender
  public var FORMATTER: Formatter = DefaultMessageFormatter
}
