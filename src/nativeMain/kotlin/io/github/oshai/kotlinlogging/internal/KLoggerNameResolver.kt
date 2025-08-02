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

    // Find the call to this function as a reliable anchor.
    val resolverCallIndex =
      stackTrace.indexOfFirst {
        it.contains("io.github.oshai.kotlinlogging.internal.KLoggerNameResolver#name")
      }

    // The caller is two frames up from this function in the stack trace.
    val callerFrameIndex = resolverCallIndex + 2
    if (resolverCallIndex != -1 && callerFrameIndex < stackTrace.size) {
      val callerFrame = stackTrace[callerFrameIndex]
      val regex = Regex("""kfun:([^#(<]+)""")
      val fqName = regex.find(callerFrame)?.groupValues?.get(1)?.trim()
      if (fqName != null) {
        return removeKtSuffix(fqName)
      }
    }
    return "UnknownLogger"
  }
}
