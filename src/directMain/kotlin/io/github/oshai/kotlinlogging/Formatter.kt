package io.github.oshai.kotlinlogging

public interface Formatter {

  public fun formatMessage(loggingEvent: KLoggingEvent): String
}

public class DefaultMessageFormatter(private val includePrefix: Boolean = true) : Formatter {

  override fun formatMessage(loggingEvent: KLoggingEvent): String {
    with(loggingEvent) {
      return buildString {
        append(prefix(level, loggerName))
        marker?.getName()?.let {
          append(it)
          append(" ")
        }
        append(message)
        append(cause.throwableToString())
      }
    }
  }

  private fun prefix(level: Level, loggerName: String): String {
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
