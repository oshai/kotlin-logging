package mu

public interface Formatter {
    public fun formatMessage(level: KotlinLoggingLevel, loggerName: String, msg: () -> Any?): Any?
    public fun formatMessage(level: KotlinLoggingLevel, loggerName: String, t: Throwable?, msg: () -> Any?): Any?
    public fun formatMessage(level: KotlinLoggingLevel, loggerName: String, marker: Marker?, msg: () -> Any?): Any?
    public fun formatMessage(
        level: KotlinLoggingLevel,
        loggerName: String,
        marker: Marker?,
        t: Throwable?,
        msg: () -> Any?
    ): Any?
}
