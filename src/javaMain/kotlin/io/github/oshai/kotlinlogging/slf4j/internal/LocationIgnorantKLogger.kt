package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.isLoggingEnabledFor
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

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        val builder = underlyingLogger.atLevel(level.toSlf4j())
        marker?.toSlf4j()?.let { builder.addMarker(it) }
        payload?.forEach { (key, value) -> builder.addKeyValue(key, value) }
        builder.setCause(cause)
        builder.log(message)
      }
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return underlyingLogger.isLoggingEnabledFor(level, marker)
  }
}
