package mu.internal

import kotlinx.cinterop.CPointer
import mu.KotlinLoggingConfiguration
import platform.posix.FILE
import platform.posix.fflush
import platform.posix.fprintf

object ConsoleAppender : IAppender {
    private val STDOUT = platform.posix.fdopen(1, "w")
    private val STDERR = platform.posix.fdopen(2, "w")

    private fun prefix(config: KotlinLoggingConfiguration, level: NativeLogLevels): String =
        "[${level.name}]${config.format}"


    private fun printWrapper(
        config: KotlinLoggingConfiguration,
        level: NativeLogLevels,
        message: String,
        descriptor: CPointer<FILE>?
    ) {
        val fullMessage = prefix(config, level) + message + "\n"
        fprintf(descriptor, fullMessage)
        fflush(descriptor)
    }

    private fun print(config: KotlinLoggingConfiguration, level: NativeLogLevels, message: String) =
        printWrapper(config, level, message, STDOUT)

    private fun printErr(config: KotlinLoggingConfiguration, level: NativeLogLevels, message: String) =
        printWrapper(config, level, message, STDERR)

    override fun debug(config: KotlinLoggingConfiguration, message: String?) = print(config, Debug, message ?: "")

    override fun info(config: KotlinLoggingConfiguration, message: String?) = print(config, Info, message ?: "")

    override fun warn(config: KotlinLoggingConfiguration, message: String?) = print(config, Warn, message ?: "")

    override fun error(config: KotlinLoggingConfiguration, message: String?) = printErr(config, Error, message ?: "")

    override fun fatal(config: KotlinLoggingConfiguration, message: String?) = printErr(config, Fatal, message ?: "")
}
