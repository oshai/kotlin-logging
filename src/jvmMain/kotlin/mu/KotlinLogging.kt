package mu

import mu.internal.KLoggerFactoryDefault
import org.slf4j.Logger


actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    actual fun logger(func: () -> Unit): KLogger = kLoggerFactory.logger(func)

    actual fun logger(name: String): KLogger = kLoggerFactory.logger(name)

    fun logger(underlyingLogger: Logger) = KLoggerFactoryDefault.wrapJLogger(underlyingLogger)

    fun logger(kLoggable: KLoggable): KLogger = kLoggerFactory.logger(kLoggable)

    actual var kLoggerFactory: KLoggerFactory = KLoggerFactoryDefault
}

fun Logger.toKLogger() = KotlinLogging.logger(this)
