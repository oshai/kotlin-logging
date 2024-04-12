@file:OptIn(ExperimentalForeignApi::class)

package io.github.oshai.kotlinlogging

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import platform.darwin.OS_LOG_TYPE_DEBUG
import platform.darwin.OS_LOG_TYPE_DEFAULT
import platform.darwin.OS_LOG_TYPE_ERROR
import platform.darwin.OS_LOG_TYPE_INFO
import platform.darwin.__dso_handle
import platform.darwin._os_log_internal
import platform.darwin.os_log_t
import platform.darwin.os_log_type_enabled
import platform.darwin.os_log_type_t

public class DarwinKLogger(override val name: String, override val underlyingLogger: os_log_t) :
  KLogger, DelegatingKLogger<os_log_t> {

  override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
    if (isLoggingEnabledFor(level, marker)) {
      KLoggingEventBuilder().apply(block).run {
        val formattedMessage = buildString {
          marker?.getName()?.let {
            append(it)
            append(" ")
          }
          append(message)
          cause?.stackTraceToString()?.let {
            append('\n')
            append(it)
          }
        }
        _os_log_internal(
          __dso_handle.ptr,
          underlyingLogger,
          level.toDarwinLevel(),
          formattedMessage,
        )
      }
    }
  }

  private fun Level.toDarwinLevel(): os_log_type_t {
    return when (this) {
      Level.TRACE -> OS_LOG_TYPE_DEBUG
      Level.DEBUG -> OS_LOG_TYPE_DEBUG
      Level.INFO -> OS_LOG_TYPE_INFO
      Level.WARN -> OS_LOG_TYPE_DEFAULT
      Level.ERROR -> OS_LOG_TYPE_ERROR
      Level.OFF -> throw Exception("off level cannot be mapped to darwin level")
    }
  }

  override fun isLoggingEnabledFor(level: Level, marker: Marker?): Boolean {
    return when (level) {
      Level.OFF -> false
      else -> os_log_type_enabled(underlyingLogger, level.toDarwinLevel())
    }
  }
}
