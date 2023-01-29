package mu.two

import mu.two.internal.toStringSafe

public object DefaultMessageFormatter : Formatter {
  public override fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      msg: () -> Any?
  ): String = "${prefix(includePrefix, level, loggerName)}${msg.toStringSafe()}"

  public override fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      t: Throwable?,
      msg: () -> Any?
  ): String =
      "${prefix(includePrefix, level, loggerName)}${msg.toStringSafe()}${t.throwableToString()}"

  public override fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      marker: mu.two.Marker?,
      msg: () -> Any?
  ): String =
      "${prefix(includePrefix, level, loggerName)}${marker?.getName()} ${msg.toStringSafe()}"

  public override fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      marker: mu.two.Marker?,
      t: Throwable?,
      msg: () -> Any?
  ): String =
      "${prefix(includePrefix, level, loggerName)}${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}"

  private fun prefix(includePrefix: Boolean, level: mu.two.Level, loggerName: String): String {
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
