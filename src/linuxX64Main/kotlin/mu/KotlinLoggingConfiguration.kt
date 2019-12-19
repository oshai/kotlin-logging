package mu

import mu.internal.ConsoleAppender
import mu.internal.IAppender
import mu.internal.Info
import mu.internal.NativeLogLevels

data class KotlinLoggingConfiguration(
    val logLevel: NativeLogLevels = Info,
    val appender: IAppender = ConsoleAppender
)
