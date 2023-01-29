package mu.two

public object KotlinLoggingConfiguration {
  public var LOG_LEVEL: mu.two.Level = mu.two.Level.INFO
  public var APPENDER: Appender = ConsoleOutputAppender
  public var FORMATTER: Formatter = DefaultMessageFormatter
}
