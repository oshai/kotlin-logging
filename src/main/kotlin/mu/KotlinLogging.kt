package mu

import mu.internal.KLoggerFactory


object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * val logger = KotlinLogging.logger {}
     */
    fun logger(func: () -> Unit): KLogger = KLoggerFactory.logger(func)

    fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}
