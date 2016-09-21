package mu.internal

import java.lang.reflect.Modifier


/**
 * Resolves name of classes using reflection
 */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerNameResolver {

    /**
     * get class name for java class (that usually represents kotlin class)
     */
    inline internal fun <T : Any> name(forClass: Class<T>): String =
            unwrapCompanionClass(forClass).name


    /**
     * unwrap companion class to enclosing class given a Java Class
     */
    inline private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
        if (clazz.enclosingClass != null) {
            try {
                val field = clazz.enclosingClass.getField(clazz.simpleName)
                if (Modifier.isStatic(field.modifiers) && field.type == clazz ) {
                    // && field.get(null) === obj
                    return clazz.enclosingClass
                }
            } catch(e: Exception) {
                //ok, it is not a companion object
            }
        }
        return clazz
    }
}
