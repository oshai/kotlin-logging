package io.github.oshai.kotlinlogging.internal

import kotlin.experimental.ExperimentalNativeApi

internal actual object KLoggerNameResolver {

  private fun removeKtSuffix(fullyQualifiedName: String): String {
    return if (fullyQualifiedName.endsWith("Kt")) {
      fullyQualifiedName.substring(0, fullyQualifiedName.length - 2)
    } else {
      fullyQualifiedName
    }
  }

  @OptIn(ExperimentalNativeApi::class)
  internal actual fun name(func: () -> Unit): String {
    // Primary method: For loggers in classes, reflection returns the FQ name.
    val nameFromReflection = func::class.qualifiedName?.substringBeforeLast(".<anonymous>")
    if (!nameFromReflection.isNullOrBlank()) {
      return nameFromReflection
    }

    // Fallback for top-level loggers: Parse the stack trace string array.
    val stackTrace: Array<String> = Throwable().getStackTrace()

    // Find the first frame outside the logging library's package.
    val callerFrame = stackTrace.firstOrNull { !it.contains("io.github.oshai.kotlinlogging") }

    if (callerFrame != null) {
      // A typical frame string is: " at
      // kfun:io.github.oshai.kotlinlogging.SimpleNativeTestKt.<clinit>()"
      // This regex extracts the fully qualified name.
      val regex = Regex("""kfun:([^#(<]+)""")
      val fqName = regex.find(callerFrame)?.groupValues?.get(1)?.trim()

      return if (fqName != null) {
        removeKtSuffix(fqName)
      } else {
        "UnknownLogger"
      }
    }
    return "UnknownLogger"
  }
}
