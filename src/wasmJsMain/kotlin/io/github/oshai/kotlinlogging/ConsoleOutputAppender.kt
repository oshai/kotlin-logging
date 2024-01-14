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

private fun consoleLog(
  @Suppress("UNUSED_PARAMETER") msg: String,
): Unit = js("console.log(msg)")

private fun consoleInfo(
  @Suppress("UNUSED_PARAMETER") msg: String,
): Unit = js("console.info(msg)")

private fun consoleWarn(
  @Suppress("UNUSED_PARAMETER") msg: String,
): Unit = js("console.warn(msg)")

private fun consoleError(
  @Suppress("UNUSED_PARAMETER") msg: String,
): Unit = js("console.error(msg)")
