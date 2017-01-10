package mu.internal

import mu.KLogger
import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.helpers.MessageFormatter
import org.slf4j.spi.LocationAwareLogger

/**
 * A class wrapping a [LocationAwareLogger] instance preserving
 * location information with the correct fully qualified class name.
 */
internal class LocationAwareKLogger(private val jLogger: LocationAwareLogger) : KLogger, Logger by jLogger {

    private val fqcn: String = LocationAwareKLogger::class.java.name

    override fun trace(msg: String?) {
        if (!jLogger.isTraceEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.TRACE_INT, msg, null, null)
    }


    override fun trace(format: String?, arg: Any?) {
        if (!jLogger.isTraceEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.TRACE_INT, formattedMessage,
                arrayOf(arg), null)
    }


    override fun trace(format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isTraceEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.TRACE_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun trace(format: String?, argArray: Array<Any?>) {
        if (!jLogger.isTraceEnabled)
            return

        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.TRACE_INT, formattedMessage, argArray, null)
    }


    override fun trace(msg: String?, t: Throwable?) {
        if (!jLogger.isTraceEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.TRACE_INT, msg, null, t)
    }


    override fun trace(marker: Marker, msg: String?) {
        if (!jLogger.isTraceEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.TRACE_INT, msg, null, null)
    }


    override fun trace(marker: Marker, format: String?, arg: Any?) {
        if (!jLogger.isTraceEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.TRACE_INT, formattedMessage,
                arrayOf(arg), null)
    }


    override fun trace(marker: Marker, format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isTraceEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.TRACE_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun trace(marker: Marker, format: String?, argArray: Array<Any?>) {
        if (!jLogger.isTraceEnabled)
            return
        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.TRACE_INT, formattedMessage, argArray, null)
    }


    override fun trace(marker: Marker, msg: String?, t: Throwable?) {
        if (!jLogger.isTraceEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.TRACE_INT, msg, null, t)
    }

    override fun debug(msg: String?) {
        if (!jLogger.isDebugEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.DEBUG_INT, msg, null, null)
    }


    override fun debug(format: String?, arg: Any?) {
        if (!jLogger.isDebugEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.DEBUG_INT, formattedMessage,
                arrayOf(arg), null)
    }


