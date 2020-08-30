package mu

import mu.internal.KLoggerLinux


actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    actual fun logger(func: () -> Unit): KLogger = KLoggerLinux(func::class.qualifiedName ?: "")

    actual fun logger(name: String): KLogger = KLoggerLinux(name)
}
