package mu.internal

import java.lang.reflect.Modifier


/**
 * Resolves name of java classes
 */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerNameResolver {

    /**
     * get class name for function by the package of the function
     */
    internal inline fun name(noinline func: () -> Unit): String {
        val name = func.javaClass.name
        val slicedName = when {
            name.contains("Kt$") -> name.substringBefore("Kt$")
            name.contains("$") -> name.substringBefore("$")
            else -> name
        }
        return slicedName
    }

    /**
     * get class name for java class (that usually represents kotlin class)
     */
    internal inline fun <T : Any> name(forClass: Class<T>): String =
            unwrapCompanionClass(forClass).name


    /**
     * unwrap companion class to enclosing class given a Java Class
     */
    private inline fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
        if (clazz.enclosingClass != null) {
            try {
                val field = clazz.enclosingClass.getField(clazz.simpleName)
                if (Modifier.isStatic(field.modifiers) && field.type == clazz) {
                    // && field.get(null) === obj
                    // the above might be safer but problematic with initialization order
                    return clazz.enclosingClass
                }
            } catch(e: Exception) {
                //ok, it is not a companion object
            }
        }
        return clazz
    }
}
