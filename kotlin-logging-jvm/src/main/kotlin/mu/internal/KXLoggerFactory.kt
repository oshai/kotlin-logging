package mu.internal

import mu.KXLoggable
import mu.KXLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger

/**
 * factory methods to obtain a [Logger]
 */
@Suppress("NOTHING_TO_INLINE")
internal object KXLoggerFactory {
    /**
     * get a java xlogger by name
     */
    private inline fun jLogger(name: String): Logger = LoggerFactory.getLogger(name)

    /**
     * wrap java xlogger based on location awareness
     */
    private inline fun wrapJLogger(jLogger: Logger): KXLogger =
            if(jLogger is LocationAwareLogger)
                LocationAwareKXLogger(jLogger)
            else
                LocationIgnorantKXLogger(jLogger)
    /**
     * get logger by explicit name
     */
    internal inline fun xlogger(name: String): KXLogger = wrapJLogger(jLogger(name))

    /**
     * get logger for the class
     */
    internal inline fun xlogger(loggable: KXLoggable): KXLogger =
            xlogger(KLoggerNameResolver.name(loggable.javaClass))
    /**
     * get logger for the method, assuming it was declared at the logger file/class
     */
    internal inline fun xlogger(noinline func: () -> Unit): KXLogger =
            xlogger(KLoggerNameResolver.name(func))

}

