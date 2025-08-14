package io.github.oshai.kotlinlogging

// Simple console appender for WASI target relying on stdout
public object ConsoleOutputAppender : FormattingAppender() {
  override fun logFormattedMessage(loggingEvent: KLoggingEvent, formattedMessage: Any?) {
    when (loggingEvent.level) {
      Level.TRACE,
      Level.DEBUG,
      Level.INFO,
      Level.WARN,
      Level.ERROR -> println(formattedMessage)
      Level.OFF -> Unit
    }
  }
}
