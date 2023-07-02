package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.toStringSafe

public object DefaultMessageFormatter : Formatter {

  public override fun formatMessage(
    includePrefix: Boolean,
    level: Level,
    loggerName: String,
    marker: Marker?,
    t: Throwable?,
    msg: () -> Any?
  ): String =
    "${prefix(includePrefix, level, loggerName)}${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}"

  private fun prefix(includePrefix: Boolean, level: Level, loggerName: String): String {
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
