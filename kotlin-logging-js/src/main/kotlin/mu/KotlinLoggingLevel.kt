package mu

var LOG_LEVEL = KotlinLoggingLevel.INFO

enum class KotlinLoggingLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR
}

fun KotlinLoggingLevel.isLoggingEnabled() = this.ordinal >= LOG_LEVEL.ordinal

var outputPipes: OutputPipes = ConsoleOutputPipes
var messageFormatter: MessageFormatter = DefaultMessageFormatter