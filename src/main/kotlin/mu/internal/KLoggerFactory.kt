package mu.internal

import mu.KLoggable
import mu.KLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * factory methods to obtain a logger
 */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerFactory {

    /**
     * get logger for the class
     */
    inline internal fun logger(loggable: KLoggable): KLogger =
            KLogger(jLogger(KLoggerNameResolver.name(loggable.javaClass)))

    /**
     * get logger by explicit name
     */
    inline internal fun logger(name: String): KLogger = KLogger(jLogger(name))

    /**
     * get a java logger by name
     */
    inline internal fun jLogger(name: String): Logger = LoggerFactory.getLogger(name)


}

