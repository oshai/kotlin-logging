package mu

import mu.internal.KLoggerJS


actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    actual fun logger(func: () -> Unit): KLogger = KLoggerJS(func::class.js.name)

    actual fun logger(name: String): KLogger = KLoggerJS(name)
}
