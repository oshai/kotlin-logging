package io.github.oshai

import io.github.oshai.internal.KLoggerFactory

/**
 * A class with logging capabilities usage example:
 * ```
 * class ClassWithLogging {
 *   companion object: KLogging()
 *   fun test() {
 *     logger.info{"test ClassWithLogging"}
 *   }
 * }
 * ```
 */
public open class KLogging : KLoggable {
  override val logger: KLogger = logger()
}

/** A class with logging capabilities and explicit logger name */
public open class NamedKLogging(name: String) : KLoggable {
  override val logger: KLogger = logger(name)
}

/**
 * An interface representing class with logging capabilities implemented using a logger obtain a
 * logger with logger() method
 */
public interface KLoggable {

  /** The member that performs the actual logging */
  public val logger: KLogger

  /** get logger for the class */
  public fun logger(): KLogger = KLoggerFactory.logger(this)

  /** get logger by explicit name */
  public fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}
