package io.github.oshai.kotlinlogging.internal

import android.util.Log
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.Marker

@Suppress("TooManyFunctions")
internal class KLoggerAndroid(override val name: String) : KLogger {
  override val underlyingLogger: Any
    get() = TODO("Not yet implemented")

  override fun trace(msg: String?) {
    TODO("Not yet implemented")
  }

  override fun trace(msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun trace(msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun trace(msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun trace(msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun trace(marker: Marker?, msg: String?) {
    TODO("Not yet implemented")
  }

  override fun trace(marker: Marker?, msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun trace(marker: Marker?, msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

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
  override fun isTraceEnabled(marker: Marker?): Boolean {
    TODO("Not yet implemented")
  }

  override fun debug(msg: String?) {
    TODO("Not yet implemented")
  }

  override fun debug(msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun debug(msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun debug(msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun debug(msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun debug(marker: Marker?, msg: String?) {
    TODO("Not yet implemented")
  }

  override fun debug(marker: Marker?, msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun debug(marker: Marker?, msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) = debug(t, msg)
  override fun isDebugEnabled(marker: Marker?): Boolean {
    TODO("Not yet implemented")
  }

  override fun info(msg: String?) {
    TODO("Not yet implemented")
  }

  override fun info(msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun info(msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun info(msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun info(msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun info(marker: Marker?, msg: String?) {
    TODO("Not yet implemented")
  }

  override fun info(marker: Marker?, msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun info(marker: Marker?, msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun info(marker: Marker?, msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) = info(t, msg)
  override fun isInfoEnabled(marker: Marker?): Boolean {
    TODO("Not yet implemented")
  }

  override fun warn(msg: String?) {
    TODO("Not yet implemented")
  }

  override fun warn(msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun warn(msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun warn(msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun warn(msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun warn(marker: Marker?, msg: String?) {
    TODO("Not yet implemented")
  }

  override fun warn(marker: Marker?, msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun warn(marker: Marker?, msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) = warn(t, msg)
  override fun isWarnEnabled(marker: Marker?): Boolean {
    TODO("Not yet implemented")
  }

  override fun error(msg: String?) {
    TODO("Not yet implemented")
  }

  override fun error(msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun error(msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun error(msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun error(msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun error(marker: Marker?, msg: String?) {
    TODO("Not yet implemented")
  }

  override fun error(marker: Marker?, msg: String?, arg: Any?) {
    TODO("Not yet implemented")
  }

  override fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?) {
    TODO("Not yet implemented")
  }

  override fun error(marker: Marker?, msg: String?, vararg arguments: Any?) {
    TODO("Not yet implemented")
  }

  override fun error(marker: Marker?, msg: String?, t: Throwable?) {
    TODO("Not yet implemented")
  }

  override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) = error(t, msg)
  override fun isErrorEnabled(marker: Marker?): Boolean {
    TODO("Not yet implemented")
  }

  override fun isLoggingOff(marker: Marker?): Boolean {
    TODO("Not yet implemented")
  }

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

  /**
   * Is the logger instance OFF?
   *
   * @return True if this Logger is set to the OFF level, false otherwise.
   */
  override val isLoggingOff: Boolean = !Log.isLoggable(name, Log.ASSERT)
}
