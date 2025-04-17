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

  override fun trace(msg: String?, arg: Any?) = underlyingLogger.trace(msg, arg)

  override fun trace(msg: String?, arg1: Any?, arg2: Any?) = underlyingLogger.trace(msg, arg1, arg2)

  override fun trace(msg: String?, vararg arguments: Any?) = underlyingLogger.trace(msg, *arguments)

  override fun trace(marker: Marker?, msg: String?, arg: Any?) =
    underlyingLogger.trace(marker?.toLogback(), msg, arg)

  override fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) =
    underlyingLogger.trace(marker?.toLogback(), msg, arg1, arg2)

  override fun trace(marker: Marker?, msg: String?, vararg arguments: Any?) =
    underlyingLogger.trace(marker?.toLogback(), msg, *arguments)

  override fun debug(msg: String?, arg: Any?) = underlyingLogger.debug(msg, arg)

  override fun debug(msg: String?, arg1: Any?, arg2: Any?) = underlyingLogger.debug(msg, arg1, arg2)

  override fun debug(msg: String?, vararg arguments: Any?) = underlyingLogger.debug(msg, *arguments)

  override fun debug(marker: Marker?, msg: String?, arg: Any?) =
    underlyingLogger.debug(marker?.toLogback(), msg, arg)

  override fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) =
    underlyingLogger.debug(marker?.toLogback(), msg, arg1, arg2)

  override fun debug(marker: Marker?, msg: String?, vararg arguments: Any?) =
    underlyingLogger.debug(marker?.toLogback(), msg, *arguments)

  override fun info(msg: String?, arg: Any?) = underlyingLogger.info(msg, arg)

  override fun info(msg: String?, arg1: Any?, arg2: Any?) = underlyingLogger.info(msg, arg1, arg2)

  override fun info(msg: String?, vararg arguments: Any?) = underlyingLogger.info(msg, *arguments)

  override fun info(marker: Marker?, msg: String?, arg: Any?) =
    underlyingLogger.info(marker?.toLogback(), msg, arg)

  override fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) =
    underlyingLogger.info(marker?.toLogback(), msg, arg1, arg2)

  override fun info(marker: Marker?, msg: String?, vararg arguments: Any?) =
    underlyingLogger.info(marker?.toLogback(), msg, *arguments)

  override fun warn(msg: String?, arg: Any?) = underlyingLogger.warn(msg, arg)

  override fun warn(msg: String?, arg1: Any?, arg2: Any?) = underlyingLogger.warn(msg, arg1, arg2)

  override fun warn(msg: String?, vararg arguments: Any?) = underlyingLogger.warn(msg, *arguments)

  override fun warn(marker: Marker?, msg: String?, arg: Any?) =
    underlyingLogger.warn(marker?.toLogback(), msg, arg)

  override fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) =
    underlyingLogger.warn(marker?.toLogback(), msg, arg1, arg2)

  override fun warn(marker: Marker?, msg: String?, vararg arguments: Any?) =
    underlyingLogger.warn(marker?.toLogback(), msg, *arguments)

  override fun error(msg: String?, arg: Any?) = underlyingLogger.error(msg, arg)

  override fun error(msg: String?, arg1: Any?, arg2: Any?) = underlyingLogger.error(msg, arg1, arg2)

  override fun error(msg: String?, vararg arguments: Any?) = underlyingLogger.error(msg, *arguments)

  override fun error(marker: Marker?, msg: String?, arg: Any?) =
    underlyingLogger.error(marker?.toLogback(), msg, arg)

  override fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) =
    underlyingLogger.error(marker?.toLogback(), msg, arg1, arg2)

  override fun error(marker: Marker?, msg: String?, vararg arguments: Any?) =
    underlyingLogger.error(marker?.toLogback(), msg, *arguments)
}
