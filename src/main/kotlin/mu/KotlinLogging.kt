package mu

import mu.internal.KLoggerFactory


object KotlinLogging {
    /**
     * This methods allow defining the logger in a file in the following way:
     * val logger = KLogging.logger {}
     */
    fun logger(func: () -> Unit): KLogger {
        val name = func.javaClass.name
        val slicedName = when {
            name.contains("Kt$") -> name.substringBefore("Kt$")
            name.contains("$") -> name.substringBefore("$")
            else -> name
        }
        return logger(slicedName)
    }

    fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}
