package io.github.oshai

import io.github.oshai.internal.KLoggerFactory

public actual object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public actual fun logger(func: () -> Unit): KLogger = KLoggerFactory.logger(func)

  public actual fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}
