package mu

import org.slf4j.Logger

/**
 * An extension for org.slf4j.Logger with Lazy message evaluation
 * example:
 * logger.info{"this is $lazy evaluated string"}
 */
class KLogger(jLogger: Logger): Logger by jLogger{

    fun trace(msg: () -> String) {
        if (isTraceEnabled) trace(msg.invoke())
    }
    fun debug(msg: () -> String) {
        if (isDebugEnabled) debug(msg.invoke())
    }
    fun info(msg: () -> String) {
        if (isInfoEnabled) info(msg.invoke())
    }
    fun warn(msg: () -> String) {
        if (isWarnEnabled) warn(msg.invoke())
    }
    fun error(msg: () -> String) {
        if (isErrorEnabled) error(msg.invoke())
    }
}
