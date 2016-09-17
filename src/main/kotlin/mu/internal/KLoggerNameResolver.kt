package mu.internal


/**
 * Resolves name of classes using reflection
 */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerNameResolver {

    /**
     * get class name for java class (that usually represents kotlin class)
     */
    inline internal fun <T : Any> name(forClass: Class<T>): String {
        val name = forClass.name
        return (
                if (name.endsWith("\$Companion"))
                    name.substring(0, name.length - 10)
                else
                    name
                )
    }


}
