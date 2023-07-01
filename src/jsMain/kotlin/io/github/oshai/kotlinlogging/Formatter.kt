package io.github.oshai.kotlinlogging

public interface Formatter {
  public fun formatMessage(
    level: Level,
    loggerName: String,
    marker: Marker?,
    throwable: Throwable?,
    message: () -> Any?
  ): Any?
}
