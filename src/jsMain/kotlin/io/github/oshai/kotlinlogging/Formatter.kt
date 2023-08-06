package io.github.oshai.kotlinlogging

public interface Formatter {
  public fun formatMessage(
    level: Level,
    loggerName: String,
    marker: Marker?,
    throwable: Throwable?,
    payload: Map<String, Any>?,
    message: () -> Any?,
  ): Any?
}
