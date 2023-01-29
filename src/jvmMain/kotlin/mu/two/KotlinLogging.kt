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
  public actual fun logger(func: () -> Unit): KLogger = KLoggerFactory.logger(func)

  public actual fun logger(name: String): KLogger = KLoggerFactory.logger(name)

  public fun logger(underlyingLogger: Logger): KLogger =
      KLoggerFactory.wrapJLogger(underlyingLogger)
}

public fun Logger.toKLogger(): KLogger = KotlinLogging.logger(this)
