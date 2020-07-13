package mu

/**
 * Factory for creating KLoggers
 */
expect interface KLoggerFactory {
    fun logger(name: String): KLogger
    fun logger(func: () -> Unit): KLogger
}
