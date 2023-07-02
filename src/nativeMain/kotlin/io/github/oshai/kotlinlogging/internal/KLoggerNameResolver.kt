package io.github.oshai.kotlinlogging.internal

internal actual object KLoggerNameResolver {

  internal actual fun name(func: () -> Unit): String = func::class.qualifiedName ?: ""
}
