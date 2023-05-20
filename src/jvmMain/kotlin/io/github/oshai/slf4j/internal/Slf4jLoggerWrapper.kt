package io.github.oshai.slf4j.internal

import io.github.oshai.KLogger
import io.github.oshai.Marker
import io.github.oshai.slf4j.toSlf4j
import org.slf4j.Logger

internal abstract class Slf4jLoggerWrapper(override val underlyingLogger: Logger) : KLogger {

  override val name: String
    get() = underlyingLogger.name

  override val isTraceEnabled: Boolean
    get() = underlyingLogger.isTraceEnabled

  override fun isTraceEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isTraceEnabled(marker?.toSlf4j())
  }

  override val isDebugEnabled: Boolean
    get() = underlyingLogger.isDebugEnabled

  override fun isDebugEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isDebugEnabled(marker?.toSlf4j())
  }

  override val isInfoEnabled: Boolean
    get() = underlyingLogger.isInfoEnabled

  override fun isInfoEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isInfoEnabled(marker?.toSlf4j())
  }

  override val isWarnEnabled: Boolean
    get() = underlyingLogger.isWarnEnabled

  override fun isWarnEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isWarnEnabled(marker?.toSlf4j())
  }

  override val isErrorEnabled: Boolean
    get() = underlyingLogger.isErrorEnabled

  override fun isErrorEnabled(marker: Marker?): Boolean {
    return underlyingLogger.isErrorEnabled(marker?.toSlf4j())
  }

  override val isLoggingOff: Boolean
    get() = !underlyingLogger.isErrorEnabled

  override fun isLoggingOff(marker: Marker?): Boolean {
    return !underlyingLogger.isErrorEnabled(marker?.toSlf4j())
  }
}
