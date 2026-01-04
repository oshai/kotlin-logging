package io.github.oshai.kotlinlogging

import kotlin.concurrent.AtomicReference

public actual object KotlinLoggingConfiguration {
  public actual val direct: DirectLoggingConfiguration =
    object : DirectLoggingConfiguration {
      private val _logLevel = AtomicReference(Level.INFO)
      override var logLevel: Level
        get() = _logLevel.value
        set(value) {
          _logLevel.value = value
        }

      private val _appender = AtomicReference(DefaultAppender)
      override var appender: Appender
        get() = _appender.value
        set(value) {
          _appender.value = value
        }

      private val _formatter =
        AtomicReference<Formatter>(DefaultMessageFormatter(includePrefix = true))
      override var formatter: Formatter
        get() = _formatter.value
        set(value) {
          _formatter.value = value
        }
    }

  public actual interface DirectLoggingConfiguration {
    public actual var logLevel: Level
    public actual var formatter: Formatter
    public actual var appender: Appender
  }

  private val _loggerFactory = AtomicReference<KLoggerFactory>(DirectLoggerFactory)
  public actual var loggerFactory: KLoggerFactory
    get() = _loggerFactory.value
    set(value) {
      _loggerFactory.value = value
    }
}
