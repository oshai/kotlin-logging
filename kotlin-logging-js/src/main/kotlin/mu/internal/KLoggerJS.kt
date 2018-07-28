package mu.internal

import mu.KLogger
import mu.KotlinLoggingLevel
import mu.isLoggingEnabled

class KLoggerJS(private val loggerName: String) : KLogger {

    override fun trace(msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("TRACE: [$loggerName] ${msg.toStringSafe()}")
        }
    }

    override fun debug(msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("DEBUG: [$loggerName] ${msg.toStringSafe()}")
        }
    }

    override fun info(msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.info("INFO: [$loggerName] ${msg.toStringSafe()}")
        }
    }

    override fun warn(msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.warn("WARN: [$loggerName] ${msg.toStringSafe()}")
        }
    }

    override fun error(msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.error("ERROR: [$loggerName] ${msg.toStringSafe()}")
        }
    }

    override fun trace(t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("TRACE: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun debug(t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("DEBUG: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun info(t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.info("INFO: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun warn(t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.warn("WARN: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun error(t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.error("ERROR: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    private fun (() -> Any?).toStringSafe(): String {
        try {
            return invoke().toString()
        } catch (e: Exception) {
            return "Log message invocation failed: $e"
        }
    }

    private fun Throwable?.throwableToString(): String {
        if (this == null) {
            return ""
        }
        var msg = ""
        var current = this
        while (current != null && current.cause != current) {
            msg += ", Caused by: '${current.message}'"
            current = current.cause
        }
        return msg
    }
}
