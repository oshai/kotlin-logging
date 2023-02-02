@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package io.github.oshai.slf4j.internal

import io.github.oshai.KLogger
import io.github.oshai.internal.toStringSafe
import io.github.oshai.slf4j.toSlf4j
import org.slf4j.Logger

/**
 * A class wrapping a [Logger] instance that is not location aware all methods of [KLogger] has
 * default implementation the rest of the methods are delegated to [Logger] Hence no implemented
 * methods
 */
@Suppress("TooManyFunctions")
internal class LocationIgnorantKLogger(override val underlyingLogger: Logger) :
    Slf4jLoggerWrapper(underlyingLogger), io.github.oshai.KLogger {

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
  override fun trace(marker: io.github.oshai.Marker?, msg: () -> Any?) {
    if (isTraceEnabled) trace(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isDebugEnabled is true */
  override fun debug(marker: io.github.oshai.Marker?, msg: () -> Any?) {
    if (isDebugEnabled) debug(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isInfoEnabled is true */
  override fun info(marker: io.github.oshai.Marker?, msg: () -> Any?) {
    if (isInfoEnabled) info(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isWarnEnabled is true */
  override fun warn(marker: io.github.oshai.Marker?, msg: () -> Any?) {
    if (isWarnEnabled) warn(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker if isErrorEnabled is true */
  override fun error(marker: io.github.oshai.Marker?, msg: () -> Any?) {
    if (isErrorEnabled) error(marker, msg.toStringSafe())
  }

  /** Lazy add a log message with a marker and throwable payload if isTraceEnabled is true */
  override fun trace(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) {
    if (isTraceEnabled) trace(marker, msg.toStringSafe(), t)
  }

  override fun trace(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.trace(msg)
    }
  }

  override fun trace(format: String?, arg: Any?) {
    underlyingLogger.trace(format, arg)
  }

  override fun trace(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.trace(format, arg1, arg2)
  }

  override fun trace(format: String?, vararg arguments: Any?) {
    underlyingLogger.trace(format, arguments)
  }

  override fun trace(msg: String?, t: Throwable?) {
    underlyingLogger.trace(msg, t)
  }

  override fun trace(marker: io.github.oshai.Marker?, msg: String?) {
    underlyingLogger.trace(marker?.toSlf4j(), msg)
  }

  override fun trace(marker: io.github.oshai.Marker?, format: String?, arg: Any?) {
    underlyingLogger.trace(marker?.toSlf4j(), format, arg)
  }

  override fun trace(marker: io.github.oshai.Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.trace(marker?.toSlf4j(), format, arg1, arg2)
  }

  override fun trace(marker: io.github.oshai.Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.trace(marker?.toSlf4j(), format, argArray)
  }

  override fun trace(marker: io.github.oshai.Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.trace(marker?.toSlf4j(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isDebugEnabled is true */
  override fun debug(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) debug(marker, msg.toStringSafe(), t)
  }

  override fun debug(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.debug(msg)
    }
  }

  override fun debug(format: String?, arg: Any?) {
    underlyingLogger.debug(format, arg)
  }

  override fun debug(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.debug(format, arg1, arg2)
  }

  override fun debug(format: String?, vararg arguments: Any?) {
    underlyingLogger.debug(format, arguments)
  }

  override fun debug(msg: String?, t: Throwable?) {
    underlyingLogger.debug(msg, t)
  }

  override fun debug(marker: io.github.oshai.Marker?, msg: String?) {
    underlyingLogger.debug(marker?.toSlf4j(), msg)
  }

  override fun debug(marker: io.github.oshai.Marker?, format: String?, arg: Any?) {
    underlyingLogger.debug(marker?.toSlf4j(), format, arg)
  }

  override fun debug(marker: io.github.oshai.Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.debug(marker?.toSlf4j(), format, arg1, arg2)
  }

  override fun debug(marker: io.github.oshai.Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.debug(marker?.toSlf4j(), format, argArray)
  }

  override fun debug(marker: io.github.oshai.Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.debug(marker?.toSlf4j(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isInfoEnabled is true */
  override fun info(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) {
    if (isInfoEnabled) info(marker, msg.toStringSafe(), t)
  }

  override fun info(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.info(msg)
    }
  }

  override fun info(format: String?, arg: Any?) {
    underlyingLogger.info(format, arg)
  }

  override fun info(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.info(format, arg1, arg2)
  }

  override fun info(format: String?, vararg arguments: Any?) {
    underlyingLogger.info(format, arguments)
  }

  override fun info(msg: String?, t: Throwable?) {
    underlyingLogger.info(msg, t)
  }

  override fun info(marker: io.github.oshai.Marker?, msg: String?) {
    underlyingLogger.info(marker?.toSlf4j(), msg)
  }

  override fun info(marker: io.github.oshai.Marker?, format: String?, arg: Any?) {
    underlyingLogger.info(marker?.toSlf4j(), format, arg)
  }

  override fun info(marker: io.github.oshai.Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.info(marker?.toSlf4j(), format, arg1, arg2)
  }

  override fun info(marker: io.github.oshai.Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.info(marker?.toSlf4j(), format, argArray)
  }

  override fun info(marker: io.github.oshai.Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.info(marker?.toSlf4j(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isWarnEnabled is true */
  override fun warn(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) warn(marker, msg.toStringSafe(), t)
  }

  override fun warn(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.warn(msg)
    }
  }

  override fun warn(format: String?, arg: Any?) {
    underlyingLogger.warn(format, arg)
  }

  override fun warn(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.warn(format, arg1, arg2)
  }

  override fun warn(format: String?, vararg arguments: Any?) {
    underlyingLogger.warn(format, arguments)
  }

  override fun warn(msg: String?, t: Throwable?) {
    underlyingLogger.warn(msg, t)
  }

  override fun warn(marker: io.github.oshai.Marker?, msg: String?) {
    underlyingLogger.warn(marker?.toSlf4j(), msg)
  }

  override fun warn(marker: io.github.oshai.Marker?, format: String?, arg: Any?) {
    underlyingLogger.warn(marker?.toSlf4j(), format, arg)
  }

  override fun warn(marker: io.github.oshai.Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.warn(marker?.toSlf4j(), format, arg1, arg2)
  }

  override fun warn(marker: io.github.oshai.Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.warn(marker?.toSlf4j(), format, argArray)
  }

  override fun warn(marker: io.github.oshai.Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.warn(marker?.toSlf4j(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isErrorEnabled is true */
  override fun error(marker: io.github.oshai.Marker?, t: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) error(marker, msg.toStringSafe(), t)
  }

  override fun error(msg: String?) {
    if (isTraceEnabled) {
      underlyingLogger.error(msg)
    }
  }

  override fun error(format: String?, arg: Any?) {
    underlyingLogger.error(format, arg)
  }

  override fun error(format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.error(format, arg1, arg2)
  }

  override fun error(format: String?, vararg arguments: Any?) {
    underlyingLogger.error(format, arguments)
  }

  override fun error(msg: String?, t: Throwable?) {
    underlyingLogger.error(msg, t)
  }

  override fun error(marker: io.github.oshai.Marker?, msg: String?) {
    underlyingLogger.error(marker?.toSlf4j(), msg)
  }

  override fun error(marker: io.github.oshai.Marker?, format: String?, arg: Any?) {
    underlyingLogger.error(marker?.toSlf4j(), format, arg)
  }

  override fun error(marker: io.github.oshai.Marker?, format: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.error(marker?.toSlf4j(), format, arg1, arg2)
  }

  override fun error(marker: io.github.oshai.Marker?, format: String?, vararg argArray: Any?) {
    underlyingLogger.error(marker?.toSlf4j(), format, argArray)
  }

  override fun error(marker: io.github.oshai.Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.error(marker?.toSlf4j(), msg, t)
  }

  override inline fun entry(vararg argArray: Any?) {
    if (underlyingLogger.isTraceEnabled) {
      underlyingLogger.trace("entry({})", argArray)
    }
  }

  override inline fun exit() {
    if (underlyingLogger.isTraceEnabled) {
      underlyingLogger.trace("exit")
    }
  }

  override inline fun <T : Any?> exit(result: T): T {
    if (underlyingLogger.isTraceEnabled) {
      underlyingLogger.trace("exit({})", result)
    }
    return result
  }

  override inline fun <T : Throwable> throwing(throwable: T): T {
    if (underlyingLogger.isErrorEnabled) {
      underlyingLogger.error("throwing($throwable)", throwable)
    }
    return throwable
  }

  override inline fun <T : Throwable> catching(throwable: T) {
    if (underlyingLogger.isErrorEnabled) {
      underlyingLogger.error("catching($throwable)", throwable)
    }
  }
}
