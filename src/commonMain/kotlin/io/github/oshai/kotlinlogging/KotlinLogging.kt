package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.KLoggerNameResolver

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
   * private val logger = KotlinLogging.logger("io.github.oshai.kotlinlogging.MyLogger")
   * ```
   *
   * In most cases the name represents the package notation of the file that the logger is defined
   * in.
   */
  public fun logger(name: String): KLogger = KotlinLoggingConfiguration.loggerFactory.logger(name)

  init {
    val factory = KotlinLoggingConfiguration.loggerFactory
    println("kotlin-logging: initializing... active logger factory: ${factory::class.simpleName}")
  }
}
