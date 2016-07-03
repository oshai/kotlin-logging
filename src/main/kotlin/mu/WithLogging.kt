package mu

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.companionObject

open class WithLogging: HasLogging {
    override val logger: KLogger = logger()
}
open class WithNamedLogging(name: String): HasLogging {
    override val logger: KLogger = logger(name)
}
interface HasLogging {

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
//
//    import kotlin.reflect.KClass

//    // unwrap companion class to enclosing class given a Kotlin Class
//    private fun <T: Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
//        return unwrapCompanionClass(ofClass.java).kotlin
//    }
//
//    // Return logger for Kotlin class
//    private fun <T: Any> logger(forClass: KClass<T>): Logger {
//        return logger(forClass.java)
//    }
}



