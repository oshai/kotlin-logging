package mu.internal

import mu.KLogger
import mu.KotlinLoggingConfiguration.appender
import mu.KotlinLoggingConfiguration.formatter
import mu.KotlinLoggingLevel
import mu.KotlinLoggingLevel.DEBUG
import mu.KotlinLoggingLevel.ERROR
import mu.KotlinLoggingLevel.INFO
import mu.KotlinLoggingLevel.TRACE
import mu.KotlinLoggingLevel.WARN
import mu.Marker
import mu.isLoggingEnabled

internal class KLoggerLinux(
    private val loggerName: String
) : KLogger {

    override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(msg, appender.value::trace)

    override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(msg, appender.value::debug)

    override fun info(msg: () -> Any?) = INFO.logIfEnabled(msg, appender.value::info)

    override fun warn(msg: () -> Any?) = WARN.logIfEnabled(msg, appender.value::warn)

    override fun error(msg: () -> Any?) = ERROR.logIfEnabled(msg, appender.value::error)

    override fun trace(t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(msg, t, appender.value::trace)

    override fun debug(t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(msg, t, appender.value::debug)

    override fun info(t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(msg, t, appender.value::info)

    override fun warn(t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(msg, t, appender.value::warn)

    override fun error(t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(msg, t, appender.value::error)

    override fun trace(marker: Marker?, msg: () -> Any?) = TRACE.logIfEnabled(marker, msg, appender.value::trace)

    override fun debug(marker: Marker?, msg: () -> Any?) = DEBUG.logIfEnabled(marker, msg, appender.value::debug)

    override fun info(marker: Marker?, msg: () -> Any?) = INFO.logIfEnabled(marker, msg, appender.value::info)

    override fun warn(marker: Marker?, msg: () -> Any?) = WARN.logIfEnabled(marker, msg, appender.value::warn)

    override fun error(marker: Marker?, msg: () -> Any?) = ERROR.logIfEnabled(marker, msg, appender.value::error)

    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        TRACE.logIfEnabled(marker, msg, t, appender.value::trace)

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        DEBUG.logIfEnabled(marker, msg, t, appender.value::debug)

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        INFO.logIfEnabled(marker, msg, t, appender.value::info)

    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        WARN.logIfEnabled(marker, msg, t, appender.value::warn)

    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        ERROR.logIfEnabled(marker, msg, t, appender.value::error)

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(formatter.value.formatMessage(this, loggerName, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, t: Throwable?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(formatter.value.formatMessage(this, loggerName, t, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(marker: Marker?, msg: () -> Any?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(formatter.value.formatMessage(this, loggerName, marker, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(
        marker: Marker?,
        msg: () -> Any?,
        t: Throwable?,
        logFunction: (Any?) -> Unit
    ) {
        if (isLoggingEnabled()) {
            logFunction(formatter.value.formatMessage(this, loggerName, marker, t, msg))
        }
    }

    override fun entry(vararg argArray: Any?) {
        TRACE.logIfEnabled({ "entry($argArray)" }, appender.value::trace)
    }

    override fun exit() {
        TRACE.logIfEnabled({ "exit()" }, appender.value::trace)
    }

    override fun <T : Any?> exit(result: T): T {
        TRACE.logIfEnabled({ "exit($result)" }, appender.value::trace)
        return result
    }

    override fun <T : Throwable> throwing(throwable: T): T {
        ERROR.logIfEnabled({ "throwing($throwable" }, throwable, appender.value::error)
        return throwable
    }

    override fun <T : Throwable> catching(throwable: T) {
        ERROR.logIfEnabled({ "catching($throwable" }, throwable, appender.value::error)
    }
}
