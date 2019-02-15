package mu

object ConsoleOutputAppender : Appender {
    override fun trace(message: Any?) = console.log(message)
    override fun debug(message: Any?) = console.log(message)
    override fun info(message: Any?) = console.info(message)
    override fun warn(message: Any?) = console.warn(message)
    override fun error(message: Any?) = console.error(message)
}