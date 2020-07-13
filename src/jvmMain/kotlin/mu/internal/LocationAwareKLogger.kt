package mu.internal

import mu.KLogger
import mu.KMarkerFactory
import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.helpers.MessageFormatter
import org.slf4j.spi.LocationAwareLogger

/**
 * A class wrapping a [LocationAwareLogger] instance preserving
 * location information with the correct fully qualified class name.
 */
internal class LocationAwareKLogger(private val underlyingLogger: LocationAwareLogger) : KLogger {

    private val fqcn: String = LocationAwareKLogger::class.java.name
    private val ENTRY = KMarkerFactory.getMarker("ENTRY")
    private val EXIT = KMarkerFactory.getMarker("EXIT")

    private val THROWING = KMarkerFactory.getMarker("THROWING")
    private val CATCHING = KMarkerFactory.getMarker("CATCHING")
    private val EXITONLY = "exit"
    private val EXITMESSAGE = "exit with ({})"

    override val name: String
        get() = underlyingLogger.name

    /**
     * Lazy add a log message if isTraceEnabled is true
     */
    override fun trace(msg: () -> Any?) {
        if (underlyingLogger.isTraceEnabled)
            logAt(
                logLevel = LocationAwareLogger.TRACE_INT,
                msg = msg
            )
    }

    /**
     * Lazy add a log message if isDebugEnabled is true
     */
    override fun debug(msg: () -> Any?) {
        if (underlyingLogger.isDebugEnabled)
            logAt(
                logLevel = LocationAwareLogger.DEBUG_INT,
                msg = msg
            )
    }

    /**
     * Lazy add a log message if isInfoEnabled is true
     */
    override fun info(msg: () -> Any?) {
        if (underlyingLogger.isInfoEnabled)
            logAt(
                logLevel = LocationAwareLogger.INFO_INT,
                msg = msg
            )
    }

    /**
     * Lazy add a log message if isWarnEnabled is true
     */
    override fun warn(msg: () -> Any?) {
        if (underlyingLogger.isWarnEnabled)
            logAt(
                logLevel = LocationAwareLogger.WARN_INT,
                msg = msg
            )
    }

