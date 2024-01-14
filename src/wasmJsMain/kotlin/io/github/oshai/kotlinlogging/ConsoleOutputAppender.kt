package io.github.oshai.kotlinlogging

public class ConsoleOutputAppender : FormattingAppender() {
  override fun logFormattedMessage(loggingEvent: KLoggingEvent, formattedMessage: Any?) {
    when (loggingEvent.level) {
      Level.TRACE -> consoleLog(formattedMessage.toString())
      Level.DEBUG -> consoleLog(formattedMessage.toString())
      Level.INFO -> consoleInfo(formattedMessage.toString())
      Level.WARN -> consoleWarn(formattedMessage.toString())
      Level.ERROR -> consoleError(formattedMessage.toString())
      Level.OFF -> Unit
    }
  }
}

private fun consoleLog(message: String): Unit = js("console.log(message)")

private fun consoleInfo(message: String): Unit = js("console.info(message)")

private fun consoleWarn(message: String): Unit = js("console.warn(message)")

private fun consoleError(message: String): Unit = js("console.error(message)")
