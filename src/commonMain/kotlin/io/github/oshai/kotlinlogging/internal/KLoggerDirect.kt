package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEvent
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Level.OFF
import io.github.oshai.kotlinlogging.Marker

internal class KLoggerDirect(override val name: String) : KLogger {

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        when (level) {
          OFF -> Unit
          else ->
            KotlinLoggingConfiguration.direct.appender.log(KLoggingEvent(level, marker, name, this))
        }
      }
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return level.isLoggingEnabled()
  }
}
