package mu.internal

import mu.KLogger
import mu.KotlinLoggingConfiguration.APPENDER
import mu.KotlinLoggingConfiguration.FORMATTER
import mu.KotlinLoggingLevel
import mu.KotlinLoggingLevel.DEBUG
import mu.KotlinLoggingLevel.ERROR
import mu.KotlinLoggingLevel.INFO
import mu.KotlinLoggingLevel.TRACE
import mu.KotlinLoggingLevel.WARN
import mu.Marker
import mu.isLoggingEnabled

internal class KLoggerJS(
    private val loggerName: String
) : KLogger {

    override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(msg, APPENDER::trace)

    override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(msg, APPENDER::debug)

    override fun info(msg: () -> Any?) = INFO.logIfEnabled(msg, APPENDER::info)

    override fun warn(msg: () -> Any?) = WARN.logIfEnabled(msg, APPENDER::warn)

    override fun error(msg: () -> Any?) = ERROR.logIfEnabled(msg, APPENDER::error)

    override fun trace(t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(msg, t, APPENDER::trace)

    override fun debug(t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(msg, t, APPENDER::debug)

    override fun info(t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(msg, t, APPENDER::info)

    override fun warn(t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(msg, t, APPENDER::warn)

    override fun error(t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(msg, t, APPENDER::error)

    override fun trace(marker: Marker?, msg: () -> Any?) = TRACE.logIfEnabled(marker, msg, APPENDER::trace)

    override fun debug(marker: Marker?, msg: () -> Any?) = DEBUG.logIfEnabled(marker, msg, APPENDER::debug)

    override fun info(marker: Marker?, msg: () -> Any?) = INFO.logIfEnabled(marker, msg, APPENDER::info)

    override fun warn(marker: Marker?, msg: () -> Any?) = WARN.logIfEnabled(marker, msg, APPENDER::warn)

    override fun error(marker: Marker?, msg: () -> Any?) = ERROR.logIfEnabled(marker, msg, APPENDER::error)

    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(marker, msg, t, APPENDER::trace)

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(marker, msg, t, APPENDER::debug)

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(marker, msg, t, APPENDER::info)

    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(marker, msg, t, APPENDER::warn)

    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(marker, msg, t, APPENDER::error)

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(FORMATTER.formatMessage(this, loggerName, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, t: Throwable?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(FORMATTER.formatMessage(this, loggerName, t, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(marker: Marker?, msg: () -> Any?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(FORMATTER.formatMessage(this, loggerName, marker, msg))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(
        marker: Marker?, msg: () -> Any?, t: Throwable?, logFunction: (Any?) -> Unit
    ) {
        if (isLoggingEnabled()) {
            logFunction(FORMATTER.formatMessage(this, loggerName, marker, t, msg))
        }
    }

    override fun entry(vararg argArray: Any) {
        TRACE.logIfEnabled({ "entry($argArray)" }, APPENDER::trace)
    }

    override fun exit() {
        TRACE.logIfEnabled({ "exit()" }, APPENDER::trace)
    }

    override fun <T : Any?> exit(retval: T): T {
        TRACE.logIfEnabled({ "exit($retval)" }, APPENDER::trace)
        return retval
    }

    override fun <T : Throwable> throwing(throwable: T): T {
        ERROR.logIfEnabled({ "throwing($throwable" }, throwable, APPENDER::error)
        return throwable
    }

    override fun <T : Throwable> catching(throwable: T) {
        ERROR.logIfEnabled({ "catching($throwable" }, throwable, APPENDER::error)
    }
}
