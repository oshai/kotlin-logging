package io.github.oshai

public interface Formatter {
  public fun formatMessage(
      includePrefix: Boolean,
      level: Level,
      loggerName: String,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: Level,
      loggerName: String,
      t: Throwable?,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: Level,
      loggerName: String,
      marker: Marker?,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: Level,
      loggerName: String,
      marker: Marker?,
      t: Throwable?,
      msg: () -> Any?
  ): String
}
