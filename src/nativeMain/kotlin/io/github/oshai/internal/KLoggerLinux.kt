package io.github.oshai.internal

import io.github.oshai.KLogger
import io.github.oshai.KotlinLoggingConfiguration.appender
import io.github.oshai.KotlinLoggingConfiguration.formatter
import io.github.oshai.Level.*
import io.github.oshai.isLoggingEnabled

internal class KLoggerLinux(override val name: String) : KLogger {

  override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(msg, appender::trace)

  override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(msg, appender::debug)

  override fun info(msg: () -> Any?) = INFO.logIfEnabled(msg, appender::info)

  override fun warn(msg: () -> Any?) = WARN.logIfEnabled(msg, appender::warn)

  override fun error(msg: () -> Any?) = ERROR.logIfEnabled(msg, appender::error)

  override fun trace(t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(msg, t, appender::trace)

  override fun debug(t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(msg, t, appender::debug)

  override fun info(t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(msg, t, appender::info)

  override fun warn(t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(msg, t, appender::warn)

  override fun error(t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(msg, t, appender::error)

  override fun trace(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    TRACE.logIfEnabled(marker, msg, appender::trace)

  override fun debug(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    DEBUG.logIfEnabled(marker, msg, appender::debug)

  override fun info(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    INFO.logIfEnabled(marker, msg, appender::info)

  override fun warn(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    WARN.logIfEnabled(marker, msg, appender::warn)

  override fun error(marker: io.github.oshai.Marker?, msg: () -> Any?) =
    ERROR.logIfEnabled(marker, msg, appender::error)

  override fun trace(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    TRACE.logIfEnabled(marker, msg, t, appender::trace)

  override fun debug(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    DEBUG.logIfEnabled(marker, msg, t, appender::debug)

  override fun info(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    INFO.logIfEnabled(marker, msg, t, appender::info)

  override fun warn(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    WARN.logIfEnabled(marker, msg, t, appender::warn)

  override fun error(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) =
    ERROR.logIfEnabled(marker, msg, t, appender::error)

  private fun io.github.oshai.Level.logIfEnabled(
    msg: () -> Any?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(name, formatter.formatMessage(appender.includePrefix, this, name, msg))
    }
  }

  private fun io.github.oshai.Level.logIfEnabled(
    msg: () -> Any?,
    t: Throwable?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(name, formatter.formatMessage(appender.includePrefix, this, name, t, msg))
    }
  }

  private fun io.github.oshai.Level.logIfEnabled(
    marker: io.github.oshai.Marker?,
    msg: () -> Any?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(name, formatter.formatMessage(appender.includePrefix, this, name, marker, msg))
    }
  }

  private fun io.github.oshai.Level.logIfEnabled(
    marker: io.github.oshai.Marker?,
    msg: () -> Any?,
    t: Throwable?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(name, formatter.formatMessage(appender.includePrefix, this, name, marker, t, msg))
    }
  }

  override fun entry(vararg argArray: Any?) {
    TRACE.logIfEnabled({ "entry($argArray)" }, appender::trace)
  }

  override fun exit() {
    TRACE.logIfEnabled({ "exit()" }, appender::trace)
  }

  override fun <T : Any?> exit(result: T): T {
    TRACE.logIfEnabled({ "exit($result)" }, appender::trace)
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    ERROR.logIfEnabled({ "throwing($throwable" }, throwable, appender::error)
    return throwable
  }

  override fun <T : Throwable> catching(throwable: T) {
    ERROR.logIfEnabled({ "catching($throwable" }, throwable, appender::error)
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
