package io.github.oshai.kotlinlogging

public actual object KotlinLoggingConfiguration {
  public actual var logLevel: Level = Level.INFO
  public actual var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
  public actual var appender: Appender = ConsoleOutputAppender()
}
