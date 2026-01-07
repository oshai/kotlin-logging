@file:Suppress("MoveLambdaOutsideParentheses")

package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.toStringSafe

/**
 * A Logger interface with Lazy message evaluation example:
 * ```
 * logger.info{"this is $lazy evaluated string"}
 * ```
 */
public interface KLogger {

  /**
   * Return the name of this `Logger` instance.
   *
   * @return name of this logger instance
   */
  public val name: String

  // region Level Checks
  /**
   * Similar to [.isTraceEnabled] method except that the marker data is also taken into account.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   */
  public fun isTraceEnabled(marker: Marker? = null): Boolean =
    isLoggingEnabledFor(Level.TRACE, marker)

  /**
   * Similar to [.isDebugEnabled] method except that the marker data is also taken into account.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  public fun isDebugEnabled(marker: Marker? = null): Boolean =
    isLoggingEnabledFor(Level.DEBUG, marker)

  /**
   * Similar to [.isInfoEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return true if this Logger is enabled for the INFO level, false otherwise.
   */
  public fun isInfoEnabled(marker: Marker? = null): Boolean =
    isLoggingEnabledFor(Level.INFO, marker)

  /**
   * Similar to [.isWarnEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  public fun isWarnEnabled(marker: Marker? = null): Boolean =
    isLoggingEnabledFor(Level.WARN, marker)

  /**
   * Similar to [.isErrorEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  public fun isErrorEnabled(marker: Marker? = null): Boolean =
    isLoggingEnabledFor(Level.ERROR, marker)

  /**
   * Similar to [.isLoggingOff] method except that the marker data is also taken into consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is set to the OFF level, false otherwise.
   */
  public fun isLoggingOff(marker: Marker? = null): Boolean =
    !isLoggingEnabledFor(Level.ERROR, marker)

  /**
   * Check if the logger is enabled for the specified level
   *
   * @param level The level to check
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the specified level, false otherwise.
   */
  public fun isLoggingEnabledFor(level: Level, marker: Marker? = null): Boolean
  // endregion

  // region Trace
  /**
   * Lazy add a log message if isTraceEnabled is true
   *
   * @param message The message to log
   */
  public fun trace(message: () -> Any?): Unit = trace(null, null, message)

  /**
   * Lazy add a log message if isTraceEnabled is true
   *
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun trace(throwable: Throwable?, message: () -> Any?): Unit =
    trace(null, throwable, message)

  /**
   * Lazy add a log message with a marker if isTraceEnabled is true
   *
   * @param marker The marker to log
   * @param message The message to log
   */
  public fun trace(marker: Marker?, message: () -> Any?): Unit = trace(marker, null, message)

  /**
   * Lazy add a log message with a marker and throwable if isTraceEnabled is true
   *
   * @param marker The marker to log
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun trace(marker: Marker?, throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.TRACE, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }
  // endregion

  // region Debug
  /**
   * Lazy add a log message if isDebugEnabled is true
   *
   * @param message The message to log
   */
  public fun debug(message: () -> Any?): Unit = debug(null, null, message)

  /**
   * Lazy add a log message if isDebugEnabled is true
   *
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun debug(throwable: Throwable?, message: () -> Any?): Unit =
    debug(null, throwable, message)

  /**
   * Lazy add a log message with a marker if isDebugEnabled is true
   *
   * @param marker The marker to log
   * @param message The message to log
   */
  public fun debug(marker: Marker?, message: () -> Any?): Unit = debug(marker, null, message)

  /**
   * Lazy add a log message with a marker and throwable if isDebugEnabled is true
   *
   * @param marker The marker to log
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun debug(marker: Marker?, throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.DEBUG, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }
  // endregion

  // region Info
  /**
   * Lazy add a log message if isInfoEnabled is true
   *
   * @param message The message to log
   */
  public fun info(message: () -> Any?): Unit = info(null, null, message)

  /**
   * Lazy add a log message if isInfoEnabled is true
   *
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun info(throwable: Throwable?, message: () -> Any?): Unit = info(null, throwable, message)

  /**
   * Lazy add a log message with a marker if isInfoEnabled is true
   *
   * @param marker The marker to log
   * @param message The message to log
   */
  public fun info(marker: Marker?, message: () -> Any?): Unit = info(marker, null, message)

  /**
   * Lazy add a log message with a marker and throwable if isInfoEnabled is true
   *
   * @param marker The marker to log
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun info(marker: Marker?, throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.INFO, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }
  // endregion

  // region Warn
  /**
   * Lazy add a log message if isWarnEnabled is true
   *
   * @param message The message to log
   */
  public fun warn(message: () -> Any?): Unit = warn(null, null, message)

  /**
   * Lazy add a log message if isWarnEnabled is true
   *
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun warn(throwable: Throwable?, message: () -> Any?): Unit = warn(null, throwable, message)

  /**
   * Lazy add a log message with a marker if isWarnEnabled is true
   *
   * @param marker The marker to log
   * @param message The message to log
   */
  public fun warn(marker: Marker?, message: () -> Any?): Unit = warn(marker, null, message)

