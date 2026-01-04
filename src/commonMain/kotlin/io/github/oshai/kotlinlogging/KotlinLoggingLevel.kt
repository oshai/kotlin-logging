package io.github.oshai.kotlinlogging

internal fun Level.isLoggingEnabled(): Boolean =
  this.compareTo(KotlinLoggingConfiguration.direct.logLevel) >= 0
