package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.KLoggerFactory
import io.github.oshai.kotlinlogging.internal.KLoggerNameResolver
import kotlin.js.JsName

public object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * private val logger = KotlinLogging.logger {}
   * ```
   */
  @JsName("kotlinLoggerByFunc")
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
  @JsName("kotlinLoggerByName")
  public fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}
