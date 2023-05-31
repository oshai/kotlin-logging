package io.github.oshai.kotlinlogging.internal

internal actual object KLoggerNameResolver {

  internal actual inline fun name(noinline func: () -> Unit): String =
    func::class.qualifiedName ?: ""
}
