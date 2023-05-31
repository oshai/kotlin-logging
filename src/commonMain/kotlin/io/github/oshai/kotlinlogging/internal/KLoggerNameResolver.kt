package io.github.oshai.kotlinlogging.internal

internal expect object KLoggerNameResolver {

  internal inline fun name(noinline func: () -> Unit): String
}
