package io.github.oshai.kotlinlogging

import kotlin.concurrent.AtomicReference

public actual object KotlinLoggingConfiguration {
  // Existing Darwin-specific properties
  public var subsystem: AtomicReference<String?> = AtomicReference(null)
  public var category: AtomicReference<String?> = AtomicReference(null)

  private val _logStartupMessage = AtomicReference(true)
  public actual var logStartupMessage: Boolean
    get() = _logStartupMessage.value
    set(value) {
      _logStartupMessage.value = value
    }

  // Common properties
  public actual val direct: DirectLoggingConfiguration =
    object : DirectLoggingConfiguration {
      private val _logLevel = AtomicReference(Level.INFO)
      override var logLevel: Level
        get() = _logLevel.value
        set(value) {
          _logLevel.value = value
          checkFactory("logLevel")
        }

      private val _formatter =
        AtomicReference<Formatter>(DefaultMessageFormatter(includePrefix = true))
      override var formatter: Formatter
        get() = _formatter.value
        set(value) {
          _formatter.value = value
          checkFactory("formatter")
        }

      private val _appender = AtomicReference(DefaultAppender)
      override var appender: Appender
        get() = _appender.value
        set(value) {
          _appender.value = value
          checkFactory("appender")
        }

      private fun checkFactory(name: String) {
        if (loggerFactory != DirectLoggerFactory) {
          println(
            "kotlin-logging: [WARN] configuring 'direct.$name' but the active logger factory is not 'DirectLoggerFactory' (active: ${loggerFactory::class.simpleName}). This config might be ignored."
          )
        }
      }
    }

  public actual interface DirectLoggingConfiguration {
    public actual var logLevel: Level
    public actual var formatter: Formatter
    public actual var appender: Appender
  }

  private val _loggerFactory = AtomicReference<KLoggerFactory>(DarwinLoggerFactory)
  public actual var loggerFactory: KLoggerFactory
    get() = _loggerFactory.value
    set(value) {
      _loggerFactory.value = value
    }
}
