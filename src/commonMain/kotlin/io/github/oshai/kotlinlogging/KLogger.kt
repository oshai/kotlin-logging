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

  @Deprecated("use trace instead", ReplaceWith("trace(null, marker, msg)"))
  public fun trace(marker: Marker?, msg: () -> Any?): Unit = trace(null as Throwable?, marker, msg)

  /** Lazy add a log message with a marker if isDebugEnabled is true */
  @Deprecated("use debug instead", ReplaceWith("debug(null, marker, msg)"))
  public fun debug(marker: Marker?, msg: () -> Any?): Unit = debug(null as Throwable?, marker, msg)

  /** Lazy add a log message with a marker if isInfoEnabled is true */
  @Deprecated("use info instead", ReplaceWith("info(null, marker, msg)"))
  public fun info(marker: Marker?, msg: () -> Any?): Unit = info(null as Throwable?, marker, msg)

  /** Lazy add a log message with a marker if isWarnEnabled is true */
  @Deprecated("use warn instead", ReplaceWith("warn(null, marker, msg)"))
  public fun warn(marker: Marker?, msg: () -> Any?): Unit = warn(null as Throwable?, marker, msg)

  /** Lazy add a log message with a marker if isErrorEnabled is true */
  @Deprecated("use error instead", ReplaceWith("error(null, marker, msg)"))
  public fun error(marker: Marker?, msg: () -> Any?): Unit = error(null as Throwable?, marker, msg)

  @Deprecated("use trace instead", ReplaceWith("trace(t, marker, msg)"))
  public fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit = trace(t, marker, msg)

  @Deprecated("use debug instead", ReplaceWith("debug(t, marker, msg)"))
  public fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit = debug(t, marker, msg)

  @Deprecated("use info instead", ReplaceWith("info(t, marker, msg)"))
  public fun info(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit = info(t, marker, msg)

  @Deprecated("use warn instead", ReplaceWith("warn(t, marker, msg)"))
  public fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit = warn(t, marker, msg)

  @Deprecated("use error instead", ReplaceWith("error(t, marker, msg)"))
  public fun error(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit = error(t, marker, msg)

  @Deprecated("Use isTraceEnabled() instead", ReplaceWith("isTraceEnabled()"))
  public val isTraceEnabled: Boolean
    get() = isTraceEnabled()

  @Deprecated("Use isDebugEnabled() instead", ReplaceWith("isDebugEnabled()"))
  public val isDebugEnabled: Boolean
    get() = isDebugEnabled()

  @Deprecated("Use isInfoEnabled() instead", ReplaceWith("isInfoEnabled()"))
  public val isInfoEnabled: Boolean
    get() = isInfoEnabled()

  @Deprecated("Use isWarnEnabled() instead", ReplaceWith("isWarnEnabled()"))
  public val isWarnEnabled: Boolean
    get() = isWarnEnabled()

  @Deprecated("Use isErrorEnabled() instead", ReplaceWith("isErrorEnabled()"))
  public val isErrorEnabled: Boolean
    get() = isErrorEnabled()

  @Deprecated("Use isLoggingOff() instead", ReplaceWith("isLoggingOff()"))
  public val isLoggingOff: Boolean
    get() = isLoggingOff()

  /**
   * Log a message at the TRACE level.
   *
   * @param msg the message string to be logged
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace { \"\$msg\"}"))
  public fun trace(msg: String?): Unit = trace { msg }

  /**
   * Log a message at the TRACE level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace { \"\$msg \$arg\"}"))
  public fun trace(msg: String?, arg: Any?): Unit = TODO()

  /**
   * Log a message at the TRACE level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace { \"\$msg \$arg1 \$arg2\"}"))
  public fun trace(msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * Log a message at the TRACE level according to the specified msg and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the TRACE
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for TRACE. The variants
   * taking [one][.trace] and [two][.trace] arguments exist solely in order to avoid this hidden
   * cost.
   *
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace { \"\$msg \$arguments\"}"))
  public fun trace(msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * Log an exception (throwable) at the TRACE level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace(t) { \"\$msg\"}"))
  public fun trace(msg: String?, t: Throwable?): Unit = trace(t as Throwable, null, { msg })

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace(marker) { \"\$msg\"}"))
  public fun trace(marker: Marker?, msg: String?): Unit = trace(null as Throwable?, marker, { msg })

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace(marker) { \"\$msg \$arg\"}"))
  public fun trace(marker: Marker?, msg: String?, arg: Any?): Unit = TODO()

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated(
    "Use trace {} instead",
    replaceWith = ReplaceWith("trace(marker) { \"\$msg \$arg1 \$arg2\"}")
  )
  public fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments an array of arguments
   */
  @Deprecated(
    "Use trace {} instead",
    replaceWith = ReplaceWith("trace(marker) { \"\$msg \$arguments\"}")
  )
  public fun trace(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use trace {} instead", replaceWith = ReplaceWith("trace(t, marker) { \"\$msg\"}"))
  public fun trace(marker: Marker?, msg: String?, t: Throwable?): Unit =
    trace(t as Throwable, marker, { msg })

  /**
   * Log a message at the DEBUG level.
   *
   * @param msg the message string to be logged
   */
  @Deprecated("Use debug {} instead", replaceWith = ReplaceWith("debug { \"\$msg\"}"))
  public fun debug(msg: String?): Unit = debug { msg }

  /**
   * Log a message at the DEBUG level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use debug {} instead", replaceWith = ReplaceWith("debug { \"\$msg \$arg\"}"))
  public fun debug(msg: String?, arg: Any?): Unit = TODO()

  /**
   * Log a message at the DEBUG level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated("Use debug {} instead", replaceWith = ReplaceWith("debug { \"\$msg \$arg1 \$arg2\"}"))
  public fun debug(msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * Log a message at the DEBUG level according to the specified msg and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the DEBUG
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for DEBUG. The variants
   * taking [one][.debug] and [two][.debug] arguments exist solely in order to avoid this hidden
   * cost.
   *
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated("Use debug {} instead")
  public fun debug(msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * Log an exception (throwable) at the DEBUG level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use debug {} instead", replaceWith = ReplaceWith("debug(t) { \"\$msg\"}"))
  public fun debug(msg: String?, t: Throwable?): Unit = debug(t as Throwable, null, { msg })

  /**
   * Log a message with the specific Marker at the DEBUG level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  @Deprecated("Use debug {} instead", replaceWith = ReplaceWith("debug(marker) { \"\$msg\"}"))
  public fun debug(marker: Marker?, msg: String?): Unit = debug(null as Throwable?, marker, { msg })

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use debug {} instead", replaceWith = ReplaceWith("debug(marker) { \"\$msg \$arg\"}"))
  public fun debug(marker: Marker?, msg: String?, arg: Any?): Unit = TODO()

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated(
    "Use debug {} instead",
    replaceWith = ReplaceWith("debug(marker) { \"\$msg \$arg1 \$arg2\"}")
  )
  public fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated(
    "Use debug {} instead",
    replaceWith = ReplaceWith("debug(marker) { \"\$msg \$arguments\"}")
  )
  public fun debug(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use debug {} instead", replaceWith = ReplaceWith("debug(t, marker) { \"\$msg\"}"))
  public fun debug(marker: Marker?, msg: String?, t: Throwable?): Unit =
    debug(t as Throwable, marker, { msg })

  /**
   * Log a message at the INFO level.
   *
   * @param msg the message string to be logged
   */
  @Deprecated("Use info {} instead") public fun info(msg: String?): Unit = info { msg }

  /**
   * Log a message at the INFO level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the INFO level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use info {} instead", replaceWith = ReplaceWith("info { \"\$msg \$arg\"}"))
  public fun info(msg: String?, arg: Any?): Unit = TODO()

  /**
   * Log a message at the INFO level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the INFO level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated("Use info {} instead", replaceWith = ReplaceWith("info { \"\$msg \$arg1 \$arg2\"}"))
  public fun info(msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * Log a message at the INFO level according to the specified msg and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the INFO
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for INFO. The variants
   * taking [one][.info] and [two][.info] arguments exist solely in order to avoid this hidden cost.
   *
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated("Use info {} instead", replaceWith = ReplaceWith("info { \"\$msg \$arguments\"}"))
  public fun info(msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * Log an exception (throwable) at the INFO level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use info {} instead", replaceWith = ReplaceWith("info(t) { \"\$msg\"}"))
  public fun info(msg: String?, t: Throwable?): Unit = info(t as Throwable, null, { msg })

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param marker The marker specific to this log statement
   * @param msg the message string to be logged
   */
  @Deprecated("Use info {} instead", replaceWith = ReplaceWith("info(marker) { \"\$msg\"}"))
  public fun info(marker: Marker?, msg: String?): Unit = info(null as Throwable?, marker, { msg })

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use info {} instead", replaceWith = ReplaceWith("info(marker) { \"\$msg \$arg\"}"))
  public fun info(marker: Marker?, msg: String?, arg: Any?): Unit = TODO()

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated(
    "Use info {} instead",
    replaceWith = ReplaceWith("info(marker) { \"\$msg \$arg1 \$arg2\"}")
  )
  public fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated(
    "Use info {} instead",
    replaceWith = ReplaceWith("info(marker) { \"\$msg \$arguments\"}")
  )
  public fun info(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data for this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use info {} instead", replaceWith = ReplaceWith("info(t, marker) { \"\$msg\"}"))
  public fun info(marker: Marker?, msg: String?, t: Throwable?): Unit =
    info(t as Throwable, marker, { msg })

  /**
   * Log a message at the WARN level.
   *
   * @param msg the message string to be logged
   */
  @Deprecated("Use warn {} instead") public fun warn(msg: String?): Unit = warn { msg }

  /**
   * Log a message at the WARN level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the WARN level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use warn {} instead", replaceWith = ReplaceWith("warn { \"\$msg \$arg\"}"))
  public fun warn(msg: String?, arg: Any?): Unit = TODO()

  /**
   * Log a message at the WARN level according to the specified msg and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the WARN
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for WARN. The variants
   * taking [one][.warn] and [two][.warn] arguments exist solely in order to avoid this hidden cost.
   *
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated("Use warn {} instead", replaceWith = ReplaceWith("warn { \"\$msg \$arguments\"}"))
  public fun warn(msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * Log a message at the WARN level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the WARN level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated("Use warn {} instead", replaceWith = ReplaceWith("warn { \"\$msg \$arg1 \$arg2\"}"))
  public fun warn(msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * Log an exception (throwable) at the WARN level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use warn {} instead", replaceWith = ReplaceWith("warn(t) { \"\$msg\"}"))
  public fun warn(msg: String?, t: Throwable?): Unit = warn(t as Throwable, null, { msg })

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param marker The marker specific to this log statement
   * @param msg the message string to be logged
   */
  @Deprecated("Use warn {} instead", replaceWith = ReplaceWith("warn(marker) { \"\$msg\"}"))
  public fun warn(marker: Marker?, msg: String?): Unit = warn(null as Throwable?, marker, { msg })

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use warn {} instead", replaceWith = ReplaceWith("warn(marker) { \"\$msg \$arg\"}"))
  public fun warn(marker: Marker?, msg: String?, arg: Any?): Unit = TODO()

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated(
    "Use warn {} instead",
    replaceWith = ReplaceWith("warn(marker) { \"\$msg \$arg1 \$arg2\"}")
  )
  public fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated(
    "Use warn {} instead",
    replaceWith = ReplaceWith("warn(marker) { \"\$msg \$arguments\"}")
  )
  public fun warn(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data for this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use warn {} instead", replaceWith = ReplaceWith("warn(t, marker) { \"\$msg\"}"))
  public fun warn(marker: Marker?, msg: String?, t: Throwable?): Unit =
    warn(t as Throwable, marker, { msg })

  /**
   * Log a message at the ERROR level.
   *
   * @param msg the message string to be logged
   */
  @Deprecated("Use error {} instead", replaceWith = ReplaceWith("error { \"\$msg\"}"))
  public fun error(msg: String?): Unit = error { msg }

  /**
   * Log a message at the ERROR level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated("Use error {} instead", replaceWith = ReplaceWith("error { \"\$msg \$arg\"}"))
  public fun error(msg: String?, arg: Any?): Unit = TODO()

  /**
   * Log a message at the ERROR level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated("Use error {} instead", replaceWith = ReplaceWith("error { \"\$msg \$arg1 \$arg2\"}"))
  public fun error(msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * Log a message at the ERROR level according to the specified msg and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the ERROR
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for ERROR. The variants
   * taking [one][.error] and [two][.error] arguments exist solely in order to avoid this hidden
   * cost.
   *
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated("Use error {} instead", replaceWith = ReplaceWith("error { \"\$msg \$arguments\"}"))
  public fun error(msg: String?, vararg arguments: Any?): Unit = TODO()

  /**
   * Log an exception (throwable) at the ERROR level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  @Deprecated("Use error(t){} instead", replaceWith = ReplaceWith("error(t) { \"\$msg\"}"))
  public fun error(msg: String?, t: Throwable?): Unit = error(t, null) { msg }

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param marker The marker specific to this log statement
   * @param msg the message string to be logged
   */
  @Deprecated(
    "Use error(marker){} instead",
    replaceWith = ReplaceWith("error(marker) { \"\$msg\"}")
  )
  public fun error(marker: Marker?, msg: String?): Unit = error(null as Throwable?, marker) { msg }

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  @Deprecated(
    "Use error(marker){} instead",
    replaceWith = ReplaceWith("error(marker) { \"\$msg \$arg \"}")
  )
  public fun error(marker: Marker?, msg: String?, arg: Any?): Unit = TODO()

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  @Deprecated(
    "Use error(marker){} instead",
    replaceWith = ReplaceWith("error(marker) { \"\$msg \$arg1 \$arg2\"}")
  )
  public fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = TODO()

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  @Deprecated(
    "Use error(marker){} instead",
    replaceWith = ReplaceWith("error(marker) { \"\$msg \$arguments\"}")
  )
  public fun error(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = TODO()

  @Deprecated("Use error instead", ReplaceWith("error(t, marker, msg)"))
  public fun error(marker: Marker?, msg: String?, t: Throwable?): Unit = error(t, marker) { msg }
}

public interface DelegatingKLogger<T> {
  /** The actual logger executing logging */
  public val underlyingLogger: T
}
