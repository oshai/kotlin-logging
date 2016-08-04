package mu

import mu.internal.KLoggerFactory

/**
 * An class with logging capabilities
 * usage example:
 * class ClassWithLogging {
 *   companion object: KLogging()
 *   fun test() {
 *     logger.info{"test ClassWithLogging"}
 *   }
 * }
 */
open class KLogging : KLoggable {
    override val logger: KLogger = logger()
}

/**
 * An class with logging capabilities and explicit logger name
 */
open class NamedKLogging(name: String): KLoggable {
    override val logger: KLogger = logger(name)
}

/**
 * An interface representing class with logging capabilities
 * implemented using a logger
 * obtain a logger with logger() method
 */
interface KLoggable {

    /**
     * The member that performs the actual logging
     */
    val logger: KLogger

    /**
     * get logger for the class
     */
    fun logger(): KLogger = KLoggerFactory.logger(this)

    /**
     * get logger by explicit name
     */
    fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}



