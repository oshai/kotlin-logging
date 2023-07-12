package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.toSlf4j
import org.slf4j.Logger
import org.slf4j.spi.CallerBoundaryAware

public abstract class Slf4jLogger<T : Logger> : KLogger, DelegatingKLogger<T> {

  override val name: String
    get() = underlyingLogger.name

  protected abstract val fqcn: String?
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

  protected abstract fun logWithoutPayload(
    kLoggingEventBuilder: KLoggingEventBuilder,
    level: Level,
    marker: Marker?
  )

  private fun logWithPayload(
    kLoggingEventBuilder: KLoggingEventBuilder,
    level: Level,
    marker: Marker?
  ) {
    val builder = underlyingLogger.atLevel(level.toSlf4j())
    marker?.toSlf4j()?.let { builder.addMarker(it) }
    kLoggingEventBuilder.payload?.forEach { (key, value) -> builder.addKeyValue(key, value) }
    builder.setCause(kLoggingEventBuilder.cause)
    if (builder is CallerBoundaryAware) {
      builder.setCallerBoundary(fqcn)
    }
    builder.log(kLoggingEventBuilder.message)
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return when (level) {
      Level.TRACE -> underlyingLogger.isTraceEnabled(marker?.toSlf4j())
      Level.DEBUG -> underlyingLogger.isDebugEnabled(marker?.toSlf4j())
      Level.INFO -> underlyingLogger.isInfoEnabled(marker?.toSlf4j())
      Level.WARN -> underlyingLogger.isWarnEnabled(marker?.toSlf4j())
      Level.ERROR -> underlyingLogger.isErrorEnabled(marker?.toSlf4j())
      Level.OFF -> false
    }
  }
}
