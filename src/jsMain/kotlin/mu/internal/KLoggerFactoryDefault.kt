package mu.internal

import mu.KLogger
import mu.KLoggerFactory

/**
 * factory methods to obtain a [KLogger]
 */
@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerFactoryDefault : KLoggerFactory {
    actual override fun logger(name: String): KLogger = KLoggerJS(name)

    actual override fun logger(func: () -> Unit): KLogger = KLoggerJS(func::class.js.name)

}
