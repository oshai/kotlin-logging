package io.github.oshai

import io.github.oshai.KotlinLoggingConfiguration.logLevel

public fun Level.isLoggingEnabled(): Boolean = this.ordinal >= logLevel.ordinal
