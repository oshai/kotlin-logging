package io.github.oshai.kotlinlogging

public class ConsoleOutputAppender : FormattingAppender() {
  override fun logFormattedMessage(loggingEvent: KLoggingEvent, formattedMessage: Any?) {
    when (loggingEvent.level) {
      Level.TRACE -> console.log(formattedMessage)
      Level.DEBUG -> console.log(formattedMessage)
      Level.INFO -> console.info(formattedMessage)
      Level.WARN -> console.warn(formattedMessage)
      Level.ERROR -> console.error(formattedMessage)
      Level.OFF -> Unit
    }
  }
}
