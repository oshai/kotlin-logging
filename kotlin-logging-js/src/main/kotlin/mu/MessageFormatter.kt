package mu

interface MessageFormatter {
    fun formatMessage(level: KotlinLoggingLevel, msg: () -> Any?, loggerName: String): String
    fun formatMessage(level: KotlinLoggingLevel, msg: () -> Any?, t: Throwable?, loggerName: String): String
    fun formatMessage(level: KotlinLoggingLevel, marker: Marker?, msg: () -> Any?, loggerName: String): String
    fun formatMessage(level: KotlinLoggingLevel, marker: Marker?, msg: () -> Any?, t: Throwable?, loggerName: String): String
}