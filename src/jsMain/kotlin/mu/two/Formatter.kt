package mu.two

public interface Formatter {
  public fun formatMessage(level: mu.two.Level, loggerName: String, msg: () -> Any?): Any?
  public fun formatMessage(level: mu.two.Level, loggerName: String, t: Throwable?, msg: () -> Any?): Any?
  public fun formatMessage(level: mu.two.Level, loggerName: String, marker: mu.two.Marker?, msg: () -> Any?): Any?
  public fun formatMessage(
      level: mu.two.Level,
      loggerName: String,
      marker: mu.two.Marker?,
      t: Throwable?,
      msg: () -> Any?
  ): Any?
}
