package mu

import org.slf4j.Logger

/**
 * An extension for [Logger] with Lazy message evaluation
 * example:
 * logger.info{"this is $lazy evaluated string"}
 */
interface KLogger: Logger{

    /**
     * Lazy add a log message if isTraceEnabled is true
     */
    fun trace(msg: () -> Any?) {
        if (isTraceEnabled) trace(msg.invoke().toString())
    }

    /**
     * Lazy add a log message if isDebugEnabled is true
     */
    fun debug(msg: () -> Any?) {
        if (isDebugEnabled) debug(msg.invoke().toString())
    }

    /**
     * Lazy add a log message if isInfoEnabled is true
     */
    fun info(msg: () -> Any?) {
        if (isInfoEnabled) info(msg.invoke().toString())
    }

    /**
     * Lazy add a log message if isWarnEnabled is true
     */
    fun warn(msg: () -> Any?) {
        if (isWarnEnabled) warn(msg.invoke().toString())
    }

    /**
     * Lazy add a log message if isErrorEnabled is true
     */
    fun error(msg: () -> Any?) {
        if (isErrorEnabled) error(msg.invoke().toString())
    }

    /**
     * Lazy add a log message with throwable payload if isTraceEnabled is true
     */
    fun trace(t: Throwable, msg: () -> Any?) {
        if (isTraceEnabled) trace(msg.invoke().toString(), t)
    }

    /**
     * Lazy add a log message with throwable payload if isDebugEnabled is true
     */
    fun debug(t: Throwable, msg: () -> Any?) {
        if (isDebugEnabled) debug(msg.invoke().toString(), t)
    }

    /**
     * Lazy add a log message with throwable payload if isInfoEnabled is true
     */
    fun info(t: Throwable, msg: () -> Any?) {
        if (isInfoEnabled) info(msg.invoke().toString(), t)
    }

    /**
     * Lazy add a log message with throwable payload if isWarnEnabled is true
     */
    fun warn(t: Throwable, msg: () -> Any?) {
        if (isWarnEnabled) warn(msg.invoke().toString(), t)
    }

    /**
     * Lazy add a log message with throwable payload if isErrorEnabled is true
     */
    fun error(t: Throwable, msg: () -> Any?) {
        if (isErrorEnabled) error(msg.invoke().toString(), t)
    }
}
