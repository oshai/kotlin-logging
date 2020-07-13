package mu

import mu.internal.KLoggerFactoryDefault


actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    actual fun logger(func: () -> Unit): KLogger = kLoggerFactory.logger(func)

    actual fun logger(name: String): KLogger = kLoggerFactory.logger(name)
    actual var kLoggerFactory: KLoggerFactory = KLoggerFactoryDefault
}
