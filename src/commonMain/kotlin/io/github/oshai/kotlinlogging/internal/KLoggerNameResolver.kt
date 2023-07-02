package io.github.oshai.kotlinlogging.internal

internal expect object KLoggerNameResolver {

  internal fun name(func: () -> Unit): String
}
