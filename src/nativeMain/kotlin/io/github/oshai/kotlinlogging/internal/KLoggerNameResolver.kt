package io.github.oshai.kotlinlogging.internal

internal actual object KLoggerNameResolver {

  internal actual fun name(func: () -> Unit): String {
    // First, try to get the name using reflection. This works in most cases.
    val nameFromReflection = func::class.qualifiedName?.substringBeforeLast(".<anonymous>")
    if (!nameFromReflection.isNullOrBlank()) {
      return nameFromReflection
    }

    // As a fallback for Kotlin/Native top-level properties, inspect the call stack.
    val stackTrace = Throwable().stackTraceToString().lines()

    // Find the first stack frame that is not part of the logging library itself.
    // This should be the line of code where the logger was created.
    val userCodeFrame = stackTrace.firstOrNull {
        it.contains("kfun:") &&
        !it.contains("io.github.oshai.kotlinlogging.KotlinLogging") &&
        !it.contains("io.github.oshai.kotlinlogging.internal")
    }

    // Extract the class/file name from the stack frame.
    return userCodeFrame?.let {
        val regex = Regex("""at kfun:([^.(<]+)""")
        regex.find(it)?.groupValues?.get(1)
    } ?: "UnknownLogger" // Provide a sensible default if all else fails.
  }
}
