package mu.internal

import mu.KLogger
import mu.KotlinLoggingConfiguration.appender
import mu.KotlinLoggingConfiguration.formatter
import mu.KotlinLoggingLevel
import mu.KotlinLoggingLevel.*
import mu.Marker
import mu.isLoggingEnabled

internal class KLoggerLinux(
    private val loggerName: String
) : KLogger {

    override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(msg, appender::trace)

    override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(msg, appender::debug)

    override fun info(msg: () -> Any?) = INFO.logIfEnabled(msg, appender::info)

    override fun warn(msg: () -> Any?) = WARN.logIfEnabled(msg, appender::warn)

    override fun error(msg: () -> Any?) = ERROR.logIfEnabled(msg, appender::error)

    override fun trace(t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(msg, t, appender::trace)

    override fun debug(t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(msg, t, appender::debug)

    override fun info(t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(msg, t, appender::info)

    override fun warn(t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(msg, t, appender::warn)

    override fun error(t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(msg, t, appender::error)

    override fun trace(marker: Marker?, msg: () -> Any?) = TRACE.logIfEnabled(marker, msg, appender::trace)

    override fun debug(marker: Marker?, msg: () -> Any?) = DEBUG.logIfEnabled(marker, msg, appender::debug)

    override fun info(marker: Marker?, msg: () -> Any?) = INFO.logIfEnabled(marker, msg, appender::info)

    override fun warn(marker: Marker?, msg: () -> Any?) = WARN.logIfEnabled(marker, msg, appender::warn)

    override fun error(marker: Marker?, msg: () -> Any?) = ERROR.logIfEnabled(marker, msg, appender::error)

    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        TRACE.logIfEnabled(marker, msg, t, appender::trace)

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        DEBUG.logIfEnabled(marker, msg, t, appender::debug)

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        INFO.logIfEnabled(marker, msg, t, appender::info)

    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        WARN.logIfEnabled(marker, msg, t, appender::warn)

    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        ERROR.logIfEnabled(marker, msg, t, appender::error)

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, logFunction: (String, String) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(loggerName, formatter.formatMessage(appender.includePrefix, this, loggerName, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, t: Throwable?, logFunction: (String, String) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(loggerName, formatter.formatMessage(appender.includePrefix, this, loggerName, t, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(marker: Marker?, msg: () -> Any?, logFunction: (String, String) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(loggerName, formatter.formatMessage(appender.includePrefix, this, loggerName, marker, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(
        marker: Marker?,
        msg: () -> Any?,
        t: Throwable?,
        logFunction: (String, String) -> Unit
    ) {
        if (isLoggingEnabled()) {
            logFunction(loggerName, formatter.formatMessage(appender.includePrefix, this, loggerName, marker, t, msg))
        }
    }

    override fun entry(vararg argArray: Any?) {
        TRACE.logIfEnabled({ "entry($argArray)" }, appender::trace)
    }

    override fun exit() {
        TRACE.logIfEnabled({ "exit()" }, appender::trace)
    }

    override fun <T : Any?> exit(result: T): T {
        TRACE.logIfEnabled({ "exit($result)" }, appender::trace)
        return result
    }

    override fun <T : Throwable> throwing(throwable: T): T {
        ERROR.logIfEnabled({ "throwing($throwable" }, throwable, appender::error)
        return throwable
    }

    override fun <T : Throwable> catching(throwable: T) {
        ERROR.logIfEnabled({ "catching($throwable" }, throwable, appender::error)
    }
}
