package mu

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.companionObject

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
open class NamedKLogging(name: String): KLoggable {
    override val logger: KLogger = logger(name)
}

/**
 * An interface representing class with logging capabilities
 * implemented using a logger
 * obtain a logger with logger() method
 */
interface KLoggable {

    val logger: KLogger

    fun logger(): KLogger = KLogger(jLogger(this.javaClass))
    fun logger(name: String): KLogger = KLogger(jLogger(name))

    ///////////////////////////// private
    private fun <T: Any> jLogger(forClass: Class<T>): Logger {
        return LoggerFactory.getLogger(unwrapCompanionClass(forClass).name)
    }

    private fun jLogger(name: String): Logger {
        return LoggerFactory.getLogger(name)
    }

    // unwrap companion class to enclosing class given a Java Class
    private fun <T: Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
        return if (ofClass.enclosingClass != null && ofClass.enclosingClass.kotlin.companionObject?.java == ofClass) {
            ofClass.enclosingClass
        } else {
            ofClass
        }
    }

}



