package io.github.oshai

import io.github.oshai.KotlinLoggingConfiguration.logLevel

public fun io.github.oshai.Level.isLoggingEnabled(): Boolean = this.ordinal >= logLevel.ordinal
