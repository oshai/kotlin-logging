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
  KLogger, DelegatingKLogger<Logger>, Slf4jLogger<Logger>() {

  override val fqcn: String?
    get() = null

  override fun logWithoutPayload(
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
