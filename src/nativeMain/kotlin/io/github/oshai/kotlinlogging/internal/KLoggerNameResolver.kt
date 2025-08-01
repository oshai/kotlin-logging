package io.github.oshai.kotlinlogging.internal

internal actual object KLoggerNameResolver {

  internal actual fun name(func: () -> Unit): String {
    // First, try to get the name using reflection. This works in most cases (e.g., inside a class).
    val nameFromReflection = func::class.qualifiedName?.substringBeforeLast(".<anonymous>")
    if (!nameFromReflection.isNullOrBlank()) {
      return nameFromReflection
    }

    // As a fallback for Kotlin/Native top-level properties, inspect the call stack.
    val stackTrace = Throwable().stackTraceToString().lines()

    // The stack trace frame of interest is the one *after* the call to the logger factory.
    val loggerFactoryCallIndex =
      stackTrace.indexOfFirst { it.contains("io.github.oshai.kotlinlogging.KotlinLogging.logger") }

    if (loggerFactoryCallIndex == -1 || loggerFactoryCallIndex + 1 >= stackTrace.size) {
      return "UnknownLogger"
    }

    val callerFrame = stackTrace[loggerFactoryCallIndex + 1]

    // Extract the fully qualified class/file name from the stack frame.
    // A typical frame looks like: "at
    // kfun:io.github.oshai.kotlinlogging.SimpleNativeTestKt.<clinit>()"
    return callerFrame
      .let {
        val regex = Regex("""at kfun:([^#(<]+)""")
        regex.find(it)?.groupValues?.get(1)
      }
      ?.trim() ?: "UnknownLogger" // Provide a sensible default if all else fails.
  }
}
