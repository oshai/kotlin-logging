package io.github.oshai.kotlinlogging.internal

import android.util.Log
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.Marker

@Suppress("TooManyFunctions")
internal class KLoggerAndroid(override val name: String) : KLogger {

  override fun trace(msg: () -> Any?) {
    if (isTraceEnabled) {
      Log.v(name, msg.toStringSafe())
    }
  }

  override fun debug(msg: () -> Any?) {
    if (isDebugEnabled) {
      Log.d(name, msg.toStringSafe())
    }
  }
  override fun info(msg: () -> Any?) {
    if (isInfoEnabled) {
      Log.i(name, msg.toStringSafe())
    }
  }
  override fun warn(msg: () -> Any?) {
    if (isWarnEnabled) {
      Log.w(name, msg.toStringSafe())
    }
  }
  override fun error(msg: () -> Any?) {
    if (isErrorEnabled) {
      Log.e(name, msg.toStringSafe())
    }
  }
  override fun trace(t: Throwable?, msg: () -> Any?) {
    if (isTraceEnabled) {
      Log.v(name, msg.toStringSafe(), t)
    }
  }
  override fun debug(t: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) {
      Log.d(name, msg.toStringSafe(), t)
    }
  }
  override fun info(t: Throwable?, msg: () -> Any?) {
    if (isInfoEnabled) {
      Log.i(name, msg.toStringSafe(), t)
    }
  }
  override fun warn(t: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) {
      Log.w(name, msg.toStringSafe(), t)
    }
  }
  override fun error(t: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) {
      Log.e(name, msg.toStringSafe(), t)
    }
  }
  override fun trace(marker: Marker?, msg: () -> Any?) = trace(msg)

  override fun debug(marker: Marker?, msg: () -> Any?) = debug(msg)

  override fun info(marker: Marker?, msg: () -> Any?) = info(msg)

  override fun warn(marker: Marker?, msg: () -> Any?) = warn(msg)

  override fun error(marker: Marker?, msg: () -> Any?) = error(msg)

  override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) = trace(t, msg)

  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) = debug(t, msg)

  override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) = info(t, msg)

  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) = warn(t, msg)

  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) = error(t, msg)

  override fun entry(vararg arguments: Any?) = trace { "entry($arguments)" }

  override fun exit() = trace { "exit()" }

  override fun <T : Any?> exit(result: T): T {
    trace { "exit($result)" }
    return result
  }

  override fun <T : Throwable> throwing(throwable: T): T {
    error(throwable) { "throwing($throwable)" }
    return throwable
  }

  override fun <T : Throwable> catching(throwable: T) {
    error(throwable) { "catching($throwable)" }
  }

  /**
   * Is the logger instance enabled for the TRACE level?
   *
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   */
  override val isTraceEnabled: Boolean = Log.isLoggable(name, Log.VERBOSE)

  /**
   * Is the logger instance enabled for the DEBUG level?
   *
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  override val isDebugEnabled: Boolean = Log.isLoggable(name, Log.DEBUG)

  /**
   * Is the logger instance enabled for the INFO level?
   *
   * @return True if this Logger is enabled for the INFO level, false otherwise.
   */
  override val isInfoEnabled: Boolean = Log.isLoggable(name, Log.INFO)

  /**
   * Is the logger instance enabled for the WARN level?
   *
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  override val isWarnEnabled: Boolean = Log.isLoggable(name, Log.WARN)

  /**
   * Is the logger instance enabled for the ERROR level?
   *
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  override val isErrorEnabled: Boolean = Log.isLoggable(name, Log.ERROR)
}
