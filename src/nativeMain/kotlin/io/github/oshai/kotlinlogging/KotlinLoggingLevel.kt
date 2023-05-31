package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.logLevel

public fun Level.isLoggingEnabled(): Boolean = this.ordinal >= logLevel.ordinal
