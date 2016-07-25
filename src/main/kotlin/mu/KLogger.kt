package mu

import org.slf4j.Logger

/**
 * An extension for org.slf4j.Logger with Lazy message evaluation
 * example:
 * logger.info{"this is $lazy evaluated string"}
 */
class KLogger(jLogger: Logger): Logger by jLogger{

    inline fun trace(msg: () -> Any?) {
        if (isTraceEnabled) trace(msg.invoke().toString())
    }
    inline fun debug(msg: () -> Any?) {
        if (isDebugEnabled) debug(msg.invoke().toString())
    }
    inline fun info(msg: () -> Any?) {
        if (isInfoEnabled) info(msg.invoke().toString())
    }
    inline fun warn(msg: () -> Any?) {
        if (isWarnEnabled) warn(msg.invoke().toString())
    }
    inline fun error(msg: () -> Any?) {
        if (isErrorEnabled) error(msg.invoke().toString())
    }
}
