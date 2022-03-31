package mu.internal

import mu.KDelegatingLogger
import mu.LoggingCallback
import org.slf4j.Logger

internal class JvmDelegatingLogger(private val underlyingLogger: Logger) : KDelegatingLogger {

    private var traceLoggingCallback: JvmLoggingCallback? = null
    private var debugLoggingCallback: JvmLoggingCallback? = null
    private var intoLoggingCallback: JvmLoggingCallback? = null
    private var warnLoggingCallback: JvmLoggingCallback? = null
    private var errorLoggingCallback: JvmLoggingCallback? = null

    override val trace: LoggingCallback?
        get() = when (underlyingLogger.isTraceEnabled) {
            true -> {
                traceLoggingCallback?.let {
                    val newCallback = JvmLoggingCallback(underlyingLogger::trace)

                    traceLoggingCallback = newCallback

                    newCallback
                }
            }
            false -> null
        }

    override val debug: LoggingCallback?
        get() = when (underlyingLogger.isDebugEnabled) {
            true -> {
                debugLoggingCallback?.let {
                    val newCallback = JvmLoggingCallback(underlyingLogger::debug)

                    debugLoggingCallback = newCallback

                    newCallback
                }
            }
            false -> null
        }

    override val info: LoggingCallback?
        get() = when (underlyingLogger.isInfoEnabled) {
            true -> {
                intoLoggingCallback?.let {
                    val newCallback = JvmLoggingCallback(underlyingLogger::info)

                    intoLoggingCallback = newCallback

                    newCallback
                }
            }
            false -> null
        }

    override val warn: LoggingCallback?
        get() = when (underlyingLogger.isWarnEnabled) {
            true -> {
                warnLoggingCallback?.let {
                    val newCallback = JvmLoggingCallback(underlyingLogger::warn)

                    warnLoggingCallback = newCallback

                    newCallback
                }
            }
            false -> null
        }

    override val error: LoggingCallback?
        get() = when (underlyingLogger.isErrorEnabled) {
            true -> {
                errorLoggingCallback?.let {
                    val newCallback = JvmLoggingCallback(underlyingLogger::error)

                    errorLoggingCallback = newCallback

                    newCallback
                }
            }
            false -> null
        }
}
