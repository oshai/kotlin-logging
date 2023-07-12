package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.toSlf4j
import org.slf4j.Logger

/**
 * A class wrapping a [Logger] instance that is not location aware all methods of [KLogger] has
 * default implementation the rest of the methods are delegated to [Logger] Hence no implemented
 * methods
 */
internal class LocationIgnorantKLogger(override val underlyingLogger: Logger) :
  KLogger, DelegatingKLogger<Logger>, Slf4jLogger() {

  override val name: String
    get() = underlyingLogger.name

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return isLoggingEnabledFor(underlyingLogger, level, marker)
  }

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        if (payload != null) {
          logWithPayload(this, level, marker)
        } else {
          logWithoutPayload(this, level, marker)
        }
      }
    }
  }

  private fun logWithPayload(
    kLoggingEventBuilder: KLoggingEventBuilder,
    level: Level,
    marker: Marker?
  ) {
    val builder = underlyingLogger.atLevel(level.toSlf4j())
    marker?.toSlf4j()?.let { builder.addMarker(it) }
    kLoggingEventBuilder.payload?.forEach { (key, value) -> builder.addKeyValue(key, value) }
    builder.setCause(kLoggingEventBuilder.cause)
    builder.log(kLoggingEventBuilder.message)
  }

  private fun logWithoutPayload(
    kLoggingEventBuilder: KLoggingEventBuilder,
    level: Level,
    marker: Marker?
  ) {
    when (level) {
      Level.TRACE ->
        underlyingLogger.trace(
          marker?.toSlf4j(),
          kLoggingEventBuilder.message,
          kLoggingEventBuilder.cause
        )
      Level.DEBUG ->
        underlyingLogger.debug(
          marker?.toSlf4j(),
          kLoggingEventBuilder.message,
          kLoggingEventBuilder.cause
        )
      Level.INFO ->
        underlyingLogger.info(
          marker?.toSlf4j(),
          kLoggingEventBuilder.message,
          kLoggingEventBuilder.cause
        )
      Level.WARN ->
        underlyingLogger.warn(
          marker?.toSlf4j(),
          kLoggingEventBuilder.message,
          kLoggingEventBuilder.cause
        )
      Level.ERROR ->
        underlyingLogger.error(
          marker?.toSlf4j(),
          kLoggingEventBuilder.message,
          kLoggingEventBuilder.cause
        )
      Level.OFF -> Unit
    }
  }
}
