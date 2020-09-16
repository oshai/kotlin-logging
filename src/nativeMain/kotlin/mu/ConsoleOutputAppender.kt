package mu

import platform.posix.fprintf
import platform.posix.stderr

public object ConsoleOutputAppender : Appender {
    public override fun trace(message: Any?): Unit = println(message)
    public override fun debug(message: Any?): Unit = println(message)
    public override fun info(message: Any?): Unit = println(message)
    public override fun warn(message: Any?): Unit = println(message)

    override fun error(message: Any?) {
        fprintf(stderr, "$message\n")
    }
}
