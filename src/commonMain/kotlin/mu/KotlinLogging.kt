package mu


public expect object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * ```
     * val logger = KotlinLogging.logger {}
     * ```
     */
    public fun logger(func: () -> Unit): KLogger

    public fun logger(name: String): KLogger
}
