package mu.internal

import mu.KLoggable
import mu.KLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.companionObject

/**
 * Helper methods to obtain a logger
 */
internal object LogHelper {

    /**
     * get logger for the class
     */
    internal fun logger(loggable: KLoggable): KLogger = KLogger(jLogger(name(loggable.javaClass)))

    /**
     * get logger by explicit name
     */
    internal fun logger(name: String): KLogger = KLogger(jLogger(name))

    /**
     * get class name for java class (that usually represents kotlin class)
     */
    internal fun <T: Any> name(forClass: Class<T>): String {
        return unwrapCompanionClass(forClass).name
    }

    /**
     * get a java logger by name
     */
    internal fun jLogger(name: String): Logger {
        return LoggerFactory.getLogger(name)
    }

    /**
     * unwrap companion class to enclosing class given a Java Class
     */
    internal fun <T: Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
        return if (ofClass.enclosingClass != null && ofClass.enclosingClass.kotlin.companionObject?.java == ofClass) {
            ofClass.enclosingClass
        } else {
            ofClass
        }
    }
}
