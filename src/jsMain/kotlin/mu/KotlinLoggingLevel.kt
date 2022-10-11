package mu

import mu.KotlinLoggingConfiguration.LOG_LEVEL

public enum class KotlinLoggingLevel {
  TRACE,
  DEBUG,
  INFO,
  WARN,
  ERROR
}

public fun KotlinLoggingLevel.isLoggingEnabled(): Boolean = this.ordinal >= LOG_LEVEL.ordinal
