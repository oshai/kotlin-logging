package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.APPENDER
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.FORMATTER
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Level.DEBUG
import io.github.oshai.kotlinlogging.Level.ERROR
import io.github.oshai.kotlinlogging.Level.INFO
import io.github.oshai.kotlinlogging.Level.OFF
import io.github.oshai.kotlinlogging.Level.TRACE
import io.github.oshai.kotlinlogging.Level.WARN
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.isLoggingEnabled

internal class KLoggerJS(override val name: String) : KLogger {

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        when (level) {
          TRACE -> APPENDER.trace(FORMATTER.formatMessage(level, name, marker, cause, payload) { message })
          DEBUG -> APPENDER.debug(FORMATTER.formatMessage(level, name, marker, cause, payload) { message })
          INFO -> APPENDER.info(FORMATTER.formatMessage(level, name, marker, cause, payload) { message })
          WARN -> APPENDER.warn(FORMATTER.formatMessage(level, name, marker, cause, payload) { message })
          ERROR -> APPENDER.error(FORMATTER.formatMessage(level, name, marker, cause, payload) { message })
          OFF -> Unit
        }
      }
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return level.isLoggingEnabled()
  }
}
