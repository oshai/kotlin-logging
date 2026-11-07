package io.github.oshai.kotlinlogging

public class ConsoleOutputAppender : Appender {
  override fun log(loggingEvent: KLoggingEvent) {
    println(KotlinLoggingConfiguration.direct.formatter.formatMessage(loggingEvent))
  }
}
