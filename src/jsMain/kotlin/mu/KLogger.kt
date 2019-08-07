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

	/**
	 * Lazy add a log message if isTraceEnabled is true
	 */
	actual fun trace(marker: Marker?, msg: () -> Any?)

	/**
	 * Lazy add a log message if isDebugEnabled is true
	 */
	actual fun debug(marker: Marker?, msg: () -> Any?)

	/**
	 * Lazy add a log message if isInfoEnabled is true
	 */
	actual fun info(marker: Marker?, msg: () -> Any?)

	/**
	 * Lazy add a log message if isWarnEnabled is true
	 */
	actual fun warn(marker: Marker?, msg: () -> Any?)

	/**
	 * Lazy add a log message if isErrorEnabled is true
	 */
	actual fun error(marker: Marker?, msg: () -> Any?)

	/**
	 * Lazy add a log message with throwable payload if isTraceEnabled is true
	 */
	actual fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

	/**
	 * Lazy add a log message with throwable payload if isDebugEnabled is true
	 */
	actual fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

	/**
	 * Lazy add a log message with throwable payload if isInfoEnabled is true
	 */
	actual fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

	/**
	 * Lazy add a log message with throwable payload if isWarnEnabled is true
	 */
	actual fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

	/**
	 * Lazy add a log message with throwable payload if isErrorEnabled is true
	 */
	actual fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

	/**
	 * Add a log message with all the supplied parameters along with method name
	 */
	actual fun entry(vararg argArray: Any)

	/**
	 * Add log message indicating exit of a method
	 */
	actual fun exit()

	/**
	 * Add a log message with the return value of a method
	 */
	actual fun <T> exit(retval: T): T where T : Any

	/**
	 * Add a log message with the optional return value of a method
	 */
	actual fun <T> exitOpt(retval: T?): T? where T : Any

	/**
	 * Add a log message indicating an exception will be thrown along with the stack trace.
	 */
	actual fun <T> throwing(throwable: T): T where T : Throwable

	/**
	 * Add a log message indicating an exception is caught along with the stack trace.
	 */
	actual fun <T> catching(throwable: T) where T : Throwable
}
