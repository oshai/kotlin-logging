package io.github.oshai.kotlinlogging

public interface Appender {
  public fun log(loggingEvent: KLoggingEvent)
}

public abstract class FormattingAppender : Appender {
  public abstract fun logFormattedMessage(loggingEvent: KLoggingEvent, formattedMessage: Any?)

  override fun log(loggingEvent: KLoggingEvent) {
    KotlinLoggingConfiguration.direct.formatter.formatMessage(loggingEvent).let {
      logFormattedMessage(loggingEvent, it)
    }
  }
}
