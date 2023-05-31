package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.internal.toStringSafe
import io.github.oshai.kotlinlogging.slf4j.toSlf4j
import org.slf4j.helpers.MessageFormatter
import org.slf4j.spi.LocationAwareLogger

/**
 * A class wrapping a [LocationAwareLogger] instance preserving location information with the
 * correct fully qualified class name.
 */
@Suppress("VariableNaming", "TooManyFunctions")
internal class LocationAwareKLogger(override val underlyingLogger: LocationAwareLogger) :
  Slf4jLoggerWrapper(underlyingLogger), io.github.oshai.kotlinlogging.KLogger {

  private val fqcn: String = LocationAwareKLogger::class.java.name
  private val ENTRY = io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("ENTRY").toSlf4j()
  private val EXIT = io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("EXIT").toSlf4j()

  private val THROWING =
    io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("THROWING").toSlf4j()
  private val CATCHING =
    io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("CATCHING").toSlf4j()
  private val EXITONLY = "exit"
  private val EXITMESSAGE = "exit with ({})"

  override fun trace(msg: String?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.TRACE_INT, msg, null, null)
  }

  override fun trace(msg: String?, arg: Any?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.TRACE_INT, msg, arrayOf(arg), null)
  }

  override fun trace(msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.TRACE_INT, msg, arrayOf(arg1, arg2), null)
  }

  override fun trace(msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.TRACE_INT, msg, arguments, null)
  }

  override fun trace(msg: String?, t: Throwable?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.TRACE_INT, msg, null, t)
  }

  override fun trace(marker: Marker?, msg: String?) {
    if (!underlyingLogger.isTraceEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.TRACE_INT, msg, null, null)
  }

  override fun trace(marker: Marker?, msg: String?, arg: Any?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.TRACE_INT,
      msg,
      arrayOf(arg),
      null
    )
  }

  override fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.TRACE_INT,
      msg,
      arrayOf(arg1, arg2),
      null
    )
  }

  override fun trace(marker: Marker?, msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isTraceEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.TRACE_INT,
      msg,
      arguments,
      null
    )
  }

  override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
    if (!underlyingLogger.isTraceEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.TRACE_INT, msg, null, t)
  }

  override fun debug(msg: String?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, msg, null, null)
  }

  override fun debug(msg: String?, arg: Any?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, msg, arrayOf(arg), null)
  }

  override fun debug(msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, msg, arrayOf(arg1, arg2), null)
  }

  override fun debug(msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, msg, arguments, null)
  }

  override fun debug(msg: String?, t: Throwable?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, msg, null, t)
  }

  override fun debug(marker: Marker?, msg: String?) {
    if (!underlyingLogger.isDebugEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.DEBUG_INT, msg, null, null)
  }

  override fun debug(marker: Marker?, msg: String?, arg: Any?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.DEBUG_INT,
      msg,
      arrayOf(arg),
      null
    )
  }

  override fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.DEBUG_INT,
      msg,
      arrayOf(arg1, arg2),
      null
    )
  }

  override fun debug(marker: Marker?, msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isDebugEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.DEBUG_INT,
      msg,
      arguments,
      null
    )
  }

  override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
    if (!underlyingLogger.isDebugEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.DEBUG_INT, msg, null, t)
  }

  override fun info(msg: String?) {
    if (!underlyingLogger.isInfoEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.INFO_INT, msg, null, null)
  }

  override fun info(msg: String?, arg: Any?) {
    if (!underlyingLogger.isInfoEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.INFO_INT, msg, arrayOf(arg), null)
  }

  override fun info(msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isInfoEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.INFO_INT, msg, arrayOf(arg1, arg2), null)
  }

  override fun info(msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isInfoEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.INFO_INT, msg, arguments, null)
  }

  override fun info(msg: String?, t: Throwable?) {
    if (!underlyingLogger.isInfoEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.INFO_INT, msg, null, t)
  }

  override fun info(marker: Marker?, msg: String?) {
    if (!underlyingLogger.isInfoEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.INFO_INT, msg, null, null)
  }

  override fun info(marker: Marker?, msg: String?, arg: Any?) {
    if (!underlyingLogger.isInfoEnabled) return
    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.INFO_INT,
      msg,
      arrayOf(arg),
      null
    )
  }

  override fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isInfoEnabled) return
    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.INFO_INT,
      msg,
      arrayOf(arg1, arg2),
      null
    )
  }

  override fun info(marker: Marker?, msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isInfoEnabled) return
    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.INFO_INT,
      msg,
      arguments,
      null
    )
  }

  override fun info(marker: Marker?, msg: String?, t: Throwable?) {
    if (!underlyingLogger.isInfoEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.INFO_INT, msg, null, t)
  }

  override fun warn(msg: String?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.WARN_INT, msg, null, null)
  }

  override fun warn(msg: String?, arg: Any?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.WARN_INT, msg, arrayOf(arg), null)
  }

  override fun warn(msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.WARN_INT, msg, arrayOf(arg1, arg2), null)
  }

  override fun warn(msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.WARN_INT, msg, arguments, null)
  }

  override fun warn(msg: String?, t: Throwable?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.WARN_INT, msg, null, t)
  }

  override fun warn(marker: Marker?, msg: String?) {
    if (!underlyingLogger.isWarnEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.WARN_INT, msg, null, null)
  }

  override fun warn(marker: Marker?, msg: String?, arg: Any?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.WARN_INT,
      msg,
      arrayOf(arg),
      null
    )
  }

  override fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.WARN_INT,
      msg,
      arrayOf(arg1, arg2),
      null
    )
  }

  override fun warn(marker: Marker?, msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isWarnEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.WARN_INT,
      msg,
      arguments,
      null
    )
  }

  override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
    if (!underlyingLogger.isWarnEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.WARN_INT, msg, null, t)
  }

  override fun error(msg: String?) {
    if (!underlyingLogger.isErrorEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.ERROR_INT, msg, null, null)
  }

  override fun error(msg: String?, arg: Any?) {
    if (!underlyingLogger.isErrorEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.ERROR_INT, msg, arrayOf(arg), null)
  }

  override fun error(msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isErrorEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.ERROR_INT, msg, arrayOf(arg1, arg2), null)
  }

  override fun error(msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isErrorEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.ERROR_INT, msg, arguments, null)
  }

  override fun error(msg: String?, t: Throwable?) {
    if (!underlyingLogger.isErrorEnabled) return

    underlyingLogger.log(null, fqcn, LocationAwareLogger.ERROR_INT, msg, null, t)
  }

  override fun error(marker: Marker?, msg: String?) {
    if (!underlyingLogger.isErrorEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.ERROR_INT, msg, null, null)
  }

  override fun error(marker: Marker?, msg: String?, arg: Any?) {
    if (!underlyingLogger.isErrorEnabled) return
    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.ERROR_INT,
      msg,
      arrayOf(arg),
      null
    )
  }

  override fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    if (!underlyingLogger.isErrorEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.ERROR_INT,
      msg,
      arrayOf(arg1, arg2),
      null
    )
  }

  override fun error(marker: Marker?, msg: String?, vararg arguments: Any?) {
    if (!underlyingLogger.isErrorEnabled) return

    underlyingLogger.log(
      marker?.toSlf4j(),
      fqcn,
      LocationAwareLogger.ERROR_INT,
      msg,
      arguments,
      null
    )
  }

  override fun error(marker: Marker?, msg: String?, t: Throwable?) {
    if (!underlyingLogger.isErrorEnabled) return
    underlyingLogger.log(marker?.toSlf4j(), fqcn, LocationAwareLogger.ERROR_INT, msg, null, t)
  }

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

  /** Lazy add a log message with a marker and throwable payload if isDebugEnabled is true */
  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) debug(marker, msg.toStringSafe(), t)
  }

  /** Lazy add a log message with a marker and throwable payload if isInfoEnabled is true */
  override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isInfoEnabled) info(marker, msg.toStringSafe(), t)
  }

  /** Lazy add a log message with a marker and throwable payload if isWarnEnabled is true */
  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) warn(marker, msg.toStringSafe(), t)
  }

  /** Lazy add a log message with a marker and throwable payload if isErrorEnabled is true */
  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) error(marker, msg.toStringSafe(), t)
  }

  override fun <T : Throwable> catching(throwable: T) {
    if (underlyingLogger.isErrorEnabled) {
      underlyingLogger.log(
        CATCHING,
        fqcn,
        LocationAwareLogger.ERROR_INT,
        "catching",
        null,
        throwable
      )
    }
  }

  override fun entry(vararg arguments: Any?) {
    if (underlyingLogger.isTraceEnabled(ENTRY)) {
      val tp = MessageFormatter.arrayFormat(buildMessagePattern(arguments.size), arguments)
      underlyingLogger.log(ENTRY, fqcn, LocationAwareLogger.TRACE_INT, tp.message, null, null)
    }
  }

  override fun exit() {
    if (underlyingLogger.isTraceEnabled(EXIT)) {
      underlyingLogger.log(EXIT, fqcn, LocationAwareLogger.TRACE_INT, EXITONLY, null, null)
    }
  }

  override fun <T : Any?> exit(result: T): T {
    if (underlyingLogger.isTraceEnabled(EXIT)) {
      val tp = MessageFormatter.format(EXITMESSAGE, result)
      underlyingLogger.log(
        EXIT,
        fqcn,
        LocationAwareLogger.TRACE_INT,
        tp.message,
        arrayOf<Any?>(result),
        tp.throwable
      )
    }
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    underlyingLogger.log(THROWING, fqcn, LocationAwareLogger.ERROR_INT, "throwing", null, throwable)
    throw throwable
  }

  private fun buildMessagePattern(len: Int): String {
    return (1..len).joinToString(separator = ", ", prefix = "entry with (", postfix = ")") { "{}" }
  }
}
