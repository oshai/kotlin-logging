package mu.two.slf4j

import mu.two.KLogger
import mu.two.Marker
import org.slf4j.Logger

internal abstract class Slf4jLoggerWrapper(override val underlyingLogger: Logger) : mu.two.KLogger {

  override val name: String
    get() = underlyingLogger.name

  override val isTraceEnabled: Boolean
    get() = underlyingLogger.isTraceEnabled

  override fun isTraceEnabled(marker: mu.two.Marker?): Boolean {
    return underlyingLogger.isTraceEnabled(marker?.toSlf4j())
  }

  override val isDebugEnabled: Boolean
    get() = underlyingLogger.isDebugEnabled

  override fun isDebugEnabled(marker: mu.two.Marker?): Boolean {
    return underlyingLogger.isDebugEnabled(marker?.toSlf4j())
  }

  override val isInfoEnabled: Boolean
    get() = underlyingLogger.isInfoEnabled

  override fun isInfoEnabled(marker: mu.two.Marker?): Boolean {
    return underlyingLogger.isInfoEnabled(marker?.toSlf4j())
  }

  override val isWarnEnabled: Boolean
    get() = underlyingLogger.isWarnEnabled

  override fun isWarnEnabled(marker: mu.two.Marker?): Boolean {
    return underlyingLogger.isWarnEnabled(marker?.toSlf4j())
  }

  override val isErrorEnabled: Boolean
    get() = underlyingLogger.isErrorEnabled

  override fun isErrorEnabled(marker: mu.two.Marker?): Boolean {
    return underlyingLogger.isErrorEnabled(marker?.toSlf4j())
  }
}
