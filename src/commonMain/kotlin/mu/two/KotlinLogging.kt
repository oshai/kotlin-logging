package io.github.oshai

public expect object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public fun logger(func: () -> Unit): io.github.oshai.KLogger

  public fun logger(name: String): io.github.oshai.KLogger
}
