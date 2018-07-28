package mu



actual interface KLogger {


  /**
   * Lazy add a log message if isTraceEnabled is true
   */
  actual fun trace(msg: () -> Any?)

  /**
   * Lazy add a log message if isDebugEnabled is true
   */
  actual fun debug(msg: () -> Any?)

  /**
   * Lazy add a log message if isInfoEnabled is true
   */
  actual fun info(msg: () -> Any?)

  /**
   * Lazy add a log message if isWarnEnabled is true
   */
  actual fun warn(msg: () -> Any?)

  /**
   * Lazy add a log message if isErrorEnabled is true
   */
  actual fun error(msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isTraceEnabled is true
   */
  actual fun trace(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isDebugEnabled is true
   */
  actual fun debug(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isInfoEnabled is true
   */
  actual fun info(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isWarnEnabled is true
   */
  actual fun warn(t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with throwable payload if isErrorEnabled is true
   */
  actual fun error(t: Throwable?, msg: () -> Any?)
}
