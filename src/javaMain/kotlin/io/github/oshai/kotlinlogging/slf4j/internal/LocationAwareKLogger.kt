package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.internal.toStringSafe
import io.github.oshai.kotlinlogging.slf4j.isLoggingEnabledFor
import io.github.oshai.kotlinlogging.slf4j.toSlf4j
import org.slf4j.event.EventConstants
import org.slf4j.helpers.MessageFormatter
import org.slf4j.spi.LocationAwareLogger

/**
 * A class wrapping a [LocationAwareLogger] instance preserving location information with the
 * correct fully qualified class name.
 */
internal class LocationAwareKLogger(override val underlyingLogger: LocationAwareLogger) :
  KLogger, DelegatingKLogger<LocationAwareLogger>, Slf4jLogger() {

  private val fqcn: String = LocationAwareKLogger::class.java.name
  private val ENTRY = io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("ENTRY").toSlf4j()
  private val EXIT = io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("EXIT").toSlf4j()

  private val THROWING =
    io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("THROWING").toSlf4j()
  private val CATCHING =
    io.github.oshai.kotlinlogging.KMarkerFactory.getMarker("CATCHING").toSlf4j()
  private val EXITONLY = "exit"
  private val EXITMESSAGE = "exit with ({})"
  override val name: String
    get() = underlyingLogger.name

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        underlyingLogger.log(marker?.toSlf4j(), fqcn, level.toSlf4j().toInt(), message, null, cause)
      }
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return underlyingLogger.isLoggingEnabledFor(level, marker)
  }

  override fun <T : Throwable> catching(throwable: T) {
    if (underlyingLogger.isErrorEnabled) {
      underlyingLogger.log(CATCHING, fqcn, EventConstants.ERROR_INT, "catching", null, throwable)
    }
  }

  /**
   * Similar to [.isTraceEnabled] method except that the marker data is also taken into account.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   */
  override fun isTraceEnabled(marker: Marker?): Boolean = isLoggingEnabledFor(Level.TRACE, marker)

  /**
   * Similar to [.isDebugEnabled] method except that the marker data is also taken into account.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  override fun isDebugEnabled(marker: Marker?): Boolean = isLoggingEnabledFor(Level.DEBUG, marker)

  /**
   * Similar to [.isInfoEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return true if this Logger is enabled for the INFO level, false otherwise.
   */
  override fun isInfoEnabled(marker: Marker?): Boolean = isLoggingEnabledFor(Level.INFO, marker)

  /**
   * Similar to [.isWarnEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  override fun isWarnEnabled(marker: Marker?): Boolean = isLoggingEnabledFor(Level.WARN, marker)

  /**
   * Similar to [.isErrorEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  override fun isErrorEnabled(marker: Marker?): Boolean = isLoggingEnabledFor(Level.ERROR, marker)

  /**
   * Similar to [.isLoggingOff] method except that the marker data is also taken into consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is set to the OFF level, false otherwise.
   */
  override fun isLoggingOff(marker: Marker?) = !isLoggingEnabledFor(Level.ERROR, marker)

  override fun entry(vararg arguments: Any?) {
    if (underlyingLogger.isTraceEnabled(ENTRY)) {
      val tp = MessageFormatter.arrayFormat(buildMessagePattern(arguments.size), arguments)
      underlyingLogger.log(ENTRY, fqcn, EventConstants.TRACE_INT, tp.message, null, null)
    }
  }

  override fun exit() {
    if (underlyingLogger.isTraceEnabled(EXIT)) {
      underlyingLogger.log(EXIT, fqcn, EventConstants.TRACE_INT, EXITONLY, null, null)
    }
  }

  override fun <T : Any?> exit(result: T): T {
    if (underlyingLogger.isTraceEnabled(EXIT)) {
      val tp = MessageFormatter.format(EXITMESSAGE, result)
      underlyingLogger.log(
        EXIT,
        fqcn,
        EventConstants.TRACE_INT,
        tp.message,
        arrayOf<Any?>(result),
        tp.throwable
      )
    }
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    underlyingLogger.log(THROWING, fqcn, LocationAwareLogger.ERROR_INT, "throwing", null, throwable)
    return throwable
  }

  private fun buildMessagePattern(len: Int): String {
    return (1..len).joinToString(separator = ", ", prefix = "entry with (", postfix = ")") { "{}" }
  }

  /** Lazy add a log message if isTraceEnabled is true */
  override fun trace(message: () -> Any?): Unit =
    at(Level.TRACE, null) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isDebugEnabled is true */
  override fun debug(message: () -> Any?): Unit =
    at(Level.DEBUG, null) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isInfoEnabled is true */
  override fun info(message: () -> Any?): Unit =
    at(Level.INFO, null) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isWarnEnabled is true */
  override fun warn(message: () -> Any?): Unit =
    at(Level.WARN, null) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isErrorEnabled is true */
  override fun error(message: () -> Any?): Unit =
    at(Level.ERROR, null) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isTraceEnabled is true */
  override fun trace(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.TRACE) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isDebugEnabled is true */
  override fun debug(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.DEBUG, null) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isInfoEnabled is true */
  override fun info(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.INFO, null) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isWarnEnabled is true */
  override fun warn(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.WARN, null) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isErrorEnabled is true */
  override fun error(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.ERROR, null) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isTraceEnabled is true */
  override fun trace(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.TRACE, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isDebugEnabled is true */
  override fun debug(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.DEBUG, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isInfoEnabled is true */
  override fun info(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.INFO, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isWarnEnabled is true */
  override fun warn(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.WARN, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isErrorEnabled is true */
  override fun error(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.ERROR, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  override fun atTrace(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.TRACE, marker, block)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  override fun atDebug(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.DEBUG, marker, block)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  override fun atInfo(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.INFO, marker, block)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  override fun atWarn(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.WARN, marker, block)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  override fun atError(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.ERROR, marker, block)
}
