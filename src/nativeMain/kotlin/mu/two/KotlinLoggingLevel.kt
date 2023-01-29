package mu.two

import mu.KotlinLoggingConfiguration.logLevel

public fun mu.two.Level.isLoggingEnabled(): Boolean = this.ordinal >= logLevel.ordinal
