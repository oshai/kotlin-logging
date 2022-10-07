package mu

import platform.posix.fprintf
import platform.posix.stderr

public object ConsoleOutputAppender : Appender {
    override val includePrefix: Boolean = true
    public override fun trace(loggerName: String, message: String): Unit = println(message)
    public override fun debug(loggerName: String, message: String): Unit = println(message)
    public override fun info(loggerName: String, message: String): Unit = println(message)
    public override fun warn(loggerName: String, message: String): Unit = println(message)

    override fun error(loggerName: String, message: String) {
        fprintf(stderr, "$message\n")
    }
}
