package io.github.oshai.kotlinlogging.irplugin

import ch.qos.logback.classic.Level

enum class TestLoggingLevel(val levelEnum: Level, val levelName: String) {
  TRACE(Level.TRACE, "trace"),
  DEBUG(Level.DEBUG, "debug"),
  INFO(Level.INFO, "info"),
  WARN(Level.WARN, "warn"),
  ERROR(Level.ERROR, "error")
}
