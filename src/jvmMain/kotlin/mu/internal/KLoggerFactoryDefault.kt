package mu.internal

import mu.KLoggable
import mu.KLogger
import mu.KLoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger

/**
 * factory methods to obtain a [Logger]
 */
@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerFactoryDefault : KLoggerFactory {

    /**
     * get logger for the class
     */
    override fun logger(loggable: KLoggable): KLogger =
        logger(KLoggerNameResolver.name(loggable.javaClass))

    /**
     * get logger by explicit name
     */
    actual override fun logger(name: String): KLogger = wrapJLogger(jLogger(name))

    /**
     * get logger for the method, assuming it was declared at the logger file/class
     */
     actual override fun logger(func: () -> Unit): KLogger =
        logger(KLoggerNameResolver.name(func))

    /**
     * get a java logger by name
     */
    private inline fun jLogger(name: String): Logger = LoggerFactory.getLogger(name)

    /**
     * wrap java logger based on location awareness
     */
    internal inline fun wrapJLogger(jLogger: Logger): KLogger =
        if (jLogger is LocationAwareLogger)
            LocationAwareKLogger(jLogger)
        else
            LocationIgnorantKLogger(jLogger)

}

