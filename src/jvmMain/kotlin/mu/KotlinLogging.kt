package mu

import mu.internal.KLoggerFactory


actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * val logger = KotlinLogging.logger {}
     */
    actual fun logger(func: () -> Unit): KLogger = KLoggerFactory.logger(func)

    actual fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}
