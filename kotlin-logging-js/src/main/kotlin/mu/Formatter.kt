package mu

interface Formatter {
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, msg: () -> Any?): Any?
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, t: Throwable?, msg: () -> Any?): Any?
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, marker: Marker?, msg: () -> Any?): Any?
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, marker: Marker?, t: Throwable?, msg: () -> Any?): Any?
}