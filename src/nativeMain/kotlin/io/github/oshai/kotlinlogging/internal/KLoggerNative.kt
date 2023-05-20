package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.appender
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.formatter
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Level.*
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.isLoggingEnabled

internal class KLoggerNative(override val name: String) : KLogger {

  override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(name, msg, appender::trace)

  override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(name, msg, appender::debug)

  override fun info(msg: () -> Any?) = INFO.logIfEnabled(name, msg, appender::info)

  override fun warn(msg: () -> Any?) = WARN.logIfEnabled(name, msg, appender::warn)

  override fun error(msg: () -> Any?) = ERROR.logIfEnabled(name, msg, appender::error)

  override fun trace(t: Throwable?, msg: () -> Any?) =
    TRACE.logIfEnabled(name, msg, t, appender::trace)

  override fun debug(t: Throwable?, msg: () -> Any?) =
    DEBUG.logIfEnabled(name, msg, t, appender::debug)

  override fun info(t: Throwable?, msg: () -> Any?) =
    INFO.logIfEnabled(name, msg, t, appender::info)

  override fun warn(t: Throwable?, msg: () -> Any?) =
    WARN.logIfEnabled(name, msg, t, appender::warn)

  override fun error(t: Throwable?, msg: () -> Any?) =
    ERROR.logIfEnabled(name, msg, t, appender::error)

  override fun trace(marker: Marker?, msg: () -> Any?) =
    TRACE.logIfEnabled(name, marker, msg, appender::trace)

  override fun debug(marker: Marker?, msg: () -> Any?) =
    DEBUG.logIfEnabled(name, marker, msg, appender::debug)

  override fun info(marker: Marker?, msg: () -> Any?) =
    INFO.logIfEnabled(name, marker, msg, appender::info)

  override fun warn(marker: Marker?, msg: () -> Any?) =
    WARN.logIfEnabled(name, marker, msg, appender::warn)

  override fun error(marker: Marker?, msg: () -> Any?) =
    ERROR.logIfEnabled(name, marker, msg, appender::error)

  override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    TRACE.logIfEnabled(name, marker, msg, t, appender::trace)

  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    DEBUG.logIfEnabled(name, marker, msg, t, appender::debug)

  override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    INFO.logIfEnabled(name, marker, msg, t, appender::info)

  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    WARN.logIfEnabled(name, marker, msg, t, appender::warn)

  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) =
    ERROR.logIfEnabled(name, marker, msg, t, appender::error)

  private fun Level.logIfEnabled(
    loggerName: String,
    msg: () -> Any?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(
        loggerName,
        formatter.formatMessage(appender.includePrefix, this, loggerName, msg)
      )
    }
  }

  private fun Level.logIfEnabled(
    loggerName: String,
    msg: () -> Any?,
    t: Throwable?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(
        loggerName,
        formatter.formatMessage(appender.includePrefix, this, loggerName, t, msg)
      )
    }
  }

  private fun Level.logIfEnabled(
    loggerName: String,
    marker: Marker?,
    msg: () -> Any?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(
        loggerName,
        formatter.formatMessage(appender.includePrefix, this, loggerName, marker, msg)
      )
    }
  }

  private fun Level.logIfEnabled(
    loggerName: String,
    marker: Marker?,
    msg: () -> Any?,
    t: Throwable?,
    logFunction: (String, String) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(
        loggerName,
        formatter.formatMessage(appender.includePrefix, this, loggerName, marker, t, msg)
      )
    }
  }

  override fun entry(vararg arguments: Any?) {
    TRACE.logIfEnabled(name, { "entry($arguments)" }, appender::trace)
  }

  override fun exit() {
    TRACE.logIfEnabled(name, { "exit()" }, appender::trace)
  }

  override fun <T : Any?> exit(result: T): T {
    TRACE.logIfEnabled(name, { "exit($result)" }, appender::trace)
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    ERROR.logIfEnabled(name, { "throwing($throwable" }, throwable, appender::error)
    return throwable
  }

  override fun <T : Throwable> catching(throwable: T) {
    ERROR.logIfEnabled(name, { "catching($throwable" }, throwable, appender::error)
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

  /**
   * Is the logger instance OFF?
   *
   * @return True if this Logger is set to the OFF level, false otherwise.
   */
  override val isLoggingOff: Boolean = OFF.isLoggingEnabled()
}
