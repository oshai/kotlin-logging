package io.github.oshai.kotlinlogging.slf4j

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory
import org.slf4j.Logger
import org.slf4j.MarkerFactory

public fun Marker.toSlf4j(): org.slf4j.Marker = MarkerFactory.getMarker(this.getName())

public fun Level.toSlf4j(): org.slf4j.event.Level =
  when (this) {
    Level.TRACE -> org.slf4j.event.Level.TRACE
    Level.DEBUG -> org.slf4j.event.Level.DEBUG
    Level.INFO -> org.slf4j.event.Level.INFO
    Level.WARN -> org.slf4j.event.Level.WARN
    Level.ERROR -> org.slf4j.event.Level.ERROR
    Level.OFF -> throw IllegalArgumentException("OFF level is not supported")
  }

public fun Logger.isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
  return when (level) {
    Level.TRACE -> this.isTraceEnabled(marker?.toSlf4j())
    Level.DEBUG -> this.isDebugEnabled(marker?.toSlf4j())
    Level.INFO -> this.isInfoEnabled(marker?.toSlf4j())
    Level.WARN -> this.isWarnEnabled(marker?.toSlf4j())
    Level.ERROR -> this.isErrorEnabled(marker?.toSlf4j())
    Level.OFF -> false
  }
}

@Suppress("UnusedReceiverParameter")
public fun KotlinLogging.logger(underlyingLogger: Logger): KLogger =
  Slf4jLoggerFactory.wrapJLogger(underlyingLogger)

public fun Logger.toKLogger(): KLogger = KotlinLogging.logger(this)
