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

var outputPipes = ConsoleOutputPipes
var messageFormatter = DefaultMessageFormatter