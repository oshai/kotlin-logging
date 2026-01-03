package io.github.oshai.kotlinlogging

public class ConsoleOutputAppender : Appender {
  override fun log(loggingEvent: KLoggingEvent) {
    println(KotlinLoggingConfiguration.formatter.formatMessage(loggingEvent))
  }
}
