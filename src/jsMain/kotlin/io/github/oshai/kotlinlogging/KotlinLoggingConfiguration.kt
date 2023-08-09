package io.github.oshai.kotlinlogging

public actual object KotlinLoggingConfiguration {
  public actual var logLevel: Level = Level.INFO
  public actual var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
  public actual var appender: Appender = ConsoleOutputAppender()

  @Deprecated("Use appender instead", ReplaceWith("appender"))
  public var APPENDER: Appender
    get() = appender
    set(value) {
      appender = value
    }
  @Deprecated("Use logLevel instead", ReplaceWith("logLevel"))
  public var LOG_LEVEL: Level
    get() = logLevel
    set(value) {
      logLevel = value
    }
}
