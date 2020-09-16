package mu

public interface Appender {
    public fun trace(message: Any?)
    public fun debug(message: Any?)
    public fun info(message: Any?)
    public fun warn(message: Any?)
    public fun error(message: Any?)
}
