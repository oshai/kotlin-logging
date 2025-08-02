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

    // Find the call to the logger factory as a reliable anchor.
    val loggerFactoryCallIndex =
      stackTrace.indexOfFirst { it.contains("io.github.oshai.kotlinlogging.KotlinLogging#logger") }

    // The caller is the next frame in the stack trace.
    if (loggerFactoryCallIndex != -1 && loggerFactoryCallIndex + 1 < stackTrace.size) {
      val callerFrame = stackTrace[loggerFactoryCallIndex + 1]

      // This regex is tailored to the format from the diagnostic log:
      // "kfun:io.github.oshai.kotlinlogging.SimpleTest#<init>()"
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
