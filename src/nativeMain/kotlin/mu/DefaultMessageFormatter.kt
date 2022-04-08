package mu

import mu.internal.toStringSafe

public object DefaultMessageFormatter : Formatter {
    public override fun formatMessage(includePrefix: Boolean, level: KotlinLoggingLevel, loggerName: String, msg: () -> Any?): String =
        "${prefix(includePrefix, level, loggerName)}${msg.toStringSafe()}"

    public override fun formatMessage(includePrefix: Boolean, level: KotlinLoggingLevel, loggerName: String, t: Throwable?, msg: () -> Any?): String =
        "${prefix(includePrefix, level, loggerName)}${msg.toStringSafe()}${t.throwableToString()}"

    public override fun formatMessage(includePrefix: Boolean, level: KotlinLoggingLevel, loggerName: String, marker: Marker?, msg: () -> Any?): String =
        "${prefix(includePrefix, level, loggerName)}${marker?.getName()} ${msg.toStringSafe()}"

    public override fun formatMessage(
        includePrefix: Boolean,
        level: KotlinLoggingLevel,
        loggerName: String,
        marker: Marker?,
        t: Throwable?,
        msg: () -> Any?
    ): String =
        "${prefix(includePrefix, level, loggerName)}${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}"

    private fun prefix(includePrefix: Boolean, level: KotlinLoggingLevel, loggerName: String): String {
        return if (includePrefix) {
            "${level.name}: [$loggerName] "
        } else {
            ""
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
