package mu.internal

import mu.KLogger
import mu.KotlinLoggingLevel
import mu.Marker
import mu.isLoggingEnabled

internal class KLoggerJS(private val loggerName: String) : KLogger {

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

    override fun trace(marker: Marker?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("TRACE: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}")
        }
    }

    override fun debug(marker: Marker?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("DEBUG: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}")
        }
    }

    override fun info(marker: Marker?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.info("INFO: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}")
        }
    }

    override fun warn(marker: Marker?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.warn("WARN: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}")
        }
    }

    override fun error(marker: Marker?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.error("ERROR: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}")
        }
    }

    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("TRACE: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("DEBUG: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.info("INFO: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.warn("WARN: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}")
        }
    }

    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.error("ERROR: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}")
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
