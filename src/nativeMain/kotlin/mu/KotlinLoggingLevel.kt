package mu

import mu.KotlinLoggingConfiguration.logLevel

public fun Level.isLoggingEnabled(): Boolean = this.ordinal >= logLevel.ordinal
