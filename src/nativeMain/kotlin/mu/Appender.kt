package mu

public interface Appender {
    public val includePrefix: Boolean

    public fun trace(loggerName: String, message: String)
    public fun debug(loggerName: String, message: String)
    public fun info(loggerName: String, message: String)
    public fun warn(loggerName: String, message: String)
    public fun error(loggerName: String, message: String)
}
