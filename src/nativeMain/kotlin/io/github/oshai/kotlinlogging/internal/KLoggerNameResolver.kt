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

    // START: Diagnostic code
    //    println("--- KOTLIN-LOGGING DIAGNOSTIC: STACK TRACE ---")
    //    stackTrace.forEach { println(it) }
    //    println("--- END DIAGNOSTIC ---")
    // END: Diagnostic code

    // Find the first frame outside of the logging framework's internal machinery.
    val callerFrame =
      stackTrace.firstOrNull {
        !it.contains("kfun:kotlin.Throwable") &&
          !it.contains("io.github.oshai.kotlinlogging.internal") &&
          !it.contains("io.github.oshai.kotlinlogging.KotlinLogging") &&
          !it.contains("kfun:io.github.oshai.kotlinlogging.\$init_global#internal") &&
          !it.contains("CallInitGlobalPossiblyLock") &&
          !it.contains("kfun:io.github.oshai.kotlinlogging.<get-logger>#internal")
      }

    if (callerFrame != null) {
      val regex = Regex("""kfun:([^#(<]+)""")
      val fqName = regex.find(callerFrame)?.groupValues?.get(1)?.trim()
      if (fqName != null) {
        return removeKtSuffix(fqName)
      }
    }
    return "UnknownLogger"
  }
}
