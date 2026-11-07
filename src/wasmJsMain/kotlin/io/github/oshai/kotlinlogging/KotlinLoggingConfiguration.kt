package io.github.oshai.kotlinlogging

private fun startupMessageDisabledInWindow(): Boolean =
  js(
    "typeof window !== 'undefined' && (window.KOTLIN_LOGGING_STARTUP_MESSAGE === 'false' || window.KOTLIN_LOGGING_STARTUP_MESSAGE === false)"
  )

private fun startupMessageDisabledInEnv(): Boolean =
  js(
    "typeof process !== 'undefined' && process.env && (process.env.KOTLIN_LOGGING_STARTUP_MESSAGE === 'false' || process.env.KOTLIN_LOGGING_STARTUP_MESSAGE === false)"
  )

private fun resolveStartupMessageDefault(): Boolean {
  try {
    if (startupMessageDisabledInWindow()) {
      return false
    }
    if (startupMessageDisabledInEnv()) {
      return false
    }
    return true
  } catch (e: Throwable) {
    return true
  }
}

public actual object KotlinLoggingConfiguration {
  public actual val direct: DirectLoggingConfiguration =
    object : DirectLoggingConfiguration {
      private var _logLevel: Level = Level.INFO
      override var logLevel: Level
        get() = _logLevel
        set(value) {
          _logLevel = value
          internalCheckFactory("logLevel", loggerFactory)
        }

      private var _formatter: Formatter = DefaultMessageFormatter(includePrefix = true)
      override var formatter: Formatter
        get() = _formatter
        set(value) {
          _formatter = value
          internalCheckFactory("formatter", loggerFactory)
        }

      private var _appender: Appender = ConsoleOutputAppender()
      override var appender: Appender
        get() = _appender
        set(value) {
          _appender = value
          internalCheckFactory("appender", loggerFactory)
        }
    }

  public actual interface DirectLoggingConfiguration {
    public actual var logLevel: Level
    public actual var formatter: Formatter
    public actual var appender: Appender
  }

  public actual var loggerFactory: KLoggerFactory = DirectLoggerFactory

  public actual var logStartupMessage: Boolean = resolveStartupMessageDefault()
}
