package mu

import mu.internal.KLoggerMacos


actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    actual fun logger(func: () -> Unit): KLogger = KLoggerMacos(func::class.qualifiedName ?: "")

    actual fun logger(name: String): KLogger = KLoggerMacos (name)
}
