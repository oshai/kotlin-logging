package io.github.oshai

import io.github.oshai.internal.KLoggerFactory
import io.github.oshai.internal.KLoggerNameResolver

public object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * private val logger = KotlinLogging.logger {}
   * ```
   */
  public fun logger(func: () -> Unit): KLogger = logger(KLoggerNameResolver.name(func))

  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * private val logger = KotlinLogging.logger("io.github.oshai.MyLogger")
   * ```
   * In most cases the name represents the package notation of the file that the logger is defined
   * in.
   */
  public fun logger(name: String, decorator: KLoggerDecorator = DoNothingDecorator): KLogger = KLoggerFactory.logger(name, decorator)
}
