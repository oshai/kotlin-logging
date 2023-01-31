package mu.two.jul.internal

import java.util.logging.Level
import java.util.logging.Logger
import mu.two.KLogger
import mu.two.Marker
import mu.two.internal.toStringSafe

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
      underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), msg)
    }
  }

  override fun trace(format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), format, arg)
  }

  override fun trace(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun trace(format: String?, vararg arguments: Any?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), format, arguments)
  }

  override fun trace(msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), msg, t)
  }

  override fun trace(marker: Marker?, msg: String?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), msg)
  }

  override fun trace(marker: Marker?, format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), format, arg)
  }

  override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun trace(marker: Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), format, argArray)
  }

  override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.TRACE.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isDebugEnabled is true */
  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) debug(marker, msg.toStringSafe(), t)
  }

  override fun debug(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), msg)
    }
  }

  override fun debug(format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), format, arg)
  }

  override fun debug(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun debug(format: String?, vararg arguments: Any?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), format, arguments)
  }

  override fun debug(msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), msg, t)
  }

  override fun debug(marker: Marker?, msg: String?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), msg)
  }

  override fun debug(marker: Marker?, format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), format, arg)
  }

  override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun debug(marker: Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), format, argArray)
  }

  override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.DEBUG.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isInfoEnabled is true */
  override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isInfoEnabled) info(marker, msg.toStringSafe(), t)
  }

  override fun info(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.info(msg)
    }
  }

  override fun info(format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), format, arg)
  }

  override fun info(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun info(format: String?, vararg arguments: Any?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), format, arguments)
  }

  override fun info(msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), msg, t)
  }

  override fun info(marker: Marker?, msg: String?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), msg)
  }

  override fun info(marker: Marker?, format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), format, arg)
  }

  override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun info(marker: Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), format, argArray)
  }

  override fun info(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.INFO.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isWarnEnabled is true */
  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) warn(marker, msg.toStringSafe(), t)
  }

  override fun warn(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), msg)
    }
  }

  override fun warn(format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), format, arg)
  }

  override fun warn(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun warn(format: String?, vararg arguments: Any?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), format, arguments)
  }

  override fun warn(msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), msg, t)
  }

  override fun warn(marker: Marker?, msg: String?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), msg)
  }

  override fun warn(marker: Marker?, format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), format, arg)
  }

  override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun warn(marker: Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), format, argArray)
  }

  override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.WARN.toJULLevel(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isErrorEnabled is true */
  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) error(marker, msg.toStringSafe(), t)
  }

  override fun error(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), msg)
    }
  }

  override fun error(format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), format, arg)
  }

  override fun error(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun error(format: String?, vararg arguments: Any?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), format, arguments)
  }

  override fun error(msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), msg, t)
  }

  override fun error(marker: Marker?, msg: String?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), msg)
  }

  override fun error(marker: Marker?, format: String?, arg: Any?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), format, arg)
  }

  override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), format, arrayOf(arg1, arg2))
  }

  override fun error(marker: Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), format, argArray)
  }

  override fun error(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.log(mu.two.Level.ERROR.toJULLevel(), msg, t)
  }

  override inline fun entry(vararg argArray: Any?) {
    trace("entry({})", argArray)
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
    get() = underlyingLogger.isLoggable(mu.two.Level.TRACE.toJULLevel())

  override fun isTraceEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(mu.two.Level.TRACE.toJULLevel())
  }

  override val isDebugEnabled: Boolean
    get() = underlyingLogger.isLoggable(mu.two.Level.DEBUG.toJULLevel())

  override fun isDebugEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(mu.two.Level.DEBUG.toJULLevel())
  }

  override val isInfoEnabled: Boolean
    get() = underlyingLogger.isLoggable(mu.two.Level.INFO.toJULLevel())

  override fun isInfoEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(mu.two.Level.INFO.toJULLevel())
  }

  override val isWarnEnabled: Boolean
    get() = underlyingLogger.isLoggable(mu.two.Level.WARN.toJULLevel())

  override fun isWarnEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(mu.two.Level.WARN.toJULLevel())
  }

  override val isErrorEnabled: Boolean
    get() = underlyingLogger.isLoggable(mu.two.Level.ERROR.toJULLevel())

  override fun isErrorEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isLoggable(mu.two.Level.ERROR.toJULLevel())
  }

  private fun mu.two.Level.toJULLevel(): Level {
    val julLevel: Level =
        when (this) {
          mu.two.Level.TRACE -> Level.FINEST
          mu.two.Level.DEBUG -> Level.FINE
          mu.two.Level.INFO -> Level.INFO
          mu.two.Level.WARN -> Level.WARNING
          mu.two.Level.ERROR -> Level.SEVERE
        }
    return julLevel
  }
}
