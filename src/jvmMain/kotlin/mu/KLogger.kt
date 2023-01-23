package mu

/**
 * An Logger interface with Lazy message evaluation example:
 * ```
 * logger.info{"this is $lazy evaluated string"}
 * ```
 */
@Suppress("TooManyFunctions")
public actual interface KLogger {

  /**
   * Return the name of this `Logger` instance.
   * @return name of this logger instance
   */
  public actual val name: String

  /** The actual logger executing logging */
  public val underlyingLogger: Any

  /** Lazy add a log message if isTraceEnabled is true */
  public actual fun trace(msg: () -> Any?)

  /** Lazy add a log message if isDebugEnabled is true */
  public actual fun debug(msg: () -> Any?)

  /** Lazy add a log message if isInfoEnabled is true */
  public actual fun info(msg: () -> Any?)

  /** Lazy add a log message if isWarnEnabled is true */
  public actual fun warn(msg: () -> Any?)

  /** Lazy add a log message if isErrorEnabled is true */
  public actual fun error(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  public actual fun trace(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  public actual fun debug(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  public actual fun info(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  public actual fun warn(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  public actual fun error(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isTraceEnabled is true */
  public actual fun trace(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isDebugEnabled is true */
  public actual fun debug(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isInfoEnabled is true */
  public actual fun info(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isWarnEnabled is true */
  public actual fun warn(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isErrorEnabled is true */
  public actual fun error(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  public actual fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  public actual fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  public actual fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  public actual fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  public actual fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Add a log message with all the supplied parameters along with method name */
  public actual fun entry(vararg argArray: Any?)

  /** Add log message indicating exit of a method */
  public actual fun exit()

  /** Add a log message with the return value of a method */
  public actual fun <T> exit(result: T): T where T : Any?

  /** Add a log message indicating an exception will be thrown along with the stack trace. */
  public actual fun <T> throwing(throwable: T): T where T : Throwable

  /** Add a log message indicating an exception is caught along with the stack trace. */
  public actual fun <T> catching(throwable: T) where T : Throwable

  /**
   * Returns whether this Logger is enabled for a given [Level].
   *
   * @param level
   * @return true if enabled, false otherwise.
   */
  public fun isEnabledForLevel(level: Level): Boolean {
    return when (level.toInt()) {
      Levels.TRACE_INT -> isTraceEnabled
      Levels.DEBUG_INT -> isDebugEnabled
      Levels.INFO_INT -> isInfoEnabled
      Levels.WARN_INT -> isWarnEnabled
      Levels.ERROR_INT -> isErrorEnabled
      else -> throw IllegalArgumentException("Level [$level] not recognized.")
    }
  }

  /**
   * Is the logger instance enabled for the TRACE level?
   *
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   * @since 1.4
   */
  public val isTraceEnabled: Boolean

  /**
   * Log a message at the TRACE level.
   *
   * @param msg the message string to be logged
   * @since 1.4
   */
  public fun trace(msg: String?)

  /**
   * Log a message at the TRACE level according to the specified format and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
   *
   * @param format the format string
   * @param arg the argument
   * @since 1.4
   */
  public fun trace(format: String?, arg: Any?)

  /**
   * Log a message at the TRACE level according to the specified format and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
   *
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   * @since 1.4
   */
  public fun trace(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the TRACE level according to the specified format and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the TRACE
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for TRACE. The variants
   * taking [one][.trace] and [two][.trace] arguments exist solely in order to avoid this hidden
   * cost.
   *
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   * @since 1.4
   */
  public fun trace(format: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the TRACE level with an accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   * @since 1.4
   */
  public fun trace(msg: String?, t: Throwable?)

  /**
   * Similar to [.isTraceEnabled] method except that the marker data is also taken into account.
   *
   * @param marker The marker data to take into consideration
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   *
   * @since 1.4
   */
  public fun isTraceEnabled(marker: Marker?): Boolean

  /**
   * Log a message with the specific Marker at the TRACE level.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message string to be logged
   * @since 1.4
   */
  public fun trace(marker: Marker?, msg: String?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arg the argument
   * @since 1.4
   */
  public fun trace(marker: Marker?, format: String?, arg: Any?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   * @since 1.4
   */
  public fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param argArray an array of arguments
   * @since 1.4
   */
  public fun trace(marker: Marker?, format: String?, vararg argArray: Any?)

  /**
   * This method is similar to [.trace] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   * @since 1.4
   */
  public fun trace(marker: Marker?, msg: String?, t: Throwable?)

  /**
   * Is the logger instance enabled for the DEBUG level?
   *
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  public val isDebugEnabled: Boolean

  /**
   * Log a message at the DEBUG level.
   *
   * @param msg the message string to be logged
   */
  public fun debug(msg: String?)

  /**
   * Log a message at the DEBUG level according to the specified format and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
   *
   * @param format the format string
   * @param arg the argument
   */
  public fun debug(format: String?, arg: Any?)

  /**
   * Log a message at the DEBUG level according to the specified format and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
   *
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun debug(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the DEBUG level according to the specified format and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the DEBUG
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for DEBUG. The variants
   * taking [one][.debug] and [two][.debug] arguments exist solely in order to avoid this hidden
   * cost.
   *
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun debug(format: String?, vararg arguments: Any?)

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
   * @param format the format string
   * @param arg the argument
   */
  public fun debug(marker: Marker?, format: String?, arg: Any?)

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.debug] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun debug(marker: Marker?, format: String?, vararg arguments: Any?)

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
   * Is the logger instance enabled for the INFO level?
   *
   * @return True if this Logger is enabled for the INFO level, false otherwise.
   */
  public val isInfoEnabled: Boolean

  /**
   * Log a message at the INFO level.
   *
   * @param msg the message string to be logged
   */
  public fun info(msg: String?)

  /**
   * Log a message at the INFO level according to the specified format and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the INFO level.
   *
   * @param format the format string
   * @param arg the argument
   */
  public fun info(format: String?, arg: Any?)

  /**
   * Log a message at the INFO level according to the specified format and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the INFO level.
   *
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun info(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the INFO level according to the specified format and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the INFO
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for INFO. The variants
   * taking [one][.info] and [two][.info] arguments exist solely in order to avoid this hidden cost.
   *
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun info(format: String?, vararg arguments: Any?)

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
   * @param format the format string
   * @param arg the argument
   */
  public fun info(marker: Marker?, format: String?, arg: Any?)

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.info] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun info(marker: Marker?, format: String?, vararg arguments: Any?)

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
   * Is the logger instance enabled for the WARN level?
   *
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  public val isWarnEnabled: Boolean

  /**
   * Log a message at the WARN level.
   *
   * @param msg the message string to be logged
   */
  public fun warn(msg: String?)

  /**
   * Log a message at the WARN level according to the specified format and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the WARN level.
   *
   * @param format the format string
   * @param arg the argument
   */
  public fun warn(format: String?, arg: Any?)

  /**
   * Log a message at the WARN level according to the specified format and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the WARN
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for WARN. The variants
   * taking [one][.warn] and [two][.warn] arguments exist solely in order to avoid this hidden cost.
   *
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun warn(format: String?, vararg arguments: Any?)

  /**
   * Log a message at the WARN level according to the specified format and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the WARN level.
   *
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun warn(format: String?, arg1: Any?, arg2: Any?)

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
   * @param format the format string
   * @param arg the argument
   */
  public fun warn(marker: Marker?, format: String?, arg: Any?)

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.warn] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun warn(marker: Marker?, format: String?, vararg arguments: Any?)

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
   * Is the logger instance enabled for the ERROR level?
   *
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  public val isErrorEnabled: Boolean

  /**
   * Log a message at the ERROR level.
   *
   * @param msg the message string to be logged
   */
  public fun error(msg: String?)

  /**
   * Log a message at the ERROR level according to the specified format and argument.
   *
   * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
   *
   * @param format the format string
   * @param arg the argument
   */
  public fun error(format: String?, arg: Any?)

  /**
   * Log a message at the ERROR level according to the specified format and arguments.
   *
   * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
   *
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun error(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the ERROR level according to the specified format and arguments.
   *
   * This form avoids superfluous string concatenation when the logger is disabled for the ERROR
   * level. However, this variant incurs the hidden (and relatively small) cost of creating an
   * `Object[]` before invoking the method, even if this logger is disabled for ERROR. The variants
   * taking [one][.error] and [two][.error] arguments exist solely in order to avoid this hidden
   * cost.
   *
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun error(format: String?, vararg arguments: Any?)

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
   * @param format the format string
   * @param arg the argument
   */
  public fun error(marker: Marker?, format: String?, arg: Any?)

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  public fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

  /**
   * This method is similar to [.error] method except that the marker data is also taken into
   * consideration.
   *
   * @param marker the marker data specific to this log statement
   * @param format the format string
   * @param arguments a list of 3 or more arguments
   */
  public fun error(marker: Marker?, format: String?, vararg arguments: Any?)

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
