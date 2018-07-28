package mu



expect interface KLogger {


  /**
   * Lazy add a log message if isTraceEnabled is true
   */
  fun trace(msg: () -> Any?)

  /**
   * Lazy add a log message if isDebugEnabled is true
   */
  fun debug(msg: () -> Any?)

  /**
   * Lazy add a log message if isInfoEnabled is true
   */
  fun info(msg: () -> Any?)

  /**
   * Lazy add a log message if isWarnEnabled is true
   */
  fun warn(msg: () -> Any?)

  /**
   * Lazy add a log message if isErrorEnabled is true
   */
  fun error(msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isTraceEnabled is true
   */
  fun trace(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isDebugEnabled is true
   */
  fun debug(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isInfoEnabled is true
   */
  fun info(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isWarnEnabled is true
   */
  fun warn(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isErrorEnabled is true
   */
  fun error(t: Throwable?, msg: () -> Any?)
}
