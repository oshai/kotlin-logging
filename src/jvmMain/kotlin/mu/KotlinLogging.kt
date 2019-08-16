package mu

import mu.internal.KLoggerFactory
import org.slf4j.Logger


actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * val logger = KotlinLogging.logger {}
     */
    actual fun logger(func: () -> Unit): KLogger = KLoggerFactory.logger(func)

    actual fun logger(name: String): KLogger = KLoggerFactory.logger(name)

    fun logger(underlyingLogger: Logger) = KLoggerFactory.wrapJLogger(underlyingLogger)
}

fun Logger.toKLogger() = KotlinLogging.logger(this)
