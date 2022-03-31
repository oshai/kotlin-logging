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
                when (traceLoggingCallback) {
                    null -> {
                        val newCallback = JvmLoggingCallback(underlyingLogger::trace)

                        traceLoggingCallback = newCallback

                        newCallback
                    }
                    else -> traceLoggingCallback
                }
            }
            false -> null
        }

    override val debug: LoggingCallback?
        get() = when (underlyingLogger.isDebugEnabled) {
            true -> {
                when (debugLoggingCallback) {
                    null -> {
                        val newCallback = JvmLoggingCallback(underlyingLogger::debug)

                        debugLoggingCallback = newCallback

                        newCallback
                    }
                    else -> debugLoggingCallback
                }
            }
            false -> null
        }

    override val info: LoggingCallback?
        get() = when (underlyingLogger.isInfoEnabled) {
            true -> {
                when (intoLoggingCallback) {
                    null -> {
                        val newCallback = JvmLoggingCallback(underlyingLogger::info)

                        intoLoggingCallback = newCallback

                        newCallback
                    }
                    else -> intoLoggingCallback
                }
            }
            false -> null
        }

    override val warn: LoggingCallback?
        get() = when (underlyingLogger.isWarnEnabled) {
            true -> {
                when (warnLoggingCallback) {
                    null -> {
                        val newCallback = JvmLoggingCallback(underlyingLogger::warn)

                        warnLoggingCallback = newCallback

                        newCallback
                    }
                    else -> warnLoggingCallback
                }
            }
            false -> null
        }

    override val error: LoggingCallback?
        get() = when (underlyingLogger.isErrorEnabled) {
            true -> {
                when (errorLoggingCallback) {
                    null -> {
                        val newCallback = JvmLoggingCallback(underlyingLogger::error)

                        errorLoggingCallback = newCallback

                        newCallback
                    }
                    else -> errorLoggingCallback
                }
            }
            false -> null
        }
}
