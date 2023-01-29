package mu.two

public object ConsoleOutputAppender : Appender {
  public override fun trace(message: Any?): Unit = console.log(message)
  public override fun debug(message: Any?): Unit = console.log(message)
  public override fun info(message: Any?): Unit = console.info(message)
  public override fun warn(message: Any?): Unit = console.warn(message)
  public override fun error(message: Any?): Unit = console.error(message)
}
