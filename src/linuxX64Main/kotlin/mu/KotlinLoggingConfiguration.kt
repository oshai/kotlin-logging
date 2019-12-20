package mu

import mu.internal.*

data class KotlinLoggingConfiguration(
    val logLevel: NativeLogLevels = Info,
    val appender: IAppender = ConsoleAppender,
    val format: String = "[${Month}/${Day}/${Year}][${Hour}:${Minute}:${Second} ${TimeZone}]"
)
