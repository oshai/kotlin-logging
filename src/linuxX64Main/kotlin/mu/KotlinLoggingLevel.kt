package mu

import mu.KotlinLoggingConfiguration.LOG_LEVEL

enum class KotlinLoggingLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR
}

fun KotlinLoggingLevel.isLoggingEnabled() = this.ordinal >= LOG_LEVEL.ordinal
