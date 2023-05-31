@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.internal.toStringSafe
import io.github.oshai.kotlinlogging.slf4j.toSlf4j
import org.slf4j.Logger

/**
 * A class wrapping a [Logger] instance that is not location aware all methods of [KLogger] has
 * default implementation the rest of the methods are delegated to [Logger] Hence no implemented
 * methods
 */
@Suppress("TooManyFunctions")
internal class LocationIgnorantKLogger(override val underlyingLogger: Logger) :
  Slf4jLoggerWrapper(underlyingLogger), KLogger {

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
      underlyingLogger.trace(msg)
    }
  }

  override fun trace(msg: String?, arg: Any?) {
    underlyingLogger.trace(msg, arg)
  }

  override fun trace(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.trace(msg, arg1, arg2)
  }

  override fun trace(msg: String?, vararg arguments: Any?) {
    underlyingLogger.trace(msg, arguments)
  }

  override fun trace(msg: String?, t: Throwable?) {
    underlyingLogger.trace(msg, t)
  }

  override fun trace(marker: Marker?, msg: String?) {
    underlyingLogger.trace(marker?.toSlf4j(), msg)
  }

  override fun trace(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.trace(marker?.toSlf4j(), msg, arg)
  }

  override fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.trace(marker?.toSlf4j(), msg, arg1, arg2)
  }

  override fun trace(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.trace(marker?.toSlf4j(), msg, arguments)
  }

  override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.trace(marker?.toSlf4j(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isDebugEnabled is true */
  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) debug(marker, msg.toStringSafe(), t)
  }

  override fun debug(msg: String?) {
    if (isDebugEnabled) {
      underlyingLogger.debug(msg)
    }
  }

  override fun debug(msg: String?, arg: Any?) {
    underlyingLogger.debug(msg, arg)
  }

  override fun debug(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.debug(msg, arg1, arg2)
  }

  override fun debug(msg: String?, vararg arguments: Any?) {
    underlyingLogger.debug(msg, arguments)
  }

  override fun debug(msg: String?, t: Throwable?) {
    underlyingLogger.debug(msg, t)
  }

  override fun debug(marker: Marker?, msg: String?) {
    underlyingLogger.debug(marker?.toSlf4j(), msg)
  }

  override fun debug(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.debug(marker?.toSlf4j(), msg, arg)
  }

  override fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.debug(marker?.toSlf4j(), msg, arg1, arg2)
  }

  override fun debug(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.debug(marker?.toSlf4j(), msg, arguments)
  }

  override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.debug(marker?.toSlf4j(), msg, t)
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
    underlyingLogger.info(msg, arg)
  }

  override fun info(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.info(msg, arg1, arg2)
  }

  override fun info(msg: String?, vararg arguments: Any?) {
    underlyingLogger.info(msg, arguments)
  }

  override fun info(msg: String?, t: Throwable?) {
    underlyingLogger.info(msg, t)
  }

  override fun info(marker: Marker?, msg: String?) {
    underlyingLogger.info(marker?.toSlf4j(), msg)
  }

  override fun info(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.info(marker?.toSlf4j(), msg, arg)
  }

  override fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.info(marker?.toSlf4j(), msg, arg1, arg2)
  }

  override fun info(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.info(marker?.toSlf4j(), msg, arguments)
  }

  override fun info(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.info(marker?.toSlf4j(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isWarnEnabled is true */
  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) warn(marker, msg.toStringSafe(), t)
  }

  override fun warn(msg: String?) {
    if (isWarnEnabled) {
      underlyingLogger.warn(msg)
    }
  }

  override fun warn(msg: String?, arg: Any?) {
    underlyingLogger.warn(msg, arg)
  }

  override fun warn(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.warn(msg, arg1, arg2)
  }

  override fun warn(msg: String?, vararg arguments: Any?) {
    underlyingLogger.warn(msg, arguments)
  }

  override fun warn(msg: String?, t: Throwable?) {
    underlyingLogger.warn(msg, t)
  }

  override fun warn(marker: Marker?, msg: String?) {
    underlyingLogger.warn(marker?.toSlf4j(), msg)
  }

  override fun warn(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.warn(marker?.toSlf4j(), msg, arg)
  }

  override fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.warn(marker?.toSlf4j(), msg, arg1, arg2)
  }

  override fun warn(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.warn(marker?.toSlf4j(), msg, arguments)
  }

  override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.warn(marker?.toSlf4j(), msg, t)
  }

  /** Lazy add a log message with a marker and throwable payload if isErrorEnabled is true */
  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) error(marker, msg.toStringSafe(), t)
  }

  override fun error(msg: String?) {
    if (isErrorEnabled) {
      underlyingLogger.error(msg)
    }
  }

  override fun error(msg: String?, arg: Any?) {
    underlyingLogger.error(msg, arg)
  }

  override fun error(msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.error(msg, arg1, arg2)
  }

  override fun error(msg: String?, vararg arguments: Any?) {
    underlyingLogger.error(msg, arguments)
  }

  override fun error(msg: String?, t: Throwable?) {
    underlyingLogger.error(msg, t)
  }

  override fun error(marker: Marker?, msg: String?) {
    underlyingLogger.error(marker?.toSlf4j(), msg)
  }

  override fun error(marker: Marker?, msg: String?, arg: Any?) {
    underlyingLogger.error(marker?.toSlf4j(), msg, arg)
  }

  override fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    underlyingLogger.error(marker?.toSlf4j(), msg, arg1, arg2)
  }

  override fun error(marker: Marker?, msg: String?, vararg arguments: Any?) {
    underlyingLogger.error(marker?.toSlf4j(), msg, arguments)
  }

  override fun error(marker: Marker?, msg: String?, t: Throwable?) {
    underlyingLogger.error(marker?.toSlf4j(), msg, t)
  }

  override inline fun entry(vararg arguments: Any?) {
    if (underlyingLogger.isTraceEnabled) {
      underlyingLogger.trace("entry({})", arguments)
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
