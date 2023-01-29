package mu.two

public interface Formatter {
  public fun formatMessage(level: Level, loggerName: String, msg: () -> Any?): Any?
  public fun formatMessage(level: Level, loggerName: String, t: Throwable?, msg: () -> Any?): Any?
  public fun formatMessage(level: Level, loggerName: String, marker: Marker?, msg: () -> Any?): Any?
  public fun formatMessage(
      level: Level,
      loggerName: String,
      marker: Marker?,
      t: Throwable?,
      msg: () -> Any?
  ): Any?
}
