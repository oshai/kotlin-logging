package mu

import mu.KotlinLoggingConfiguration.LOG_LEVEL

public fun Level.isLoggingEnabled(): Boolean = this.ordinal >= LOG_LEVEL.ordinal
