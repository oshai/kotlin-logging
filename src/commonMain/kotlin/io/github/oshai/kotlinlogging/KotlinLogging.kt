package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.KLoggerNameResolver
import kotlin.js.JsName

public object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * private val logger = KotlinLogging.logger {}
   * ```
   *
   * On JVM and Android, logger name resolution strips everything from the first `$` in the binary
   * class name, so loggers declared inside nested or inner classes (and their companion objects)
   * use the enclosing top-level class name. To use a distinct logger name for a nested class, pass
   * it explicitly via `KotlinLogging.logger(Outer.Inner::class.java.name)`.
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
  public fun logger(name: String): KLogger = KotlinLoggingConfiguration.loggerFactory.logger(name)

  init {
    val factory = KotlinLoggingConfiguration.loggerFactory
    if (KotlinLoggingConfiguration.logStartupMessage) {
      println("kotlin-logging: initializing... active logger factory: ${factory::class.simpleName}")
    }
  }
}
