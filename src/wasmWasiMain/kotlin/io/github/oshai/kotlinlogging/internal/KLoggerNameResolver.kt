package io.github.oshai.kotlinlogging.internal

private const val NO_CLASS = ""

internal actual object KLoggerNameResolver {
  // In WASI the stacktrace is often empty; derive from lambda's synthetic class name when possible.
  internal actual fun name(func: () -> Unit): String {
    val qn = func::class.qualifiedName ?: return NO_CLASS
    // Examples:
    //  - "SimpleWasmWasiTest$anonymousClassPropLogger$lambda" -> "SimpleWasmWasiTest"
    //  - "anonymousFilePropLogger$lambda" -> unknown (return NO_CLASS)
    val base = qn.substringBefore('$')
    return if (base.isNotEmpty() && base != "anonymousFilePropLogger") base else NO_CLASS
  }
}
