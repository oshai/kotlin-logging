package mu

import mu.internal.toStringSafe

object DefaultMessageFormatter : MessageFormatter {
    override fun formatMessage(level: KotlinLoggingLevel, msg: () -> Any?, loggerName: String) =
            "${level.name}: [$loggerName] ${msg.toStringSafe()}"

    override fun formatMessage(level: KotlinLoggingLevel, msg: () -> Any?, t: Throwable?, loggerName: String) =
            "${level.name}: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}"

    override fun formatMessage(level: KotlinLoggingLevel, marker: Marker?, msg: () -> Any?, loggerName: String) =
            "${level.name}: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}"

    override fun formatMessage(level: KotlinLoggingLevel, marker: Marker?, msg: () -> Any?, t: Throwable?, loggerName: String) =
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