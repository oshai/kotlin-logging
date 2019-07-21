package mu

import mu.internal.toStringSafe

object DefaultMessageFormatter : Formatter {
    override fun formatMessage(level: KotlinLoggingLevel, loggerName: String, msg: () -> Any?) =
            "${level.name}: [$loggerName] ${msg.toStringSafe()}"

    override fun formatMessage(level: KotlinLoggingLevel, loggerName: String, t: Throwable?, msg: () -> Any?) =
            "${level.name}: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}"

    override fun formatMessage(level: KotlinLoggingLevel, loggerName: String, marker: Marker?, msg: () -> Any?) =
            "${level.name}: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}"

    override fun formatMessage(level: KotlinLoggingLevel, loggerName: String, marker: Marker?, t: Throwable?, msg: () -> Any?) =
            "${level.name}: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}"

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
