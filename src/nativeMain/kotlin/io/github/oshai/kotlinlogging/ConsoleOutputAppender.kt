package io.github.oshai.kotlinlogging

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.fprintf
import platform.posix.stderr

public object ConsoleOutputAppender : FormattingAppender() {
  @OptIn(ExperimentalForeignApi::class)
  override fun logFormattedMessage(loggingEvent: KLoggingEvent, formattedMessage: Any?) {
    if (loggingEvent.level == Level.ERROR) {
      fprintf(stderr, "$formattedMessage\n")
    } else {
      println(formattedMessage)
    }
  }
}
