package mu.internal

import mu.KLogger
import mu.KLoggerFactory

/**
 * factory methods to obtain a [KLogger]
 */
@Suppress("NOTHING_TO_INLINE")
internal expect object KLoggerFactoryDefault : KLoggerFactory {
    /**
     * get logger by explicit name
     */
    override fun logger(name: String): KLogger

    /**
     * get logger for the method, assuming it was declared at the logger file/class
     */
    override fun logger(func: () -> Unit): KLogger

}
