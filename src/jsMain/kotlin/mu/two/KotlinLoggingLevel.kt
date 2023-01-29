package mu.two

import mu.two.KotlinLoggingConfiguration.LOG_LEVEL

public fun mu.two.Level.isLoggingEnabled(): Boolean = this.ordinal >= LOG_LEVEL.ordinal
