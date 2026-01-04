package io.github.oshai.kotlinlogging

public actual object KotlinLoggingConfiguration {
  public actual val direct: DirectLoggingConfiguration =
    object : DirectLoggingConfiguration {
      override var logLevel: Level = Level.INFO
      override var formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
      override var appender: Appender = ConsoleOutputAppender()
    }

  public actual interface DirectLoggingConfiguration {
    public actual var logLevel: Level
    public actual var formatter: Formatter
    public actual var appender: Appender
  }

  public actual var loggerFactory: KLoggerFactory = DirectLoggerFactory
}
