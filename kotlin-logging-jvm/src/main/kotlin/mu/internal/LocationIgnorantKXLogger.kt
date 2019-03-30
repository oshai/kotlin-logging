package mu.internal

import mu.KLogger
import mu.KXLogger
import org.slf4j.Logger
import org.slf4j.Marker

/**
 * A class wrapping a [Logger] instance that is not location aware
 * all methods of [KLogger] has default implementation
 * the rest of the methods are delegated to [Logger]
 * Hence no implemented methods
 */
internal class LocationIgnorantKXLogger(override val underlyingLogger: Logger) : LocationIgnorantKLogger(underlyingLogger), KXLogger, Logger by underlyingLogger {
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

    override inline fun <T : Any> exit(retval: T): T {
        if (underlyingLogger.isTraceEnabled) {
            underlyingLogger.trace("exit({}}", retval)
        }
        return retval
    }

    override inline fun  <T : Throwable> throwing(throwable: T): T {
        if (underlyingLogger.isErrorEnabled) {
            underlyingLogger.error("throwing($throwable)", throwable)
        }
        return throwable
    }

    override inline fun  <T : Throwable> catching(throwable: T) {
        if (underlyingLogger.isErrorEnabled) {
            underlyingLogger.error("catching($throwable)", throwable)
        }
    }

    override inline fun trace(marker: Marker?, format: String?, vararg argArray: Any?) {
        super.trace(marker, format, *argArray)
    }

    override inline fun trace(msg: String?, t: Throwable?) {
        super.trace(msg, t)
    }

    override inline fun trace(msg: String?) {
        super.trace(msg)
    }

    override inline fun trace(format: String?, arg: Any?) {
        super.trace(format, arg)
    }

    override inline fun trace(marker: Marker?, msg: String?, t: Throwable?) {
        super.trace(marker, msg, t)
    }

    override inline fun trace(marker: Marker?, format: String?, arg: Any?) {
        super.trace(marker, format, arg)
    }

