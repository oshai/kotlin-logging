package mu.two.internal

import mu.two.KLogger
import mu.KotlinLoggingConfiguration.APPENDER
import mu.KotlinLoggingConfiguration.FORMATTER
import mu.two.Level
import mu.two.Level.DEBUG
import mu.two.Level.ERROR
import mu.two.Level.INFO
import mu.two.Level.TRACE
import mu.two.Level.WARN
import mu.two.Marker
import mu.isLoggingEnabled

@Suppress("TooManyFunctions")
internal class KLoggerJS(override val name: String) : mu.two.KLogger {

  override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(msg, APPENDER::trace)

  override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(msg, APPENDER::debug)

  override fun info(msg: () -> Any?) = INFO.logIfEnabled(msg, APPENDER::info)

  override fun warn(msg: () -> Any?) = WARN.logIfEnabled(msg, APPENDER::warn)

  override fun error(msg: () -> Any?) = ERROR.logIfEnabled(msg, APPENDER::error)

  override fun trace(t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(msg, t, APPENDER::trace)

  override fun debug(t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(msg, t, APPENDER::debug)

  override fun info(t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(msg, t, APPENDER::info)

  override fun warn(t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(msg, t, APPENDER::warn)

  override fun error(t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(msg, t, APPENDER::error)

  override fun trace(marker: mu.two.Marker?, msg: () -> Any?) =
      TRACE.logIfEnabled(marker, msg, APPENDER::trace)

  override fun debug(marker: mu.two.Marker?, msg: () -> Any?) =
      DEBUG.logIfEnabled(marker, msg, APPENDER::debug)

  override fun info(marker: mu.two.Marker?, msg: () -> Any?) =
      INFO.logIfEnabled(marker, msg, APPENDER::info)

  override fun warn(marker: mu.two.Marker?, msg: () -> Any?) =
      WARN.logIfEnabled(marker, msg, APPENDER::warn)

  override fun error(marker: mu.two.Marker?, msg: () -> Any?) =
      ERROR.logIfEnabled(marker, msg, APPENDER::error)

  override fun trace(marker: mu.two.Marker?, t: Throwable?, msg: () -> Any?) =
      TRACE.logIfEnabled(marker, msg, t, APPENDER::trace)

  override fun debug(marker: mu.two.Marker?, t: Throwable?, msg: () -> Any?) =
      DEBUG.logIfEnabled(marker, msg, t, APPENDER::debug)

  override fun info(marker: mu.two.Marker?, t: Throwable?, msg: () -> Any?) =
      INFO.logIfEnabled(marker, msg, t, APPENDER::info)

  override fun warn(marker: mu.two.Marker?, t: Throwable?, msg: () -> Any?) =
      WARN.logIfEnabled(marker, msg, t, APPENDER::warn)

  override fun error(marker: mu.two.Marker?, t: Throwable?, msg: () -> Any?) =
      ERROR.logIfEnabled(marker, msg, t, APPENDER::error)

  private fun mu.two.Level.logIfEnabled(msg: () -> Any?, logFunction: (Any?) -> Unit) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, msg))
    }
  }

  private fun mu.two.Level.logIfEnabled(msg: () -> Any?, t: Throwable?, logFunction: (Any?) -> Unit) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, t, msg))
    }
  }

  private fun mu.two.Level.logIfEnabled(marker: mu.two.Marker?, msg: () -> Any?, logFunction: (Any?) -> Unit) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, marker, msg))
    }
  }

  private fun mu.two.Level.logIfEnabled(
      marker: mu.two.Marker?,
      msg: () -> Any?,
      t: Throwable?,
      logFunction: (Any?) -> Unit
  ) {
    if (isLoggingEnabled()) {
      logFunction(FORMATTER.formatMessage(this, name, marker, t, msg))
    }
  }

  override fun entry(vararg argArray: Any?) {
    TRACE.logIfEnabled({ "entry($argArray)" }, APPENDER::trace)
  }

  override fun exit() {
    TRACE.logIfEnabled({ "exit()" }, APPENDER::trace)
  }

  override fun <T : Any?> exit(result: T): T {
    TRACE.logIfEnabled({ "exit($result)" }, APPENDER::trace)
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    ERROR.logIfEnabled({ "throwing($throwable" }, throwable, APPENDER::error)
    return throwable
  }

  override fun <T : Throwable> catching(throwable: T) {
    ERROR.logIfEnabled({ "catching($throwable" }, throwable, APPENDER::error)
  }
}
