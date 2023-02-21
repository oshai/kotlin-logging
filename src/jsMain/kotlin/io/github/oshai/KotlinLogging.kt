package io.github.oshai

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * The JS way to define a logger without explicit name
 * ```
 * class MyClass {
 *     private val logger by KotlinLogging.logger()
 * }
 * ```
 */
public fun KotlinLogging.logger(): ReadOnlyProperty<Any?, KLogger> = LoggerDelegate()

private class LoggerDelegate : ReadOnlyProperty<Any?, KLogger> {
  private lateinit var logger: KLogger

  override fun getValue(thisRef: Any?, property: KProperty<*>): KLogger {
    if (!::logger.isInitialized) {
      logger =
        thisRef.asDynamic()?.constructor?.name.unsafeCast<String?>()?.let {
          KotlinLogging.logger(it)
        }
          ?: KotlinLogging.logger("root-logger")
    }
    return logger
  }
}
