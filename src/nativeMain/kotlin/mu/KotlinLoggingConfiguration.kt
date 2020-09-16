package mu

import kotlin.native.concurrent.AtomicReference

@Suppress("ObjectPropertyName")
public object KotlinLoggingConfiguration {
    private val _logLevel = AtomicReference(KotlinLoggingLevel.INFO)
    public var logLevel: KotlinLoggingLevel
        get() = _logLevel.value
        set(value) {
            _logLevel.value = value
        }
    private val _appender = AtomicReference<Appender>(ConsoleOutputAppender)
    public var appender: Appender
        get() = _appender.value
        set(value) {
            _appender.value = value
        }
    private val _formatter = AtomicReference<Formatter>(DefaultMessageFormatter)
    public var formatter: Formatter
        get() = _formatter.value
        set(value) {
            _formatter.value = value
        }
}
