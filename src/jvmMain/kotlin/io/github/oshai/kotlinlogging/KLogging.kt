package io.github.oshai.kotlinlogging

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
@Deprecated("Use KLogger instead", ReplaceWith("KLogger", "io.github.oshai.kotlinlogging.KLogger"))
@Suppress("DEPRECATION")
public open class KLogging : KLoggable {
  override val logger: KLogger = logger()
}

/** A class with logging capabilities and explicit logger name */
@Deprecated("Use KLogger instead", ReplaceWith("KLogger", "io.github.oshai.kotlinlogging.KLogger"))
@Suppress("DEPRECATION")
public open class NamedKLogging(name: String) : KLoggable {
  override val logger: KLogger = logger(name)
}

/**
 * An interface representing class with logging capabilities implemented using a logger obtain a
 * logger with logger() method
 */
@Deprecated("Use KLogger instead", ReplaceWith("KLogger", "io.github.oshai.kotlinlogging.KLogger"))
public interface KLoggable {

  /** The member that performs the actual logging */
  public val logger: KLogger

  /** get logger for the class */
  public fun logger(): KLogger =
    KotlinLoggingConfiguration.loggerFactory.logger(this::class.java.name)

  /** get logger by explicit name */
  public fun logger(name: String): KLogger = KotlinLoggingConfiguration.loggerFactory.logger(name)
}
