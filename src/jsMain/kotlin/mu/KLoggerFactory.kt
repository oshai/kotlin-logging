package mu

actual interface KLoggerFactory {
    actual fun logger(name: String): KLogger
    actual fun logger(func: () -> Unit): KLogger
}
