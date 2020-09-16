package mu

import mu.KotlinLoggingConfiguration.logLevel

public enum class KotlinLoggingLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR
}

public fun KotlinLoggingLevel.isLoggingEnabled(): Boolean = this.ordinal >= logLevel.ordinal
