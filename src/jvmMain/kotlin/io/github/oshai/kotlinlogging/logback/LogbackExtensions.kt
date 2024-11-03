package io.github.oshai.kotlinlogging.logback

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.LogbackServiceProvider
import io.github.oshai.kotlinlogging.Level.DEBUG
import io.github.oshai.kotlinlogging.Level.ERROR
import io.github.oshai.kotlinlogging.Level.INFO
import io.github.oshai.kotlinlogging.Level.OFF
import io.github.oshai.kotlinlogging.Level.TRACE
import io.github.oshai.kotlinlogging.Level.WARN
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jMarker

public fun io.github.oshai.kotlinlogging.Level.toLogbackLevel(): Level {
  val logbackLevel: Level =
    when (this) {
      TRACE -> Level.TRACE
      DEBUG -> Level.DEBUG
      INFO -> Level.INFO
      WARN -> Level.WARN
      ERROR -> Level.ERROR
      OFF -> Level.OFF
    }
  return logbackLevel
}

public fun Marker.toLogback(logbackServiceProvider: LogbackServiceProvider): org.slf4j.Marker =
  when (this) {
    is Slf4jMarker -> marker
    else -> logbackServiceProvider.markerFactory.getMarker(getName())
  }
