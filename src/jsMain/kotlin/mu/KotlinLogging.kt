package mu

import mu.internal.KLoggerJS


public actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    public actual fun logger(func: () -> Unit): KLogger = KLoggerJS(func::class.js.name)

    public actual fun logger(name: String): KLogger = KLoggerJS(name)

    // This method uses simpleName because qualifiedName is not available
    // in KotlinJS as of v1.6.
    public actual inline fun <reified T> T.logger(): KLogger = logger(T::class.simpleName ?: "")
}
