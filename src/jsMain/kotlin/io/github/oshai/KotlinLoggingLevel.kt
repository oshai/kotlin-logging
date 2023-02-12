package io.github.oshai

import io.github.oshai.KotlinLoggingConfiguration.LOG_LEVEL

public fun Level.isLoggingEnabled(): Boolean = this.ordinal >= LOG_LEVEL.ordinal
