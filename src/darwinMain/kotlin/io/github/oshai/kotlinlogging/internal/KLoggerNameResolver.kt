package io.github.oshai.kotlinlogging.internal

internal actual object KLoggerNameResolver {

  internal actual fun name(ref: Any): String = ref::class.qualifiedName ?: ""
}
