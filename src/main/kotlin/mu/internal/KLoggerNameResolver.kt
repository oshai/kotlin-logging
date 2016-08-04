package mu.internal

import kotlin.reflect.companionObject

/**
 * Resolves name of classes using reflection
 */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerNameResolver {

    /**
     * get class name for java class (that usually represents kotlin class)
     */
    inline internal fun <T: Any> name(forClass: Class<T>): String =
        unwrapCompanionClass(forClass).name


    /**
     * unwrap companion class to enclosing class given a Java Class
     */
    inline private fun <T: Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> =
        if (ofClass.enclosingClass != null && ofClass.enclosingClass.kotlin.companionObject?.java == ofClass) {
            ofClass.enclosingClass
        } else {
            ofClass
        }

}
