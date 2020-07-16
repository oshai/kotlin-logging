package mu

/**
 * Factory for creating KLoggers
 */
actual interface KLoggerFactory {
    actual fun logger(name: String): KLogger
    actual fun logger(func: () -> Unit): KLogger
}
