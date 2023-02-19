package io.github.oshai.internal

import io.github.oshai.KLogger
import io.github.oshai.KotlinLoggingConfiguration.APPENDER
import io.github.oshai.KotlinLoggingConfiguration.FORMATTER
import io.github.oshai.Level
import io.github.oshai.Level.DEBUG
import io.github.oshai.Level.ERROR
import io.github.oshai.Level.INFO
import io.github.oshai.Level.TRACE
import io.github.oshai.Level.WARN
import io.github.oshai.Marker
import io.github.oshai.isLoggingEnabled

@Suppress("TooManyFunctions")
internal class KLoggerJS(override val name: String) : KLogger {

  override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(name, msg, APPENDER::trace)

  override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(name, msg, APPENDER::debug)

  override fun info(msg: () -> Any?) = INFO.logIfEnabled(name, msg, APPENDER::info)

  override fun warn(msg: () -> Any?) = WARN.logIfEnabled(name, msg, APPENDER::warn)

  override fun error(msg: () -> Any?) = ERROR.logIfEnabled(name, msg, APPENDER::error)

  override fun trace(t: Throwable?, msg: () -> Any?) =
    TRACE.logIfEnabled(name, msg, t, APPENDER::trace)

  override fun debug(t: Throwable?, msg: () -> Any?) =
    DEBUG.logIfEnabled(name, msg, t, APPENDER::debug)

  override fun info(t: Throwable?, msg: () -> Any?) =
    INFO.logIfEnabled(name, msg, t, APPENDER::info)

  override fun warn(t: Throwable?, msg: () -> Any?) =
    WARN.logIfEnabled(name, msg, t, APPENDER::warn)

  override fun error(t: Throwable?, msg: () -> Any?) =
    ERROR.logIfEnabled(name, msg, t, APPENDER::error)

  override fun trace(marker: Marker?, msg: () -> Any?) =
    TRACE.logIfEnabled(name, marker, msg, APPENDER::trace)

  override fun debug(marker: Marker?, msg: () -> Any?) =
    DEBUG.logIfEnabled(name, marker, msg, APPENDER::debug)

  override fun info(marker: Marker?, msg: () -> Any?) =
    INFO.logIfEnabled(name, marker, msg, APPENDER::info)

  override fun warn(marker: Marker?, msg: () -> Any?) =
    WARN.logIfEnabled(name, marker, msg, APPENDER::warn)

  override fun error(marker: Marker?, msg: () -> Any?) =
    ERROR.logIfEnabled(name, marker, msg, APPENDER::error)

  override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    TRACE.logIfEnabled(name, marker, msg, t, APPENDER::trace)

  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    DEBUG.logIfEnabled(name, marker, msg, t, APPENDER::debug)

  override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    INFO.logIfEnabled(name, marker, msg, t, APPENDER::info)

  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    WARN.logIfEnabled(name, marker, msg, t, APPENDER::warn)

  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    ERROR.logIfEnabled(name, marker, msg, t, APPENDER::error)

  private fun Level.logIfEnabled(loggerName: String, msg: () -> Any?, logFunction: (Any?) -> Unit) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, loggerName, msg))
    }
  }

  private fun Level.logIfEnabled(
    loggerName: String,
    msg: () -> Any?,
    t: Throwable?,
    logFunction: (Any?) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, loggerName, t, msg))
    }
  }

  private fun Level.logIfEnabled(
    loggerName: String,
    marker: Marker?,
    msg: () -> Any?,
    logFunction: (Any?) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, loggerName, marker, msg))
    }
  }

  private fun Level.logIfEnabled(
    loggerName: String,
    marker: Marker?,
    msg: () -> Any?,
    t: Throwable?,
    logFunction: (Any?) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, loggerName, marker, t, msg))
    }
  }

  override fun entry(vararg argArray: Any?) {
    TRACE.logIfEnabled(name, { "entry($argArray)" }, APPENDER::trace)
  }

  override fun exit() {
    TRACE.logIfEnabled(name, { "exit()" }, APPENDER::trace)
  }

  override fun <T : Any?> exit(result: T): T {
    TRACE.logIfEnabled(name, { "exit($result)" }, APPENDER::trace)
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    ERROR.logIfEnabled(name, { "throwing($throwable" }, throwable, APPENDER::error)
    return throwable
  }

  override fun <T : Throwable> catching(throwable: T) {
    ERROR.logIfEnabled(name, { "catching($throwable" }, throwable, APPENDER::error)
  }

  /**
   * Is the logger instance enabled for the TRACE level?
   *
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   */
  override val isTraceEnabled: Boolean = TRACE.isLoggingEnabled()

  /**
   * Is the logger instance enabled for the DEBUG level?
   *
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  override val isDebugEnabled: Boolean = DEBUG.isLoggingEnabled()

  /**
   * Is the logger instance enabled for the INFO level?
   *
   * @return True if this Logger is enabled for the INFO level, false otherwise.
   */
  override val isInfoEnabled: Boolean = INFO.isLoggingEnabled()

  /**
   * Is the logger instance enabled for the WARN level?
   *
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  override val isWarnEnabled: Boolean = WARN.isLoggingEnabled()

  /**
   * Is the logger instance enabled for the ERROR level?
   *
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  override val isErrorEnabled: Boolean = ERROR.isLoggingEnabled()
}
