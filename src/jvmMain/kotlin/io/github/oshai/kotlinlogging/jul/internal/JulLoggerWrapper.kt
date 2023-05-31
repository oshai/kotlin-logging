package io.github.oshai.kotlinlogging.jul.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.Level.*
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.internal.toStringSafe
import java.util.logging.Level
import java.util.logging.Logger

@Suppress("OVERRIDE_BY_INLINE", "NOTHING_TO_INLINE")
internal class JulLoggerWrapper(override val underlyingLogger: Logger) : KLogger {
  override val name: String
    get() = underlyingLogger.name

  /** Lazy add a log message if isTraceEnabled is true */
  override fun trace(msg: () -> Any?) {
    if (isTraceEnabled) trace(msg.toStringSafe())
  }

  /** Lazy add a log message if isDebugEnabled is true */
  override fun debug(msg: () -> Any?) {
    if (isDebugEnabled) debug(msg.toStringSafe())
  }

  /** Lazy add a log message if isInfoEnabled is true */
  override fun info(msg: () -> Any?) {
    if (isInfoEnabled) info(msg.toStringSafe())
  }

  /** Lazy add a log message if isWarnEnabled is true */
  override fun warn(msg: () -> Any?) {
    if (isWarnEnabled) warn(msg.toStringSafe())
  }

