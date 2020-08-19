package mu

import platform.posix.fprintf
import platform.posix.stderr

object ConsoleOutputAppender : Appender {
    override fun trace(message: Any?) = println(message)
    override fun debug(message: Any?) = println(message)
    override fun info(message: Any?) = println(message)
    override fun warn(message: Any?) = println(message)

    override fun error(message: Any?) {
        fprintf(stderr, "$message\n")
    }
}
