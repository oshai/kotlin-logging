package mu.internal


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
    inline private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
        if (ofClass.enclosingClass != null) {
            try {
                ofClass.enclosingClass.getField(ofClass.simpleName)
                return ofClass.enclosingClass
            } catch(e: NoSuchFieldException) {
            }
        }
        return ofClass
    }
}
