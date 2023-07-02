package io.github.oshai.kotlinlogging

public interface Formatter {

  public fun formatMessage(
    includePrefix: Boolean,
    level: Level,
    loggerName: String,
    marker: Marker?,
    t: Throwable?,
    msg: () -> Any?
  ): String
}
