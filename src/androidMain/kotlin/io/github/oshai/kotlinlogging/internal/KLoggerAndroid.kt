package io.github.oshai.kotlinlogging.internal

import android.util.Log
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker

internal class KLoggerAndroid(override val name: String) : KLogger {
  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        when (level) {
          Level.TRACE -> Log.v(name, this.message, this.cause)
          Level.DEBUG -> Log.d(name, this.message, this.cause)
          Level.INFO -> Log.i(name, this.message, this.cause)
          Level.WARN -> Log.w(name, this.message, this.cause)
          Level.ERROR -> Log.e(name, this.message, this.cause)
          Level.OFF -> Unit
        }
      }
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return when (level) {
      Level.TRACE -> Log.isLoggable(name, Log.VERBOSE)
      Level.DEBUG -> Log.isLoggable(name, Log.DEBUG)
      Level.INFO -> Log.isLoggable(name, Log.INFO)
      Level.WARN -> Log.isLoggable(name, Log.WARN)
      Level.ERROR -> Log.isLoggable(name, Log.ERROR)
      Level.OFF -> false
    }
  }
}
