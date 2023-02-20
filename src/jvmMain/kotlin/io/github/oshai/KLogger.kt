package io.github.oshai

/**
 * A Logger interface with Lazy message evaluation example:
 * ```
 * logger.info{"this is $lazy evaluated string"}
 * ```
 */
@Suppress("TooManyFunctions")
public actual interface KLogger : ActualKLogger {

  /** The actual logger executing logging */
  public val underlyingLogger: Any

  /**
   * Log a message at the TRACE level.
   *
   * @param msg the message string to be logged
   */
  public fun trace(msg: String?)

  /**
   * Log a message at the TRACE level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  public fun trace(msg: String?, arg: Any?)

  /**
   * Log a message at the TRACE level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun trace(msg: String?, arg1: Any?, arg2: Any?)

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
  public fun trace(msg: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the TRACE level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun trace(msg: String?, t: Throwable?)

  /**
   * Similar to [.isTraceEnabled] method except that the marker data is also taken into account.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   *
   * @since 14
   */
  public fun isTraceEnabled(marker: Marker?): Boolean

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public fun trace(marker: Marker?, msg: String?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  public fun trace(marker: Marker?, msg: String?, arg: Any?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param argArray an array of arguments
   */
  public fun trace(marker: Marker?, msg: String?, vararg argArray: Any?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun trace(marker: Marker?, msg: String?, t: Throwable?)

  /**
   * Log a message at the DEBUG level.
   *
   * @param msg the message string to be logged
   */
  public fun debug(msg: String?)

  /**
   * Log a message at the DEBUG level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  public fun debug(msg: String?, arg: Any?)

  /**
   * Log a message at the DEBUG level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun debug(msg: String?, arg1: Any?, arg2: Any?)

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
  public fun debug(msg: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the DEBUG level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun debug(msg: String?, t: Throwable?)

  /**
   * Similar to [.isDebugEnabled] method except that the marker data is also taken into account.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  public fun isDebugEnabled(marker: Marker?): Boolean

  /**
   * Log a message with the specific Marker at the DEBUG level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   */
  public fun debug(marker: Marker?, msg: String?)

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  public fun debug(marker: Marker?, msg: String?, arg: Any?)

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  public fun debug(marker: Marker?, msg: String?, vararg arguments: Any?)

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun debug(marker: Marker?, msg: String?, t: Throwable?)

  /**
   * Log a message at the INFO level.
   *
   * @param msg the message string to be logged
   */
  public fun info(msg: String?)

  /**
   * Log a message at the INFO level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the INFO level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  public fun info(msg: String?, arg: Any?)

  /**
   * Log a message at the INFO level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the INFO level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun info(msg: String?, arg1: Any?, arg2: Any?)

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
  public fun info(msg: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the INFO level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun info(msg: String?, t: Throwable?)

  /**
   * Similar to [.isInfoEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return true if this Logger is enabled for the INFO level, false otherwise.
   */
  public fun isInfoEnabled(marker: Marker?): Boolean

  /**
   * Log a message with the specific Marker at the INFO level.
   *
   * @param marker The marker specific to this log statement
   * @param msg the message string to be logged
   */
  public fun info(marker: Marker?, msg: String?)

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  public fun info(marker: Marker?, msg: String?, arg: Any?)

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  public fun info(marker: Marker?, msg: String?, vararg arguments: Any?)

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data for this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun info(marker: Marker?, msg: String?, t: Throwable?)

  /**
   * Log a message at the WARN level.
   *
   * @param msg the message string to be logged
   */
  public fun warn(msg: String?)

  /**
   * Log a message at the WARN level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the WARN level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  public fun warn(msg: String?, arg: Any?)

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
  public fun warn(msg: String?, vararg arguments: Any?)

  /**
   * Log a message at the WARN level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the WARN level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun warn(msg: String?, arg1: Any?, arg2: Any?)

  /**
   * Log an exception (throwable) at the WARN level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun warn(msg: String?, t: Throwable?)

  /**
   * Similar to [.isWarnEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  public fun isWarnEnabled(marker: Marker?): Boolean

  /**
   * Log a message with the specific Marker at the WARN level.
   *
   * @param marker The marker specific to this log statement
   * @param msg the message string to be logged
   */
  public fun warn(marker: Marker?, msg: String?)

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  public fun warn(marker: Marker?, msg: String?, arg: Any?)

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  public fun warn(marker: Marker?, msg: String?, vararg arguments: Any?)

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data for this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun warn(marker: Marker?, msg: String?, t: Throwable?)

  /**
   * Log a message at the ERROR level.
   *
   * @param msg the message string to be logged
   */
  public fun error(msg: String?)

  /**
   * Log a message at the ERROR level according to the specified msg and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
   *
   * @param msg the msg string
   * @param arg the argument
   */
  public fun error(msg: String?, arg: Any?)

  /**
   * Log a message at the ERROR level according to the specified msg and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
   *
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun error(msg: String?, arg1: Any?, arg2: Any?)

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
  public fun error(msg: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the ERROR level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun error(msg: String?, t: Throwable?)

  /**
   * Similar to [.isErrorEnabled] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  public fun isErrorEnabled(marker: Marker?): Boolean

  /**
   * Log a message with the specific Marker at the ERROR level.
   *
   * @param marker The marker specific to this log statement
   * @param msg the message string to be logged
   */
  public fun error(marker: Marker?, msg: String?)

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg the argument
   */
  public fun error(marker: Marker?, msg: String?, arg: Any?)

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the msg string
   * @param arguments a list of 3 or more arguments
   */
  public fun error(marker: Marker?, msg: String?, vararg arguments: Any?)

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public fun error(marker: Marker?, msg: String?, t: Throwable?)
}
