package mu

import mu.LoggingCallback

public interface KDelegatingLogger {

    /**
     * Returns callback, which can be used for logging with trace level.
     * Please note: multiple calls can have different results (for example, if configuration is updated dynamically).
     *
     * @see LoggingCallback
     */
    public val trace: LoggingCallback?

    /**
     * Returns callback, which can be used for logging with debug level.
     * Please note: multiple calls can have different results (for example, if configuration is updated dynamically).
     *
     * @see LoggingCallback
     */
    public val debug: LoggingCallback?

    /**
     * Returns callback, which can be used for logging with info level.
     * Please note: multiple calls can have different results (for example, if configuration is updated dynamically).
     *
     * @see LoggingCallback
     */
    public val info: LoggingCallback?

    /**
     * Returns callback, which can be used for logging with warn level.
     * Please note: multiple calls can have different results (for example, if configuration is updated dynamically).
     *
     * @see LoggingCallback
     */
    public val warn: LoggingCallback?

    /**
     * Returns callback, which can be used for logging with error level.
     * Please note: multiple calls can have different results (for example, if configuration is updated dynamically).
     *
     * @see LoggingCallback
     */
    public val error: LoggingCallback?
}
