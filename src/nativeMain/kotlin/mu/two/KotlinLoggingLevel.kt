package mu.two

import mu.two.KotlinLoggingConfiguration.logLevel

public fun mu.two.Level.isLoggingEnabled(): Boolean = this.ordinal >= logLevel.ordinal
