package mu.two

import mu.two.internal.KLoggerFactory

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
  override val logger: mu.two.KLogger = logger()
}

/** A class with logging capabilities and explicit logger name */
public open class NamedKLogging(name: String) : KLoggable {
  override val logger: mu.two.KLogger = logger(name)
}

/**
 * An interface representing class with logging capabilities implemented using a logger obtain a
 * logger with logger() method
 */
public interface KLoggable {

  /** The member that performs the actual logging */
  public val logger: mu.two.KLogger

  /** get logger for the class */
  public fun logger(): mu.two.KLogger = KLoggerFactory.logger(this)

  /** get logger by explicit name */
  public fun logger(name: String): mu.two.KLogger = KLoggerFactory.logger(name)
}
