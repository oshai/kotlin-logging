package mu.two

import mu.internal.KLoggerJS

public actual object KotlinLogging {
  /**
   * This method allows defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public actual fun logger(func: () -> Unit): mu.two.KLogger = KLoggerJS(func::class.js.name)

  public actual fun logger(name: String): mu.two.KLogger = KLoggerJS(name)
}
