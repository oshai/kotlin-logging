package mu

import mu.KotlinLoggingConfiguration.logLevel

enum class KotlinLoggingLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR
}

fun KotlinLoggingLevel.isLoggingEnabled() = this.ordinal >= logLevel.value.ordinal
