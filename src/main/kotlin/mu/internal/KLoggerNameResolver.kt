package mu.internal

import java.lang.reflect.Modifier


/**
 * Resolves name of classes using reflection
 */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerNameResolver {

    /**
     * get class name for obj, if companion object, get the class name of the enclosing class
     */
    inline internal fun <T : Any> name(obj : T): String =
            unwrapCompanionClass(obj).name


    /**
     * unwrap companion class to enclosing class given a Java Class
     */
    inline private fun <T : Any> unwrapCompanionClass(obj: T): Class<*> {
        val clazz: Class<T> = obj.javaClass
        if (clazz.enclosingClass != null) {
            try {
                val field = clazz.enclosingClass.getField(clazz.simpleName)
                if (Modifier.isStatic(field.modifiers) && field.type == clazz && field.get(null) === obj) {
                    return clazz.enclosingClass
                }
            } catch(e: Exception) {
                //ok, it is not a companion object
            }
        }
        return clazz
    }
}
