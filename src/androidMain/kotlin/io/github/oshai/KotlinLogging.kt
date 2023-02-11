package io.github.oshai


import io.github.oshai.internal.KLoggerAndroid
import io.github.oshai.internal.KLoggerNameResolver

public actual object KotlinLogging {
  /**
   * This method allows defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public actual fun logger(func: () -> Unit): KLogger =
      logger(KLoggerNameResolver.name(func))

  public actual fun logger(name: String): KLogger = KLoggerAndroid(name)
}
