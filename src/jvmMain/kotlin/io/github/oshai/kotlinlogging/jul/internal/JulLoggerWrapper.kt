package io.github.oshai.kotlinlogging.jul.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level.*
import io.github.oshai.kotlinlogging.Marker
import java.util.logging.Level
import java.util.logging.Logger

internal class JulLoggerWrapper(override val underlyingLogger: Logger) :
  KLogger, DelegatingKLogger<Logger> {
  override val name: String
    get() = underlyingLogger.name

  override fun at(
    level: io.github.oshai.kotlinlogging.Level,
    marker: Marker?, // marker is not supported in JUL
    block: KLoggingEventBuilder.() -> Unit,
  ) {
    if (isLoggingEnabledFor(level, null)) {
      KLoggingEventBuilder().apply(block).run {
        underlyingLogger.log(level.toJULLevel(), message, cause)
      }
    }
  }

  override fun isLoggingEnabledFor(
    level: io.github.oshai.kotlinlogging.Level,
    marker: Marker?,
  ): Boolean {
    return underlyingLogger.isLoggable(level.toJULLevel())
  }

  private fun io.github.oshai.kotlinlogging.Level.toJULLevel(): Level {
    val julLevel: Level =
      when (this) {
        TRACE -> Level.FINEST
        DEBUG -> Level.FINE
        INFO -> Level.INFO
        WARN -> Level.WARNING
        ERROR -> Level.SEVERE
        OFF -> Level.OFF
      }
    return julLevel
  }
}
