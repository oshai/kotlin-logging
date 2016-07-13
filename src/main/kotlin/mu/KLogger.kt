package mu

import org.slf4j.Logger

/**
 * An extension for org.slf4j.Logger with Lazy message evaluation
 * example:
 * logger.info{"this is $lazy evaluated string"}
 */
class KLogger(jLogger: Logger): Logger by jLogger{

    fun trace(msg: () -> Any?) {
        if (isTraceEnabled) trace(msg.invoke().toString())
    }
    fun debug(msg: () -> Any?) {
        if (isDebugEnabled) debug(msg.invoke().toString())
    }
    fun info(msg: () -> Any?) {
        if (isInfoEnabled) info(msg.invoke().toString())
    }
    fun warn(msg: () -> Any?) {
        if (isWarnEnabled) warn(msg.invoke().toString())
    }
    fun error(msg: () -> Any?) {
        if (isErrorEnabled) error(msg.invoke().toString())
    }
}
