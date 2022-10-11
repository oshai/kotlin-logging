package mu

import kotlin.native.concurrent.AtomicReference
import platform.darwin.os_log_create
import platform.darwin.os_log_t

public class OSLogSubsystemAppender(public val subsystem: String) : OSLogAppender() {
  override val includePrefix: Boolean = false

  private val logs: AtomicReference<Map<String, os_log_t>> = AtomicReference(mapOf())

  override fun logger(loggerName: String): os_log_t {
    var logger: os_log_t
    do {
      val existing = logs.value
      logger = existing[loggerName]
      if (logger != null) {
        return logger
      }

      val updated = existing.toMutableMap()
      logger = os_log_create(subsystem, loggerName)
      updated[loggerName] = logger
    } while (!logs.compareAndSet(existing, updated))

    return logger
  }
}
