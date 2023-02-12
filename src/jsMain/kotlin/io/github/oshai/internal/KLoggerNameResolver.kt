package io.github.oshai.internal

@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerNameResolver {

  internal actual inline fun name(noinline func: () -> Unit): String = func::class.js.name
}
