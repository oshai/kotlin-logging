package io.github.oshai

public interface Formatter {
  public fun formatMessage(
      includePrefix: Boolean,
      level: io.github.oshai.Level,
      loggerName: String,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: io.github.oshai.Level,
      loggerName: String,
      t: Throwable?,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: io.github.oshai.Level,
      loggerName: String,
      marker: io.github.oshai.Marker?,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: io.github.oshai.Level,
      loggerName: String,
      marker: io.github.oshai.Marker?,
      t: Throwable?,
      msg: () -> Any?
  ): String
}
