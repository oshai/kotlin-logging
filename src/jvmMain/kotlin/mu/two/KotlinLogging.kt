package mu.two

import mu.two.internal.KLoggerFactory
import org.slf4j.Logger

public actual object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public actual fun logger(func: () -> Unit): mu.two.KLogger = KLoggerFactory.logger(func)

  public actual fun logger(name: String): mu.two.KLogger = KLoggerFactory.logger(name)

  public fun logger(underlyingLogger: Logger): mu.two.KLogger =
      KLoggerFactory.wrapJLogger(underlyingLogger)
}

public fun Logger.toKLogger(): mu.two.KLogger = mu.two.KotlinLogging.logger(this)
