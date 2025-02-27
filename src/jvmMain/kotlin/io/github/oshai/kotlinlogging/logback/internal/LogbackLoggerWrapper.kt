package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Logger
import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.logback.toLogback
import io.github.oshai.kotlinlogging.logback.toLogbackLevel
import io.github.oshai.kotlinlogging.slf4j.internal.LocationAwareKLogger
import org.slf4j.event.KeyValuePair

internal class LogbackLoggerWrapper(override val underlyingLogger: Logger) :
  KLogger, DelegatingKLogger<Logger> {

  override val name: String
    get() = underlyingLogger.name

  private val fqcn: String = LocationAwareKLogger::class.java.name

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        val logbackEvent =
          LogbackLogEvent(
            fqcn = fqcn,
            logger = underlyingLogger,
            level = level,
            kLoggingEvent = this,
          )
        marker?.toLogback()?.let { logbackEvent.addMarker(it) }
        payload?.forEach { (key, value) -> logbackEvent.addKeyValuePair(KeyValuePair(key, value)) }
        underlyingLogger.callAppenders(logbackEvent)
      }
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?) =
    underlyingLogger.isEnabledFor(marker?.toLogback(), level.toLogbackLevel())
}