    /**
     * Lazy add a log message if isErrorEnabled is true
     */
    override fun error(msg: () -> Any?) {
        if (underlyingLogger.isErrorEnabled)
            logAt(
                logLevel = LocationAwareLogger.ERROR_INT,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with throwable payload if isTraceEnabled is true
     */
    override fun trace(t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isTraceEnabled)
            logAt(
                logLevel = LocationAwareLogger.TRACE_INT,
                t = t,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with throwable payload if isDebugEnabled is true
     */
    override fun debug(t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isDebugEnabled)
            logAt(
                logLevel = LocationAwareLogger.DEBUG_INT,
                t = t,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with throwable payload if isInfoEnabled is true
     */
    override fun info(t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isInfoEnabled)
            logAt(
                logLevel = LocationAwareLogger.INFO_INT,
                t = t,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with throwable payload if isWarnEnabled is true
     */
    override fun warn(t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isWarnEnabled)
            logAt(
                logLevel = LocationAwareLogger.WARN_INT,
                t = t,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with throwable payload if isErrorEnabled is true
     */
    override fun error(t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isErrorEnabled)
            logAt(
                logLevel = LocationAwareLogger.ERROR_INT,
                t = t,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with a marker if isTraceEnabled is true
     */
    override fun trace(marker: Marker?, msg: () -> Any?) {
        if (underlyingLogger.isTraceEnabled)
            logAt(
                logLevel = LocationAwareLogger.TRACE_INT,
                marker = marker,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with a marker if isDebugEnabled is true
     */
    override fun debug(marker: Marker?, msg: () -> Any?) {
        if (underlyingLogger.isDebugEnabled)
            logAt(
                logLevel = LocationAwareLogger.DEBUG_INT,
                marker = marker,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with a marker if isInfoEnabled is true
     */
    override fun info(marker: Marker?, msg: () -> Any?) {
        if (underlyingLogger.isInfoEnabled)
            logAt(
                logLevel = LocationAwareLogger.INFO_INT,
                marker = marker,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with a marker if isWarnEnabled is true
     */
    override fun warn(marker: Marker?, msg: () -> Any?) {
        if (underlyingLogger.isWarnEnabled)
            logAt(
                logLevel = LocationAwareLogger.WARN_INT,
                marker = marker,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with a marker if isErrorEnabled is true
     */
    override fun error(marker: Marker?, msg: () -> Any?) {
        if (underlyingLogger.isErrorEnabled)
            logAt(
                logLevel = LocationAwareLogger.ERROR_INT,
                marker = marker,
                msg = msg
            )
    }

    /**
     * Lazy add a log message with a marker and throwable payload if isTraceEnabled is true
     */
    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isTraceEnabled)
            logAt(
                logLevel = LocationAwareLogger.TRACE_INT,
                marker = marker,
                t = t,
                msg = msg
            )
    }

    private inline fun logAt(logLevel: Int, marker: Marker? = null, t: Throwable? = null, msg: (() -> Any?)) {
        underlyingLogger.log(
            marker, fqcn, logLevel, msg.toStringSafe(), null, t
        )
    }
    /**
     * Lazy add a log message with a marker and throwable payload if isDebugEnabled is true
     */
    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isDebugEnabled) {
            logAt(
                logLevel = LocationAwareLogger.DEBUG_INT,
                marker = marker,
                msg = msg,
                t = t
            )
        }
    }

    /**
     * Lazy add a log message with a marker and throwable payload if isInfoEnabled is true
     */
    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isInfoEnabled)
            logAt(
                logLevel = LocationAwareLogger.INFO_INT,
                marker = marker,
                msg = msg,
                t = t
            )
    }

    /**
     * Lazy add a log message with a marker and throwable payload if isWarnEnabled is true
     */
    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isWarnEnabled)
            logAt(
                logLevel = LocationAwareLogger.WARN_INT,
                marker = marker,
                msg = msg,
                t = t
            )
    }

    /**
     * Lazy add a log message with a marker and throwable payload if isErrorEnabled is true
     */
    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (underlyingLogger.isErrorEnabled)
            logAt(
                logLevel = LocationAwareLogger.ERROR_INT,
                marker = marker,
                msg = msg,
                t = t
            )
    }

    override fun <T : Throwable> catching(throwable: T) {
        if (underlyingLogger.isErrorEnabled) {
            underlyingLogger.log(CATCHING, fqcn, LocationAwareLogger.ERROR_INT, "catching", null, throwable)
        }
    }

    override fun entry(vararg argArray: Any?) {
        if (underlyingLogger.isTraceEnabled(ENTRY)) {
            val tp = MessageFormatter.arrayFormat(buildMessagePattern(argArray.size), argArray)
            underlyingLogger.log(ENTRY, fqcn, LocationAwareLogger.TRACE_INT, tp.message, null, null)
        }
    }

    override fun exit() {
        if (underlyingLogger.isTraceEnabled(EXIT)) {
            underlyingLogger.log(EXIT, fqcn, LocationAwareLogger.TRACE_INT, EXITONLY, null, null)
        }
    }

    override fun <T : Any?> exit(result: T): T {
        if (underlyingLogger.isTraceEnabled(EXIT)) {
            val tp = MessageFormatter.format(EXITMESSAGE, result)
            underlyingLogger.log(
                EXIT, fqcn, LocationAwareLogger.TRACE_INT, tp.message, arrayOf<Any?>(result), tp.throwable
            )
        }
        return result
    }

    override fun <T : Throwable> throwing(throwable: T): T {
        underlyingLogger.log(THROWING, fqcn, LocationAwareLogger.ERROR_INT, "throwing", null, throwable)
        throw throwable
    }

    private fun buildMessagePattern(len: Int): String {
        return (1..len).joinToString(
            separator = ", ",
            prefix = "entry with (",
            postfix = ")") {
            "{}"
        }
    }

}
