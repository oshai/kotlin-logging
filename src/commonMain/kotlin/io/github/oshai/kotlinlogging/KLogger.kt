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
   * @return name of this logger instance
   */
  public val name: String

  /** Lazy add a log message if isTraceEnabled is true */
  public fun trace(message: () -> Any?): Unit =
    at(Level.TRACE) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isDebugEnabled is true */
  public fun debug(message: () -> Any?): Unit =
    at(Level.DEBUG) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isInfoEnabled is true */
  public fun info(message: () -> Any?): Unit =
    at(Level.INFO) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isWarnEnabled is true */
  public fun warn(message: () -> Any?): Unit =
    at(Level.WARN) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isErrorEnabled is true */
  public fun error(message: () -> Any?): Unit =
    at(Level.ERROR) { this.message = message.toStringSafe() }

  /** Lazy add a log message if isTraceEnabled is true */
  public fun trace(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.TRACE) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isDebugEnabled is true */
  public fun debug(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.DEBUG) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isInfoEnabled is true */
  public fun info(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.INFO) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isWarnEnabled is true */
  public fun warn(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.WARN) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isErrorEnabled is true */
  public fun error(throwable: Throwable?, message: () -> Any?): Unit =
    at(Level.ERROR) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isTraceEnabled is true */
  public fun trace(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.TRACE, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isDebugEnabled is true */
  public fun debug(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.DEBUG, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isInfoEnabled is true */
  public fun info(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.INFO, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isWarnEnabled is true */
  public fun warn(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.WARN, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message if isErrorEnabled is true */
  public fun error(throwable: Throwable?, marker: Marker?, message: () -> Any?): Unit =
    at(Level.ERROR, marker) {
      this.message = message.toStringSafe()
      this.cause = throwable
    }

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  public fun atTrace(marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.TRACE, marker, block)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  public fun atDebug(marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.DEBUG, marker, block)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  public fun atInfo(marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.INFO, marker, block)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  public fun atWarn(marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.WARN, marker, block)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  public fun atError(marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit): Unit =
    at(Level.ERROR, marker, block)

  /** Lazy add a log message if level enabled */
  public fun at(level: Level, marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit)

  /** Add a log message with all the supplied parameters along with method name */
  public fun entry(vararg arguments: Any?): Unit = trace { "entry(${arguments.joinToString() })" }

  /** Add log message indicating exit of a method */
  public fun exit(): Unit = trace { "exit" }

  /** Add a log message with the return value of a method */
  public fun <T> exit(result: T): T where T : Any? {
    trace { "exit($result)" }
    return result
  }

  /** Add a log message indicating an exception will be thrown along with the stack trace. */
  public fun <T> throwing(throwable: T): T where T : Throwable {
    atError {
      cause = throwable
      message = "throwing($throwable)"
    }
    return throwable
  }

  /** Add a log message indicating an exception is caught along with the stack trace. */
  public fun <T> catching(throwable: T) where T : Throwable {
    atError {
      cause = throwable
      message = "catching($throwable)"
    }
  }

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

  public fun isLoggingEnabledFor(level: Level, marker: Marker? = null): Boolean
}

public interface DelegatingKLogger<T> {
  /** The actual logger executing logging */
  public val underlyingLogger: T
}
