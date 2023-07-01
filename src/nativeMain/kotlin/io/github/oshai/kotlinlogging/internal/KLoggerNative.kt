package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.appender
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.formatter
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Level.*
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.isLoggingEnabled

internal class KLoggerNative(override val name: String) : KLogger {

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        val formattedMessage =
          formatter.formatMessage(appender.includePrefix, level, name, marker, this.cause) {
            this.message
          }
        when (level) {
          TRACE -> appender.trace(name, formattedMessage)
          DEBUG -> appender.debug(name, formattedMessage)
          INFO -> appender.info(name, formattedMessage)
          WARN -> appender.warn(name, formattedMessage)
          ERROR -> appender.error(name, formattedMessage)
          OFF -> Unit
        }
      }
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return level.isLoggingEnabled()
  }
}
