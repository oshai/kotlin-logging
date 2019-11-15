package mu


expect object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    fun logger(func: () -> Unit): KLogger

    fun logger(name: String): KLogger
}
