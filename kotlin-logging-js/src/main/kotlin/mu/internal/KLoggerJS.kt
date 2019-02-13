package mu.internal

import mu.*
import mu.KotlinLoggingLevel.*

internal class KLoggerJS(
        private val loggerName: String,
        private val pipes: OutputPipes = outputPipes,
        private val formatter: MessageFormatter = messageFormatter
) : KLogger, MessageFormatter by formatter {

    override fun trace(msg: () -> Any?) = TRACE.logIfEnabled(msg, pipes::trace)

    override fun debug(msg: () -> Any?) = DEBUG.logIfEnabled(msg, pipes::debug)

    override fun info(msg: () -> Any?) = INFO.logIfEnabled(msg, pipes::info)

    override fun warn(msg: () -> Any?) = WARN.logIfEnabled(msg, pipes::warn)

    override fun error(msg: () -> Any?) = ERROR.logIfEnabled(msg, pipes::error)

    override fun trace(t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(msg, t, pipes::trace)

    override fun debug(t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(msg, t, pipes::debug)

    override fun info(t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(msg, t, pipes::info)

    override fun warn(t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(msg, t, pipes::warn)

    override fun error(t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(msg, t, pipes::error)

    override fun trace(marker: Marker?, msg: () -> Any?) = TRACE.logIfEnabled(marker, msg, pipes::trace)

    override fun debug(marker: Marker?, msg: () -> Any?) = DEBUG.logIfEnabled(marker, msg, pipes::debug)

    override fun info(marker: Marker?, msg: () -> Any?) = INFO.logIfEnabled(marker, msg, pipes::info)

    override fun warn(marker: Marker?, msg: () -> Any?) = WARN.logIfEnabled(marker, msg, pipes::warn)

    override fun error(marker: Marker?, msg: () -> Any?) = ERROR.logIfEnabled(marker, msg, pipes::error)

    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) = TRACE.logIfEnabled(marker, msg, t, pipes::trace)

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) = DEBUG.logIfEnabled(marker, msg, t, pipes::debug)

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) = INFO.logIfEnabled(marker, msg, t, pipes::info)

    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) = WARN.logIfEnabled(marker, msg, t, pipes::warn)

    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) = ERROR.logIfEnabled(marker, msg, t, pipes::error)

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(formatMessage(this, msg, loggerName))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(msg: () -> Any?, t: Throwable?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(formatMessage(this, msg, t, loggerName))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(marker: Marker?, msg: () -> Any?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(formatMessage(this, marker, msg, loggerName))
        }
    }

    private fun KotlinLoggingLevel.logIfEnabled(marker: Marker?, msg: () -> Any?, t: Throwable?, logFunction: (Any?) -> Unit) {
        if (isLoggingEnabled()) {
            logFunction(formatMessage(this, marker, msg, t, loggerName))
        }
    }

}