    override inline fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        super.trace(marker, format, arg1, arg2)
    }

    override inline fun trace(marker: Marker?, msg: String?) {
        super.trace(marker, msg)
    }

    override inline fun trace(format: String?, vararg arguments: Any?) {
        super.trace(format, *arguments)
    }

    override inline fun trace(format: String?, arg1: Any?, arg2: Any?) {
        super.trace(format, arg1, arg2)
    }

    override fun trace(msg: () -> Any?) {
        super.trace(msg)
    }

    override fun trace(t: Throwable?, msg: () -> Any?) {
        super.trace(t, msg)
    }

    override fun trace(marker: Marker?, msg: () -> Any?) {
        super.trace(marker, msg)
    }

    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        super.trace(marker, t, msg)
    }

    override inline fun debug(marker: Marker?, format: String?, arg: Any?) {
        super.debug(marker, format, arg)
    }

    override inline fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        super.debug(marker, format, arg1, arg2)
    }

    override inline fun debug(format: String?, arg: Any?) {
        super.debug(format, arg)
    }

    override inline fun debug(msg: String?, t: Throwable?) {
        super.debug(msg, t)
    }

    override inline fun debug(format: String?, arg1: Any?, arg2: Any?) {
        super.debug(format, arg1, arg2)
    }

    override inline fun debug(msg: String?) {
        super.debug(msg)
    }

    override inline fun debug(marker: Marker?, format: String?, vararg arguments: Any?) {
        super.debug(marker, format, *arguments)
    }

    override inline fun debug(format: String?, vararg arguments: Any?) {
        super.debug(format, *arguments)
    }

    override inline fun debug(marker: Marker?, msg: String?, t: Throwable?) {
        super.debug(marker, msg, t)
    }

    override inline fun debug(marker: Marker?, msg: String?) {
        super.debug(marker, msg)
    }

    override fun debug(msg: () -> Any?) {
        super.debug(msg)
    }

    override fun debug(t: Throwable?, msg: () -> Any?) {
        super.debug(t, msg)
    }

    override fun debug(marker: Marker?, msg: () -> Any?) {
        super.debug(marker, msg)
    }

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        super.debug(marker, t, msg)
    }

    override inline fun info(marker: Marker?, msg: String?) {
        super.info(marker, msg)
    }

    override inline fun info(msg: String?) {
        super.info(msg)
    }

    override inline fun info(msg: String?, t: Throwable?) {
        super.info(msg, t)
    }

    override inline fun info(format: String?, vararg arguments: Any?) {
        super.info(format, *arguments)
    }

    override inline fun info(marker: Marker?, format: String?, arg: Any?) {
        super.info(marker, format, arg)
    }

    override inline fun info(marker: Marker?, msg: String?, t: Throwable?) {
        super.info(marker, msg, t)
    }

    override inline fun info(format: String?, arg1: Any?, arg2: Any?) {
        super.info(format, arg1, arg2)
    }

    override inline fun info(format: String?, arg: Any?) {
        super.info(format, arg)
    }

    override inline fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        super.info(marker, format, arg1, arg2)
    }

    override inline fun info(marker: Marker?, format: String?, vararg arguments: Any?) {
        super.info(marker, format, *arguments)
    }

    override fun info(msg: () -> Any?) {
        super.info(msg)
    }

    override fun info(t: Throwable?, msg: () -> Any?) {
        super.info(t, msg)
    }

    override fun info(marker: Marker?, msg: () -> Any?) {
        super.info(marker, msg)
    }

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        super.info(marker, t, msg)
    }

    override inline fun warn(format: String?, arg1: Any?, arg2: Any?) {
        super.warn(format, arg1, arg2)
    }

    override inline fun warn(marker: Marker?, format: String?, vararg arguments: Any?) {
        super.warn(marker, format, *arguments)
    }

    override inline fun warn(msg: String?, t: Throwable?) {
        super.warn(msg, t)
    }

    override inline fun warn(format: String?, arg: Any?) {
        super.warn(format, arg)
    }

    override inline fun warn(marker: Marker?, format: String?, arg: Any?) {
        super.warn(marker, format, arg)
    }

    override inline fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        super.warn(marker, format, arg1, arg2)
    }

    override inline fun  warn(msg: String?) {
        super.warn(msg)
    }

    override inline fun  warn(marker: Marker?, msg: String?, t: Throwable?) {
        super.warn(marker, msg, t)
    }

    override inline fun  warn(marker: Marker?, msg: String?) {
        super.warn(marker, msg)
    }

    override inline fun  warn(format: String?, vararg arguments: Any?) {
        super.warn(format, *arguments)
    }

    override fun  warn(msg: () -> Any?) {
        super.warn(msg)
    }

    override fun  warn(t: Throwable?, msg: () -> Any?) {
        super.warn(t, msg)
    }

    override fun  warn(marker: Marker?, msg: () -> Any?) {
        super.warn(marker, msg)
    }

    override fun  warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        super.warn(marker, t, msg)
    }

    override inline fun  error(format: String?, arg: Any?) {
        super.error(format, arg)
    }

    override inline fun  error(format: String?, vararg arguments: Any?) {
        super.error(format, *arguments)
    }

    override inline fun  error(marker: Marker?, msg: String?) {
        super.error(marker, msg)
    }

    override inline fun  error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        super.error(marker, format, arg1, arg2)
    }

    override inline fun  error(format: String?, arg1: Any?, arg2: Any?) {
        super.error(format, arg1, arg2)
    }

    override inline fun  error(msg: String?) {
        super.error(msg)
    }

    override inline fun  error(msg: String?, t: Throwable?) {
        super.error(msg, t)
    }

    override fun  error(marker: Marker?, msg: String?, t: Throwable?) {
        super.error(marker, msg, t)
    }

    override fun  error(marker: Marker?, format: String?, arg: Any?) {
        super.error(marker, format, arg)
    }

    override fun  error(marker: Marker?, format: String?, vararg arguments: Any?) {
        super.error(marker, format, *arguments)
    }

    override fun  error(msg: () -> Any?) {
        super.error(msg)
    }

    override fun  error(t: Throwable?, msg: () -> Any?) {
        super.error(t, msg)
    }

    override fun  error(marker: Marker?, msg: () -> Any?) {
        super.error(marker, msg)
    }

    override fun  error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        super.error(marker, t, msg)
    }

    override inline fun  getName(): String {
        return super.getName()
    }

    override inline fun  isErrorEnabled(marker: Marker?): Boolean {
        return super.isErrorEnabled(marker)
    }

    override inline fun  isErrorEnabled(): Boolean {
        return super.isErrorEnabled()
    }

    override inline fun  isDebugEnabled(): Boolean {
        return super.isDebugEnabled()
    }

    override inline fun  isDebugEnabled(marker: Marker?): Boolean {
        return super.isDebugEnabled(marker)
    }

    override inline fun  isInfoEnabled(): Boolean {
        return super.isInfoEnabled()
    }

    override inline fun  isInfoEnabled(marker: Marker?): Boolean {
        return super.isInfoEnabled(marker)
    }

    override inline fun  isWarnEnabled(marker: Marker?): Boolean {
        return super.isWarnEnabled(marker)
    }

    override inline fun  isWarnEnabled(): Boolean {
        return super.isWarnEnabled()
    }

    override inline fun  isTraceEnabled(): Boolean {
        return super.isTraceEnabled()
    }

    override inline fun  isTraceEnabled(marker: Marker?): Boolean {
        return super.isTraceEnabled(marker)
    }
}
