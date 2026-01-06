package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.toSlf4j
import org.slf4j.Logger

public abstract class Slf4jLogger<T : Logger> : KLogger, DelegatingKLogger<T> {

  // we don't move more methods to here because if it will appear on stacktrace
  // it will break fqcn for class name in location aware loggers
  // (tests are also failing when doing this)

  protected fun isLoggingEnabledFor(
    underlyingLogger: Logger,
    level: Level,
    marker: Marker?,
  ): Boolean {
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
