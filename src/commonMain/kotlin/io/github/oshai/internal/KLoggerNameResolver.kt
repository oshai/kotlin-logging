package io.github.oshai.internal

internal expect object KLoggerNameResolver {

  internal inline fun name(noinline func: () -> Unit): String
}
