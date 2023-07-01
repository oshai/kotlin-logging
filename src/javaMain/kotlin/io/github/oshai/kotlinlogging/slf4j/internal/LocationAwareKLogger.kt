package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
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
  KLogger, DelegatingKLogger<LocationAwareLogger> {

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
}
