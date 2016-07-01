package mu

import org.slf4j.Logger


class KLogger(jLogger: Logger): Logger by jLogger{

    fun trace(msg: () -> String): Unit {
        if (isTraceEnabled) trace(msg.invoke())
    }
    fun debug(msg: () -> String): Unit {
        if (isDebugEnabled) debug(msg.invoke())
    }
    fun info(msg: () -> String): Unit {
        if (isInfoEnabled) info(msg.invoke())
    }
    fun warn(msg: () -> String): Unit {
        if (isWarnEnabled) warn(msg.invoke())
    }
    fun error(msg: () -> String): Unit = if (isErrorEnabled) error(msg.invoke())
}