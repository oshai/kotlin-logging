package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.LOG_LEVEL

public fun Level.isLoggingEnabled(): Boolean = this.ordinal >= LOG_LEVEL.ordinal
