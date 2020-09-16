package mu

import mu.internal.KLoggerLinux


public actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    public actual fun logger(func: () -> Unit): KLogger = KLoggerLinux(func::class.qualifiedName ?: "")

    public actual fun logger(name: String): KLogger = KLoggerLinux(name)
}
