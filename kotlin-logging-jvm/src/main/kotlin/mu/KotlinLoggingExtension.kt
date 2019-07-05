package mu

import mu.internal.KLoggerNameResolver
import kotlin.reflect.KProperty

inline fun <reified T : Any> T.logger(): KLogger =
    KotlinLogging.logger(
        @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
        KLoggerNameResolver.name(forClass = T::class.java)
    )

val <T : Any> T.log: KLogger by LoggerLoader()

private class LoggerLoader<T : Any> {
    private val loggers: MutableMap<Class<*>, KLogger> = mutableMapOf()

    operator fun getValue(thisRef: T, property: KProperty<*>): KLogger {
        val instanceClass = thisRef::class.java
        val v1 = loggers[instanceClass]
        if (v1 !== null) {
            return v1
        }

        return synchronized(this) {
            val v2 = loggers[instanceClass]
            if (v2 !== null) {
                v2
            } else {
                val loggerForClass = KotlinLogging.logger(
                    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
                    KLoggerNameResolver.name(forClass = instanceClass)
                )
                loggers[instanceClass] = loggerForClass
                loggerForClass
            }
        }
    }
}
