package io.github.oshai

import io.github.oshai.internal.KLoggerJS

public actual object KotlinLogging {
  /**
   * This method allows defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public actual fun logger(func: () -> Unit): io.github.oshai.KLogger =
      KLoggerJS(func::class.js.name)

  public actual fun logger(name: String): io.github.oshai.KLogger = KLoggerJS(name)
}