  /**
   * Lazy add a log message with a marker and throwable if isWarnEnabled is true
   *
   * @param marker The marker to log
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun warn(marker: Marker?, throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.WARN, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }
  // endregion

  // region Error
  /**
   * Lazy add a log message if isErrorEnabled is true
   *
   * @param message The message to log
   */
  public fun error(message: () -> Any?): Unit = error(null, null, message)

  /**
   * Lazy add a log message if isErrorEnabled is true
   *
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun error(throwable: Throwable?, message: () -> Any?): Unit =
    error(null, throwable, message)

  /**
   * Lazy add a log message with a marker if isErrorEnabled is true
   *
   * @param marker The marker to log
   * @param message The message to log
   */
  public fun error(marker: Marker?, message: () -> Any?): Unit = error(marker, null, message)

  /**
   * Lazy add a log message with a marker and throwable if isErrorEnabled is true
   *
   * @param marker The marker to log
   * @param throwable The throwable to log
   * @param message The message to log
   */
  public fun error(marker: Marker?, throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.ERROR, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }
  // endregion

  // region Fluent API
  /**
   * Lazy add a log message with throwable payload if isTraceEnabled is true
   *
   * @param marker The marker to log
   * @param block The block to log
   */
  public fun atTrace(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.TRACE, marker, block)

  /**
   * Lazy add a log message with throwable payload if isTraceEnabled is true
   *
   * @param block The block to log
   */
  public fun atTrace(block: KLoggingEventBuilder.() -> Unit): Unit = at(Level.TRACE, null, block)

  /**
   * Lazy add a log message with throwable payload if isDebugEnabled is true
   *
   * @param marker The marker to log
   * @param block The block to log
   */
  public fun atDebug(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.DEBUG, marker, block)

  /**
   * Lazy add a log message with throwable payload if isDebugEnabled is true
   *
   * @param block The block to log
   */
  public fun atDebug(block: KLoggingEventBuilder.() -> Unit): Unit = at(Level.DEBUG, null, block)

  /**
   * Lazy add a log message with throwable payload if isInfoEnabled is true
   *
   * @param marker The marker to log
   * @param block The block to log
   */
  public fun atInfo(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.INFO, marker, block)

  /**
   * Lazy add a log message with throwable payload if isInfoEnabled is true
   *
   * @param block The block to log
   */
  public fun atInfo(block: KLoggingEventBuilder.() -> Unit): Unit = at(Level.INFO, null, block)

  /**
   * Lazy add a log message with throwable payload if isWarnEnabled is true
   *
   * @param marker The marker to log
   * @param block The block to log
   */
  public fun atWarn(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.WARN, marker, block)

  /**
   * Lazy add a log message with throwable payload if isWarnEnabled is true
   *
   * @param block The block to log
   */
  public fun atWarn(block: KLoggingEventBuilder.() -> Unit): Unit = at(Level.WARN, null, block)

  /**
   * Lazy add a log message with throwable payload if isErrorEnabled is true
   *
   * @param marker The marker to log
   * @param block The block to log
   */
  public fun atError(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.ERROR, marker, block)

  /**
   * Lazy add a log message with throwable payload if isErrorEnabled is true
   *
   * @param block The block to log
   */
  public fun atError(block: KLoggingEventBuilder.() -> Unit): Unit = at(Level.ERROR, null, block)

  /**
   * Lazy add a log message if level enabled
   *
   * @param level The level to log
   * @param marker The marker to log
   * @param block The block to log
   */
  public fun at(level: Level, marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit)
  // endregion

  // region Utility
  /**
   * Add a log message with all the supplied parameters along with method name
   *
   * @param arguments The arguments to log
   */
  public fun entry(vararg arguments: Any?): Unit = trace { "entry(${arguments.joinToString() })" }

  /** Add log message indicating exit of a method */
  public fun exit(): Unit = trace { "exit" }

  /**
   * Add a log message with the return value of a method
   *
   * @param result The result to log
   * @return The result
   */
  public fun <T> exit(result: T): T where T : Any? {
    trace { "exit($result)" }
    return result
  }

  /**
   * Add a log message indicating an exception will be thrown along with the stack trace.
   *
   * @param throwable The throwable to log
   * @return The throwable
   */
  public fun <T> throwing(throwable: T): T where T : Throwable {
    atError {
      cause = throwable
      message = "throwing($throwable)"
    }
    return throwable
  }

  /**
   * Add a log message indicating an exception is caught along with the stack trace.
   *
   * @param throwable The throwable to log
   */
  public fun <T> catching(throwable: T) where T : Throwable {
    atError {
      cause = throwable
      message = "catching($throwable)"
    }
  }
  // endregion
}