    override fun debug(format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isDebugEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.DEBUG_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun debug(format: String?, argArray: Array<Any?>) {
        if (!jLogger.isDebugEnabled)
            return

        val ft = MessageFormatter.arrayFormat(format, argArray)
        jLogger.log(null, fqcn,
                LocationAwareLogger.DEBUG_INT, ft.message, ft.argArray, ft.throwable)
    }


    override fun debug(msg: String?, t: Throwable?) {
        if (!jLogger.isDebugEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.DEBUG_INT, msg, null, t)
    }


    override fun debug(marker: Marker, msg: String?) {
        if (!jLogger.isDebugEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.DEBUG_INT, msg, null, null)
    }


    override fun debug(marker: Marker, format: String?, arg: Any?) {
        if (!jLogger.isDebugEnabled)
            return
        val ft = MessageFormatter.format(format, arg)
        jLogger.log(marker, fqcn,
                LocationAwareLogger.DEBUG_INT, ft.message, ft.argArray, ft.throwable)
    }


    override fun debug(marker: Marker, format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isDebugEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.DEBUG_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun debug(marker: Marker, format: String?, argArray: Array<Any?>) {
        if (!jLogger.isDebugEnabled)
            return

        val ft = MessageFormatter.arrayFormat(format, argArray)
        jLogger.log(marker, fqcn,
                LocationAwareLogger.DEBUG_INT, ft.message, argArray, ft.throwable)
    }


    override fun debug(marker: Marker, msg: String?, t: Throwable?) {
        if (!jLogger.isDebugEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.DEBUG_INT, msg, null, t)
    }

    override fun info(msg: String?) {
        if (!jLogger.isInfoEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.INFO_INT, msg, null, null)
    }


    override fun info(format: String?, arg: Any?) {
        if (!jLogger.isInfoEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.INFO_INT, formattedMessage, arrayOf(arg),
                null)
    }


    override fun info(format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isInfoEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.INFO_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun info(format: String?, argArray: Array<Any?>) {
        if (!jLogger.isInfoEnabled)
            return

        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.INFO_INT, formattedMessage, argArray, null)
    }


    override fun info(msg: String?, t: Throwable?) {
        if (!jLogger.isInfoEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.INFO_INT, msg, null, t)
    }


    override fun info(marker: Marker, msg: String?) {
        if (!jLogger.isInfoEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.INFO_INT, msg, null, null)
    }


    override fun info(marker: Marker, format: String?, arg: Any?) {
        if (!jLogger.isInfoEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.INFO_INT, formattedMessage, arrayOf(arg),
                null)
    }


    override fun info(marker: Marker, format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isInfoEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.INFO_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun info(marker: Marker, format: String?, argArray: Array<Any?>) {
        if (!jLogger.isInfoEnabled)
            return
        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.INFO_INT, formattedMessage, argArray, null)
    }


    override fun info(marker: Marker, msg: String?, t: Throwable?) {
        if (!jLogger.isInfoEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.INFO_INT, msg, null, t)
    }

    override fun warn(msg: String?) {
        if (!jLogger.isWarnEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.WARN_INT, msg, null, null)
    }


    override fun warn(format: String?, arg: Any?) {
        if (!jLogger.isWarnEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.WARN_INT, formattedMessage, arrayOf(arg),
                null)
    }


    override fun warn(format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isWarnEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.WARN_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun warn(format: String?, argArray: Array<Any?>) {
        if (!jLogger.isWarnEnabled)
            return

        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.WARN_INT, formattedMessage, argArray, null)
    }


    override fun warn(msg: String?, t: Throwable?) {
        if (!jLogger.isWarnEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.WARN_INT, msg, null, t)
    }


    override fun warn(marker: Marker, msg: String?) {
        if (!jLogger.isWarnEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.WARN_INT, msg, null, null)
    }


    override fun warn(marker: Marker, format: String?, arg: Any?) {
        if (!jLogger.isWarnEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.WARN_INT, formattedMessage, arrayOf(arg),
                null)
    }


    override fun warn(marker: Marker, format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isWarnEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.WARN_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun warn(marker: Marker, format: String?, argArray: Array<Any?>) {
        if (!jLogger.isWarnEnabled)
            return
        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.WARN_INT, formattedMessage, argArray, null)
    }


    override fun warn(marker: Marker, msg: String?, t: Throwable?) {
        if (!jLogger.isWarnEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.WARN_INT, msg, null, t)
    }

    override fun error(msg: String?) {
        if (!jLogger.isErrorEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.ERROR_INT, msg, null, null)
    }


    override fun error(format: String?, arg: Any?) {
        if (!jLogger.isErrorEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.ERROR_INT, formattedMessage,
                arrayOf(arg), null)
    }


    override fun error(format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isErrorEnabled)
            return

        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.ERROR_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun error(format: String?, argArray: Array<Any?>) {
        if (!jLogger.isErrorEnabled)
            return

        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(null, fqcn,
                LocationAwareLogger.ERROR_INT, formattedMessage, argArray, null)
    }


    override fun error(msg: String?, t: Throwable?) {
        if (!jLogger.isErrorEnabled)
            return

        jLogger.log(null, fqcn,
                LocationAwareLogger.ERROR_INT, msg, null, t)
    }


    override fun error(marker: Marker, msg: String?) {
        if (!jLogger.isErrorEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.ERROR_INT, msg, null, null)
    }


    override fun error(marker: Marker, format: String?, arg: Any?) {
        if (!jLogger.isErrorEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.ERROR_INT, formattedMessage,
                arrayOf(arg), null)
    }


    override fun error(marker: Marker, format: String?, arg1: Any?, arg2: Any?) {
        if (!jLogger.isErrorEnabled)
            return
        val formattedMessage = MessageFormatter.format(format, arg1, arg2).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.ERROR_INT, formattedMessage, arrayOf(arg1, arg2), null)
    }


    override fun error(marker: Marker, format: String?, argArray: Array<Any?>) {
        if (!jLogger.isErrorEnabled)
            return
        val formattedMessage = MessageFormatter.arrayFormat(format, argArray).message
        jLogger.log(marker, fqcn,
                LocationAwareLogger.ERROR_INT, formattedMessage, argArray, null)
    }


    override fun error(marker: Marker, msg: String?, t: Throwable?) {
        if (!jLogger.isErrorEnabled)
            return
        jLogger.log(marker, fqcn,
                LocationAwareLogger.ERROR_INT, msg, null, t)
    }

}
