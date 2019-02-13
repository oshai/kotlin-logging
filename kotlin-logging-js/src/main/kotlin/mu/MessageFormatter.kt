package mu

interface MessageFormatter {
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, msg: () -> Any?): String
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, t: Throwable?, msg: () -> Any?): String
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, marker: Marker?, msg: () -> Any?): String
    fun formatMessage(level: KotlinLoggingLevel, loggerName: String, marker: Marker?, t: Throwable?, msg: () -> Any?): String
}