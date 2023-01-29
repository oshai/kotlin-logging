package mu.two

public interface Formatter {
  public fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      t: Throwable?,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      marker: mu.two.Marker?,
      msg: () -> Any?
  ): String
  public fun formatMessage(
      includePrefix: Boolean,
      level: mu.two.Level,
      loggerName: String,
      marker: mu.two.Marker?,
      t: Throwable?,
      msg: () -> Any?
  ): String
}
