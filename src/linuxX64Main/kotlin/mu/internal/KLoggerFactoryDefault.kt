package mu.internal

import mu.KLogger
import mu.KLoggerFactory

/**
 * factory methods to obtain a [KLogger]
 */
@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerFactoryDefault : KLoggerFactory {
    /**
     * get logger by explicit name
     */
    actual override fun logger(name: String): KLogger = KLoggerLinux(name)

    /**
     * get logger for the method, assuming it was declared at the logger file/class
     */
    actual override fun logger(func: () -> Unit): KLogger = logger(func::class.qualifiedName ?: "")

}
