package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.DarwinKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration
import platform.darwin.os_log_create

/** factory methods to obtain a [KLogger] */
internal actual object KLoggerFactory {

  /** get logger by explicit name */
  internal actual fun logger(name: String): KLogger {
    val subsystemConfigured = KotlinLoggingConfiguration.subsystem.value
    val categoryConfigured = KotlinLoggingConfiguration.category.value
    return when {
      subsystemConfigured != null || categoryConfigured != null -> {
        DarwinKLogger(name, os_log_create(subsystemConfigured ?: name, categoryConfigured ?: name))
      }
      name.contains(".") -> {
        DarwinKLogger(
          name,
          os_log_create(name.substringBeforeLast("."), name.substringAfterLast("."))
        )
      }
      else -> {
        DarwinKLogger(name, os_log_create(name, "default"))
      }
    }
  }
}
