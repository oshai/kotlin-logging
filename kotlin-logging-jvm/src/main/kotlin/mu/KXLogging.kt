package mu

import mu.internal.KXLoggerFactory

/**
 * A class with logging capabilities
 * usage example:
 * class ClassWithLogging {
 *   companion object: KLogging()
 *   fun test() {
 *     logger.info{"test ClassWithLogging"}
 *   }
 * }
 */
open class KXLogging : KXLoggable {
    override val xlogger: KXLogger = xlogger()
}

/**
 * A class with logging capabilities and explicit logger name
 */
open class NamedKXLogging(name: String): KXLoggable {
    override val xlogger: KXLogger = xlogger(name)
}

/**
 * An interface representing class with logging capabilities
 * implemented using a logger
 * obtain a logger with logger() method
 */
interface KXLoggable {

    /**
     * The member that performs the actual logging
     */
    val xlogger: KXLogger

    /**
     * get logger for the class
     */
    fun xlogger(): KXLogger = KXLoggerFactory.xlogger(this)

    /**
     * get logger by explicit name
     */
    fun xlogger(name: String): KXLogger = KXLoggerFactory.xlogger(name)
}



