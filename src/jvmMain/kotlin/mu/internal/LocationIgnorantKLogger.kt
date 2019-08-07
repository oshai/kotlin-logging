package mu.internal

import mu.KLogger
import org.slf4j.Logger
import org.slf4j.Marker

/**
 * A class wrapping a [Logger] instance that is not location aware
 * all methods of [KLogger] has default implementation
 * the rest of the methods are delegated to [Logger]
 * Hence no implemented methods
 */
internal class LocationIgnorantKLogger(override val underlyingLogger: Logger) : KLogger, Logger by underlyingLogger {

	/**
	 * Lazy add a log message if isTraceEnabled is true
	 */
	override fun trace(msg: () -> Any?) {
		if (isTraceEnabled) trace(msg.toStringSafe())
	}

	/**
	 * Lazy add a log message if isDebugEnabled is true
	 */
	override fun debug(msg: () -> Any?) {
		if (isDebugEnabled) debug(msg.toStringSafe())
	}

	/**
	 * Lazy add a log message if isInfoEnabled is true
	 */
	override fun info(msg: () -> Any?) {
		if (isInfoEnabled) info(msg.toStringSafe())
	}

	/**
	 * Lazy add a log message if isWarnEnabled is true
	 */
	override fun warn(msg: () -> Any?) {
		if (isWarnEnabled) warn(msg.toStringSafe())
	}

	/**
	 * Lazy add a log message if isErrorEnabled is true
	 */
	override fun error(msg: () -> Any?) {
		if (isErrorEnabled) error(msg.toStringSafe())
	}

	/**
	 * Lazy add a log message with throwable payload if isTraceEnabled is true
	 */
	override fun trace(t: Throwable?, msg: () -> Any?) {
		if (isTraceEnabled) trace(msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with throwable payload if isDebugEnabled is true
	 */
	override fun debug(t: Throwable?, msg: () -> Any?) {
		if (isDebugEnabled) debug(msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with throwable payload if isInfoEnabled is true
	 */
	override fun info(t: Throwable?, msg: () -> Any?) {
		if (isInfoEnabled) info(msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with throwable payload if isWarnEnabled is true
	 */
	override fun warn(t: Throwable?, msg: () -> Any?) {
		if (isWarnEnabled) warn(msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with throwable payload if isErrorEnabled is true
	 */
	override fun error(t: Throwable?, msg: () -> Any?) {
		if (isErrorEnabled) error(msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with a marker if isTraceEnabled is true
	 */
	override fun trace(marker: Marker?, msg: () -> Any?) {
		if (isTraceEnabled) trace(marker, msg.toStringSafe())
	}

	/**
	 * Lazy add a log message with a marker if isDebugEnabled is true
	 */
	override fun debug(marker: Marker?, msg: () -> Any?) {
		if (isDebugEnabled) debug(marker, msg.toStringSafe())
	}

	/**
	 * Lazy add a log message with a marker if isInfoEnabled is true
	 */
	override fun info(marker: Marker?, msg: () -> Any?) {
		if (isInfoEnabled) info(marker, msg.toStringSafe())
	}

	/**
	 * Lazy add a log message with a marker if isWarnEnabled is true
	 */
	override fun warn(marker: Marker?, msg: () -> Any?) {
		if (isWarnEnabled) warn(marker, msg.toStringSafe())
	}

	/**
	 * Lazy add a log message with a marker if isErrorEnabled is true
	 */
	override fun error(marker: Marker?, msg: () -> Any?) {
		if (isErrorEnabled) error(marker, msg.toStringSafe())
	}

	/**
	 * Lazy add a log message with a marker and throwable payload if isTraceEnabled is true
	 */
	override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) {
		if (isTraceEnabled) trace(marker, msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with a marker and throwable payload if isDebugEnabled is true
	 */
	override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
		if (isDebugEnabled) debug(marker, msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with a marker and throwable payload if isInfoEnabled is true
	 */
	override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
		if (isInfoEnabled) info(marker, msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with a marker and throwable payload if isWarnEnabled is true
	 */
	override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
		if (isWarnEnabled) warn(marker, msg.toStringSafe(), t)
	}

	/**
	 * Lazy add a log message with a marker and throwable payload if isErrorEnabled is true
	 */
	override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
		if (isErrorEnabled) error(marker, msg.toStringSafe(), t)
	}

	override inline fun entry(vararg argArray: Any) {
		if (underlyingLogger.isTraceEnabled) {
			underlyingLogger.trace("entry({})", argArray)
		}
	}

	override inline fun exit() {
		if (underlyingLogger.isTraceEnabled) {
			underlyingLogger.trace("exit")
		}
	}

	override inline fun <T : Any?> exit(retval: T): T {
		if (underlyingLogger.isTraceEnabled) {
			underlyingLogger.trace("exit({}}", retval)
		}
		return retval
	}

	override inline fun <T : Throwable> throwing(throwable: T): T {
		if (underlyingLogger.isErrorEnabled) {
			underlyingLogger.error("throwing($throwable)", throwable)
		}
		return throwable
	}

	override inline fun <T : Throwable> catching(throwable: T) {
		if (underlyingLogger.isErrorEnabled) {
			underlyingLogger.error("catching($throwable)", throwable)
		}
	}
}
