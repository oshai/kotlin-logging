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

  /**
   * Lazy add a log message with a marker if isTraceEnabled is true
   */
  fun trace(marker: Marker?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker if isDebugEnabled is true
   */
  fun debug(marker: Marker?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker if isInfoEnabled is true
   */
  fun info(marker: Marker?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker if isWarnEnabled is true
   */
  fun warn(marker: Marker?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker if isErrorEnabled is true
   */
  fun error(marker: Marker?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker and throwable payload if isTraceEnabled is true
   */
  fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker and throwable payload if isDebugEnabled is true
   */
  fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker and throwable payload if isInfoEnabled is true
   */
  fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker and throwable payload if isWarnEnabled is true
   */
  fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /**
   * Lazy add a log message with a marker and throwable payload if isErrorEnabled is true
   */
  fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /**
   * Add a log message with all the supplied parameters along with method name
   */
  fun entry(vararg argArray: Any)

  /**
   * Add log message indicating exit of a method
   */
  fun exit()

  /**
   * Add a log message with the return value of a method
   */
  fun <T> exit(retval: T): T where T : Any?

  /**
   * Add a log message indicating an exception will be thrown along with the stack trace.
   */
  fun <T> throwing(throwable: T): T where T : Throwable

  /**
   * Add a log message indicating an exception is caught along with the stack trace.
   */
  fun <T> catching(throwable: T) where T : Throwable

}
