package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.toStringSafe

public object DefaultMessageFormatter : Formatter {

  public override fun formatMessage(
    level: Level,
    loggerName: String,
    marker: Marker?,
    throwable: Throwable?,
    message: () -> Any?
  ): String =
    "${level.name}: [$loggerName] ${marker?.getName()} ${message.toStringSafe()}${throwable.throwableToString()}"

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
