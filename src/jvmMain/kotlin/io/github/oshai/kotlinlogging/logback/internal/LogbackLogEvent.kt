package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.LoggingEvent

public class LogbackLogEvent(
  fqcn: String,
  logger: Logger,
  level: Level,
  message: String?,
  private val finalFormattedMessage: String?,
  throwable: Throwable?,
  argArray: Array<Any>
) : LoggingEvent(fqcn, logger, level, message, throwable, argArray) {

  override fun getFormattedMessage(): String? {
    return finalFormattedMessage
  }

}
