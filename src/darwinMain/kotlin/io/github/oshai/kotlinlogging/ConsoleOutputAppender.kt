@file:OptIn(ExperimentalForeignApi::class)

package io.github.oshai.kotlinlogging

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.fprintf
import platform.posix.stderr

public object ConsoleOutputAppender : FormattingAppender() {
  override fun logFormattedMessage(loggingEvent: KLoggingEvent, formattedMessage: Any?) {
    if (loggingEvent.level == Level.ERROR) {
      fprintf(stderr, "%s\n", formattedMessage.toString())
    } else {
      println(formattedMessage)
    }
  }
}
