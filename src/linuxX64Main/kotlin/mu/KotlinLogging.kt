package mu

import mu.internal.NativeLogger

actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    actual fun logger(func: () -> Unit): KLogger =
        NativeLogger(func::class.simpleName!!, KotlinLoggingConfiguration())


    actual fun logger(name: String): KLogger =
        NativeLogger(name, KotlinLoggingConfiguration())

    fun logger(config: KotlinLoggingConfiguration, func: () -> Unit): KLogger =
        NativeLogger(func::class.simpleName!!, config)


    fun logger(name: String, config: KotlinLoggingConfiguration): KLogger =
        NativeLogger(name, config)

}
