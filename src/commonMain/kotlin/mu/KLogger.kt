package mu

public expect interface KLogger {

    /**
     * Lazy add a log message if isTraceEnabled is true
     */
    public fun trace(msg: () -> Any?)

    /**
     * Lazy add a log message if isDebugEnabled is true
     */
    public fun debug(msg: () -> Any?)

    /**
     * Lazy add a log message if isInfoEnabled is true
     */
    public fun info(msg: () -> Any?)

    /**
     * Lazy add a log message if isWarnEnabled is true
     */
    public fun warn(msg: () -> Any?)

    /**
     * Lazy add a log message if isErrorEnabled is true
     */
    public fun error(msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isTraceEnabled is true
     */
    public fun trace(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isDebugEnabled is true
     */
    public fun debug(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isInfoEnabled is true
     */
    public fun info(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isWarnEnabled is true
     */
    public fun warn(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with throwable payload if isErrorEnabled is true
     */
    public fun error(t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker if isTraceEnabled is true
     */
    public fun trace(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker if isDebugEnabled is true
     */
    public fun debug(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker if isInfoEnabled is true
     */
    public fun info(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker if isWarnEnabled is true
     */
    public fun warn(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker if isErrorEnabled is true
     */
    public fun error(marker: Marker?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker and throwable payload if isTraceEnabled is true
     */
    public fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker and throwable payload if isDebugEnabled is true
     */
    public fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker and throwable payload if isInfoEnabled is true
     */
    public fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker and throwable payload if isWarnEnabled is true
     */
    public fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Lazy add a log message with a marker and throwable payload if isErrorEnabled is true
     */
    public fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

    /**
     * Add a log message with all the supplied parameters along with method name
     */
    public fun entry(vararg argArray: Any?)

    /**
     * Add log message indicating exit of a method
     */
    public fun exit()

    /**
     * Add a log message with the return value of a method
     */
    public fun <T> exit(result: T): T where T : Any?

    /**
     * Add a log message indicating an exception will be thrown along with the stack trace.
     */
    public fun <T> throwing(throwable: T): T where T : Throwable

    /**
     * Add a log message indicating an exception is caught along with the stack trace.
     */
    public fun <T> catching(throwable: T) where T : Throwable

}
