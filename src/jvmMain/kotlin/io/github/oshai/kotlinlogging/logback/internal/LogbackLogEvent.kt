package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.LoggingEvent
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.logback.toLogbackLevel

public class LogbackLogEvent(
  fqcn: String,
  logger: Logger,
  level: Level,
  private val kLoggingEvent: KLoggingEventBuilder,
) :
  LoggingEvent(
    fqcn,
    logger,
    level.toLogbackLevel(),
    kLoggingEvent.internalCompilerData?.messageTemplate ?: kLoggingEvent.message,
    kLoggingEvent.cause,
    kLoggingEvent.arguments ?: emptyArray(),
  ) {

  override fun getFormattedMessage(): String? {
    return kLoggingEvent.message
  }

  override fun getCallerData(): Array<StackTraceElement> =
    if (kLoggingEvent.internalCompilerData?.fileName != null) {
      arrayOf(
        StackTraceElement(
          kLoggingEvent.internalCompilerData?.className,
          kLoggingEvent.internalCompilerData?.methodName,
          kLoggingEvent.internalCompilerData?.fileName,
          kLoggingEvent.internalCompilerData?.lineNumber ?: 0,
        )
      )
    } else {
      super.getCallerData()
    }

  override fun hasCallerData(): Boolean =
    if (kLoggingEvent.internalCompilerData?.fileName != null) {
      true
    } else {
      super.hasCallerData()
    }
}
