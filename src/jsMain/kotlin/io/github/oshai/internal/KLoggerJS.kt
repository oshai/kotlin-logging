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
import io.github.oshai.isLoggingEnabled

@Suppress("TooManyFunctions")
internal class KLoggerJS(override val name: String) : KLogger {

  override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(msg, APPENDER::trace)

  override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(msg, APPENDER::debug)

  override fun info(msg: () -> Any?) = INFO.logIfEnabled(msg, APPENDER::info)

  override fun warn(msg: () -> Any?) = WARN.logIfEnabled(msg, APPENDER::warn)

  override fun error(msg: () -> Any?) = ERROR.logIfEnabled(msg, APPENDER::error)

  override fun trace(t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(msg, t, APPENDER::trace)

  override fun debug(t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(msg, t, APPENDER::debug)

  override fun info(t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(msg, t, APPENDER::info)

  override fun warn(t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(msg, t, APPENDER::warn)

  override fun error(t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(msg, t, APPENDER::error)

  override fun trace(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    TRACE.logIfEnabled(marker, msg, APPENDER::trace)

  override fun debug(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    DEBUG.logIfEnabled(marker, msg, APPENDER::debug)

  override fun info(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    INFO.logIfEnabled(marker, msg, APPENDER::info)

  override fun warn(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    WARN.logIfEnabled(marker, msg, APPENDER::warn)

  override fun error(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    ERROR.logIfEnabled(marker, msg, APPENDER::error)

  override fun trace(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    TRACE.logIfEnabled(marker, msg, t, APPENDER::trace)

  override fun debug(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    DEBUG.logIfEnabled(marker, msg, t, APPENDER::debug)

  override fun info(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    INFO.logIfEnabled(marker, msg, t, APPENDER::info)

  override fun warn(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    WARN.logIfEnabled(marker, msg, t, APPENDER::warn)

  override fun error(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    ERROR.logIfEnabled(marker, msg, t, APPENDER::error)

  private fun Level.logIfEnabled(msg: () -> Any?, logFunction: (Any?) -> Unit) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, msg))
    }
  }

  private fun Level.logIfEnabled(msg: () -> Any?, t: Throwable?, logFunction: (Any?) -> Unit) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, t, msg))
    }
  }

  private fun Level.logIfEnabled(
    marker: io.github.oshai.Marker?,
    msg: () -> Any?,
    logFunction: (Any?) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, marker, msg))
    }
  }

  private fun Level.logIfEnabled(
    marker: io.github.oshai.Marker?,
    msg: () -> Any?,
    t: Throwable?,
    logFunction: (Any?) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, marker, t, msg))
    }
  }

  override fun entry(vararg argArray: Any?) {
    TRACE.logIfEnabled({ "entry($argArray)" }, APPENDER::trace)
  }

  override fun exit() {
    TRACE.logIfEnabled({ "exit()" }, APPENDER::trace)
  }

  override fun <T : Any?> exit(result: T): T {
    TRACE.logIfEnabled({ "exit($result)" }, APPENDER::trace)
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    ERROR.logIfEnabled({ "throwing($throwable" }, throwable, APPENDER::error)
    return throwable
  }

  override fun <T : Throwable> catching(throwable: T) {
    ERROR.logIfEnabled({ "catching($throwable" }, throwable, APPENDER::error)
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
