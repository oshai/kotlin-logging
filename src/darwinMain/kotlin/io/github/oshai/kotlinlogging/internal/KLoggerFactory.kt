package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.DarwinKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration
import kotlin.native.concurrent.AtomicReference
import platform.darwin.OS_LOG_DEFAULT
import platform.darwin.os_log_create

/** factory methods to obtain a [KLogger] */
internal actual object KLoggerFactory {

  private val constantLogger: AtomicReference<KLogger?> = AtomicReference(null)
  private val constantOsDefaultLogger: KLogger = DarwinKLogger("", OS_LOG_DEFAULT)

  /** get logger by explicit name */
  internal actual fun logger(name: String): KLogger {
    val subsystemConfigured = KotlinLoggingConfiguration.subsystem.value
    val categoryConfigured = KotlinLoggingConfiguration.category.value
    return when {
      subsystemConfigured != null && categoryConfigured != null -> {
        constantLogger.value
          ?: DarwinKLogger(name, os_log_create(subsystemConfigured, categoryConfigured)).also {
            constantLogger.value = it
          }
      }
      subsystemConfigured != null || categoryConfigured != null -> {
        DarwinKLogger(name, os_log_create(subsystemConfigured ?: name, categoryConfigured ?: name))
      }
      name.isBlank() -> constantOsDefaultLogger
      name.contains(".") -> {
        DarwinKLogger(
          name,
          os_log_create(name.substringBeforeLast("."), name.substringAfterLast("."))
        )
      }
      else -> {
        DarwinKLogger(name, os_log_create(name, ""))
      }
    }
  }
}