  /** Lazy add a log message if isErrorEnabled is true */
  override fun error(msg: () -> Any?) {
    if (isErrorEnabled) error(msg.toStringSafe())
  }

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  override fun trace(t: Throwable?, msg: () -> Any?) {
    if (isTraceEnabled) trace(msg.toStringSafe(), t)
  }

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  override fun debug(t: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) debug(msg.toStringSafe(), t)
  }

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  override fun info(t: Throwable?, msg: () -> Any?) {
    if (isInfoEnabled) info(msg.toStringSafe(), t)
  }

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  override fun warn(t: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) warn(msg.toStringSafe(), t)
  }

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  override fun error(t: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) error(msg.toStringSafe(), t)
  }

  /** Lazy add a log message with a marker if isTraceEnabled is true */
  override fun trace(marker: Marker?, msg: () -> Any?) {
    if (isTraceEnabled) trace(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isDebugEnabled is true */
  override fun debug(marker: Marker?, msg: () -> Any?) {
    if (isDebugEnabled) debug(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isInfoEnabled is true */
  override fun info(marker: Marker?, msg: () -> Any?) {
    if (isInfoEnabled) info(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isWarnEnabled is true */
  override fun warn(marker: Marker?, msg: () -> Any?) {
    if (isWarnEnabled) warn(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isErrorEnabled is true */
  override fun error(marker: Marker?, msg: () -> Any?) {
    if (isErrorEnabled) error(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker and throwable payload if isTraceEnabled is true */
  override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isTraceEnabled) trace(marker, msg.toStringSafe(), t)
  }

  override fun trace(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.log(TRACE.toJULLevel(), msg)
    }
  }

  override fun trace(msg: String?, arg: Any?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, arg)
  }

  override fun trace(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun trace(msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, arguments)
  }

  override fun trace(msg: String?, t: Throwable?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, t)
  }

  override fun trace(marker: Marker?, msg: String?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg)
  }

  override fun trace(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, arg)
  }

  override fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun trace(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, arguments)
  }

  override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(TRACE.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isDebugEnabled is true */
  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) debug(marker, msg.toStringSafe(), t)
  }

  override fun debug(msg: String?) {
    if (isDebugEnabled) {
      underlyingLogger.log(DEBUG.toJULLevel(), msg)
    }
  }

  override fun debug(msg: String?, arg: Any?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, arg)
  }

  override fun debug(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun debug(msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, arguments)
  }

  override fun debug(msg: String?, t: Throwable?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, t)
  }

  override fun debug(marker: Marker?, msg: String?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg)
  }

  override fun debug(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, arg)
  }

  override fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun debug(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, arguments)
  }

  override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(DEBUG.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isInfoEnabled is true */
  override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isInfoEnabled) info(marker, msg.toStringSafe(), t)
  }

  override fun info(msg: String?) {
    if (isInfoEnabled) {
      underlyingLogger.info(msg)
    }
  }

  override fun info(msg: String?, arg: Any?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, arg)
  }

  override fun info(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun info(msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, arguments)
  }

  override fun info(msg: String?, t: Throwable?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, t)
  }

  override fun info(marker: Marker?, msg: String?) {
    underlyingLogger.log(INFO.toJULLevel(), msg)
  }

  override fun info(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, arg)
  }

  override fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun info(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, arguments)
  }

  override fun info(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(INFO.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isWarnEnabled is true */
  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) warn(marker, msg.toStringSafe(), t)
  }

  override fun warn(msg: String?) {
    if (isWarnEnabled) {
      underlyingLogger.log(WARN.toJULLevel(), msg)
    }
  }

  override fun warn(msg: String?, arg: Any?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, arg)
  }

  override fun warn(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun warn(msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, arguments)
  }

  override fun warn(msg: String?, t: Throwable?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, t)
  }

  override fun warn(marker: Marker?, msg: String?) {
    underlyingLogger.log(WARN.toJULLevel(), msg)
  }

  override fun warn(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, arg)
  }

  override fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun warn(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, arguments)
  }

  override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(WARN.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isErrorEnabled is true */
  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) error(marker, msg.toStringSafe(), t)
  }

  override fun error(msg: String?) {
    if (isErrorEnabled) {
      underlyingLogger.log(ERROR.toJULLevel(), msg)
    }
  }

  override fun error(msg: String?, arg: Any?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, arg)
  }

  override fun error(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun error(msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, arguments)
  }

  override fun error(msg: String?, t: Throwable?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, t)
  }

  override fun error(marker: Marker?, msg: String?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg)
  }

  override fun error(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, arg)
  }

  override fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, arrayOf(arg1, arg2))
  }

  override fun error(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, arguments)
  }

  override fun error(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(ERROR.toJULLevel(), msg, t)
  }

  override inline fun entry(vararg arguments: Any?) {
    trace("entry({})", arguments)
  }

  override inline fun exit() {
    trace("exit")
  }

  override inline fun <T : Any?> exit(result: T): T {
    trace("exit({})", result)
    return result
  }

  override inline fun <T : Throwable> throwing(throwable: T): T {
    error("throwing($throwable)", throwable)
    return throwable
  }

  override inline fun <T : Throwable> catching(throwable: T) {
    error("catching($throwable)", throwable)
  }

  override val isTraceEnabled: Boolean
    get() = underlyingLogger.isLoggable(TRACE.toJULLevel())

  override fun isTraceEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(TRACE.toJULLevel())
  }

  override val isDebugEnabled: Boolean
    get() = underlyingLogger.isLoggable(DEBUG.toJULLevel())

  override fun isDebugEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(DEBUG.toJULLevel())
  }

  override val isInfoEnabled: Boolean
    get() = underlyingLogger.isLoggable(INFO.toJULLevel())

  override fun isInfoEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(INFO.toJULLevel())
  }

  override val isWarnEnabled: Boolean
    get() = underlyingLogger.isLoggable(WARN.toJULLevel())

  override fun isWarnEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(WARN.toJULLevel())
  }

  override val isErrorEnabled: Boolean
    get() = underlyingLogger.isLoggable(ERROR.toJULLevel())

  override fun isErrorEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(ERROR.toJULLevel())
  }

  override val isLoggingOff: Boolean
    get() = underlyingLogger.isLoggable(OFF.toJULLevel())

  override fun isLoggingOff(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(OFF.toJULLevel())
  }

  private fun io.github.oshai.kotlinlogging.Level.toJULLevel(): Level {
    val julLevel: Level =
      when (this) {
        TRACE -> Level.FINEST
        DEBUG -> Level.FINE
        INFO -> Level.INFO
        WARN -> Level.WARNING
        ERROR -> Level.SEVERE
        OFF -> Level.OFF
      }
    return julLevel
  }
}
