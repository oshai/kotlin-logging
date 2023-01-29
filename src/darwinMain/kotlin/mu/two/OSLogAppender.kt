package mu.two

import kotlinx.cinterop.ptr
import platform.darwin.OS_LOG_DEFAULT
import platform.darwin.OS_LOG_TYPE_DEBUG
import platform.darwin.OS_LOG_TYPE_DEFAULT
import platform.darwin.OS_LOG_TYPE_ERROR
import platform.darwin.OS_LOG_TYPE_INFO
import platform.darwin.__dso_handle
import platform.darwin._os_log_internal
import platform.darwin.os_log_t
import platform.darwin.os_log_type_enabled
import platform.darwin.os_log_type_t

public open class OSLogAppender : Appender {
  override val includePrefix: Boolean = true

  protected open fun logger(loggerName: String): os_log_t {
    return OS_LOG_DEFAULT
  }

  private fun log(level: os_log_type_t, loggerName: String, message: String) {
    val logger = logger(loggerName)
    if (os_log_type_enabled(logger, level)) {
      _os_log_internal(__dso_handle.ptr, logger, level, message)
    }
  }

  override fun trace(loggerName: String, message: String) {
    log(OS_LOG_TYPE_DEBUG, loggerName, message)
  }

  override fun debug(loggerName: String, message: String) {
    log(OS_LOG_TYPE_DEBUG, loggerName, message)
  }

  override fun info(loggerName: String, message: String) {
    log(OS_LOG_TYPE_INFO, loggerName, message)
  }

  override fun warn(loggerName: String, message: String) {
    log(OS_LOG_TYPE_DEFAULT, loggerName, message)
  }

  override fun error(loggerName: String, message: String) {
    log(OS_LOG_TYPE_ERROR, loggerName, message)
  }
}
