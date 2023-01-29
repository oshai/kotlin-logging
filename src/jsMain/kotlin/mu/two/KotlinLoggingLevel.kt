package mu.two

import mu.KotlinLoggingConfiguration.LOG_LEVEL

public fun mu.two.Level.isLoggingEnabled(): Boolean = this.ordinal >= LOG_LEVEL.ordinal
