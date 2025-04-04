package io.github.oshai.kotlinlogging.internal

internal expect object KLoggerNameResolver {

  internal fun name(ref: Any): String
}
