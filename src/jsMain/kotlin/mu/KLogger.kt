package mu

public actual interface KLogger {

    /**
     * Lazy add a log message if isTraceEnabled is true
     */
    public actual fun trace(msg: () -> Any?)

    /**
     * Lazy add a log message if isDebugEnabled is true
     */
    public actual fun debug(msg: () -> Any?)

    /**
     * Lazy add a log message if isInfoEnabled is true
     */
    public actual fun info(msg: () -> Any?)

    /**
     * Lazy add a log message if isWarnEnabled is true
     */
    public actual fun warn(msg: () -> Any?)

    /**
     * Lazy add a log message if isErrorEnabled is true
     */
    public actual fun error(msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isTraceEnabled is true
     */
    public actual fun trace(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isDebugEnabled is true
     */
    public actual fun debug(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isInfoEnabled is true
     */
    public actual fun info(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isWarnEnabled is true
     */
    public actual fun warn(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isErrorEnabled is true
     */
    public actual fun error(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message if isTraceEnabled is true
     */
    public actual fun trace(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message if isDebugEnabled is true
     */
    public actual fun debug(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message if isInfoEnabled is true
     */
    public actual fun info(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message if isWarnEnabled is true
     */
    public actual fun warn(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message if isErrorEnabled is true
     */
    public actual fun error(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isTraceEnabled is true
     */
    public actual fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isDebugEnabled is true
     */
    public actual fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isInfoEnabled is true
     */
    public actual fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isWarnEnabled is true
     */
    public actual fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isErrorEnabled is true
     */
    public actual fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Add a log message with all the supplied parameters along with method name
     */
    public actual fun entry(vararg argArray: Any?)

    /**
     * Add log message indicating exit of a method
     */
    public actual fun exit()

    /**
     * Add a log message with the return value of a method
     */
    public actual fun <T> exit(result: T): T where T : Any?

    /**
     * Add a log message indicating an exception will be thrown along with the stack trace.
     */
    public actual fun <T> throwing(throwable: T): T where T : Throwable

    /**
     * Add a log message indicating an exception is caught along with the stack trace.
     */
    public actual fun <T> catching(throwable: T) where T : Throwable
}
