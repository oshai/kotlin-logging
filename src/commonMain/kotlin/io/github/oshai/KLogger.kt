package io.github.oshai

/**
 * A Logger interface with Lazy message evaluation example:
 * ```
 * logger.info{"this is $lazy evaluated string"}
 * ```
 */
public expect interface KLogger {

  /**
   * Return the name of this `Logger` instance.
   * @return name of this logger instance
   */
  public val name: String

  /** Lazy add a log message if isTraceEnabled is true */
  public fun trace(msg: () -> Any?)

  /** Lazy add a log message if isDebugEnabled is true */
  public fun debug(msg: () -> Any?)

  /** Lazy add a log message if isInfoEnabled is true */
  public fun info(msg: () -> Any?)

  /** Lazy add a log message if isWarnEnabled is true */
  public fun warn(msg: () -> Any?)

  /** Lazy add a log message if isErrorEnabled is true */
  public fun error(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  public fun trace(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  public fun debug(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  public fun info(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  public fun warn(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  public fun error(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with a marker if isTraceEnabled is true */
  public fun trace(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with a marker if isDebugEnabled is true */
  public fun debug(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with a marker if isInfoEnabled is true */
  public fun info(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with a marker if isWarnEnabled is true */
  public fun warn(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with a marker if isErrorEnabled is true */
  public fun error(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with a marker and throwable payload if isTraceEnabled is true */
  public fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with a marker and throwable payload if isDebugEnabled is true */
  public fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with a marker and throwable payload if isInfoEnabled is true */
  public fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with a marker and throwable payload if isWarnEnabled is true */
  public fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with a marker and throwable payload if isErrorEnabled is true */
  public fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Add a log message with all the supplied parameters along with method name */
  public fun entry(vararg arguments: Any?)

  /** Add log message indicating exit of a method */
  public fun exit()

  /** Add a log message with the return value of a method */
  public fun <T> exit(result: T): T where T : Any?

  /** Add a log message indicating an exception will be thrown along with the stack trace. */
  public fun <T> throwing(throwable: T): T where T : Throwable

  /** Add a log message indicating an exception is caught along with the stack trace. */
  public fun <T> catching(throwable: T) where T : Throwable

  /**
   * Is the logger instance enabled for the TRACE level?
   *
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   */
  public val isTraceEnabled: Boolean

  /**
   * Is the logger instance enabled for the DEBUG level?
   *
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  public val isDebugEnabled: Boolean

  /**
   * Is the logger instance enabled for the INFO level?
   *
   * @return True if this Logger is enabled for the INFO level, false otherwise.
   */
  public val isInfoEnabled: Boolean

  /**
   * Is the logger instance enabled for the WARN level?
   *
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  public val isWarnEnabled: Boolean

  /**
   * Is the logger instance enabled for the ERROR level?
   *
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  public val isErrorEnabled: Boolean
}

/**
 * Returns whether this Logger is enabled for a given [Level].
 *
 * @param level
 * @return true if enabled, false otherwise.
 */
public fun KLogger.isEnabledForLevel(level: Level): Boolean {
  return when (level.toInt()) {
    Levels.TRACE_INT -> isTraceEnabled
    Levels.DEBUG_INT -> isDebugEnabled
    Levels.INFO_INT -> isInfoEnabled
    Levels.WARN_INT -> isWarnEnabled
    Levels.ERROR_INT -> isErrorEnabled
    Levels.OFF_INT -> false
    else -> throw IllegalArgumentException("Level [$level] not recognized.")
  }
}

@Suppress("TooManyFunctions")
public interface ActualKLogger {

  /**
   * Return the name of this `Logger` instance.
   * @return name of this logger instance
   */
  public val name: String

  /** Lazy add a log message if isTraceEnabled is true */
  public fun trace(msg: () -> Any?)

  /** Lazy add a log message if isDebugEnabled is true */
  public fun debug(msg: () -> Any?)

  /** Lazy add a log message if isInfoEnabled is true */
  public fun info(msg: () -> Any?)

  /** Lazy add a log message if isWarnEnabled is true */
  public fun warn(msg: () -> Any?)

  /** Lazy add a log message if isErrorEnabled is true */
  public fun error(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  public fun trace(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  public fun debug(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  public fun info(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  public fun warn(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  public fun error(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isTraceEnabled is true */
  public fun trace(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isDebugEnabled is true */
  public fun debug(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isInfoEnabled is true */
  public fun info(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isWarnEnabled is true */
  public fun warn(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message if isErrorEnabled is true */
  public fun error(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  public fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  public fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  public fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  public fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  public fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Add a log message with all the supplied parameters along with method name */
  public fun entry(vararg arguments: Any?)

  /** Add log message indicating exit of a method */
  public fun exit()

  /** Add a log message with the return value of a method */
  public fun <T> exit(result: T): T where T : Any?

  /** Add a log message indicating an exception will be thrown along with the stack trace. */
  public fun <T> throwing(throwable: T): T where T : Throwable

  /** Add a log message indicating an exception is caught along with the stack trace. */
  public fun <T> catching(throwable: T) where T : Throwable

  /**
   * Is the logger instance enabled for the TRACE level?
   *
   * @return True if this Logger is enabled for the TRACE level, false otherwise.
   */
  public val isTraceEnabled: Boolean

  /**
   * Is the logger instance enabled for the DEBUG level?
   *
   * @return True if this Logger is enabled for the DEBUG level, false otherwise.
   */
  public val isDebugEnabled: Boolean

  /**
   * Is the logger instance enabled for the INFO level?
   *
   * @return True if this Logger is enabled for the INFO level, false otherwise.
   */
  public val isInfoEnabled: Boolean

  /**
   * Is the logger instance enabled for the WARN level?
   *
   * @return True if this Logger is enabled for the WARN level, false otherwise.
   */
  public val isWarnEnabled: Boolean

  /**
   * Is the logger instance enabled for the ERROR level?
   *
   * @return True if this Logger is enabled for the ERROR level, false otherwise.
   */
  public val isErrorEnabled: Boolean
}
