package io.github.oshai

import io.github.oshai.internal.KLoggerLinux

public actual object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public actual fun logger(func: () -> Unit): io.github.oshai.KLogger =
      KLoggerLinux(func::class.qualifiedName ?: "")

  public actual fun logger(name: String): io.github.oshai.KLogger = KLoggerLinux(name)
}
