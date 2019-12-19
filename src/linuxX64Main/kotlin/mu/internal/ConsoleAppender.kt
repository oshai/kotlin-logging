package mu.internal

import kotlinx.cinterop.*
import platform.posix.*

object ConsoleAppender : IAppender {
    private val STDOUT = platform.posix.fdopen(1, "w")
    private val STDERR = platform.posix.fdopen(2, "w")

    private fun prefix(level: NativeLogLevels): String = memScoped {
        val t = alloc<time_tVar>()
        t.value = time(null)
        val dateTime = asctime(gmtime(t.ptr))
        val timeString = dateTime?.toKString() ?: ""
        return "[${level.name}][${timeString.trim()}]"
    }

    private fun printWrapper(level: NativeLogLevels, message: String, descriptor: CPointer<FILE>?) {
        val fullMessage = prefix(level) + message + "\n"
        fprintf(descriptor, fullMessage)
        val fflush = fflush(descriptor)

    }

    private fun print(level: NativeLogLevels, message: String) = printWrapper(level, message, STDOUT)
    private fun printErr(level: NativeLogLevels, message: String) = printWrapper(level, message, STDERR)

    override fun debug(message: String?) = print(Debug, message ?: "")

    override fun info(message: String?) = print(Info, message ?: "")

    override fun warn(message: String?) = print(Warn, message ?: "")

    override fun error(message: String?) = printErr(Error, message ?: "")

    override fun fatal(message: String?) = printErr(Fatal, message ?: "")
}
