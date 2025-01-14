package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder

public fun KLogger.entryWithCompilerData(
  compilerData: KLoggingEventBuilder.InternalCompilerData? = null,
  vararg arguments: Any?,
): Unit = atTrace {
  message = "entry(${arguments.joinToString()})"
  internalCompilerData = compilerData
}

public fun KLogger.exitWithCompilerData(
  compilerData: KLoggingEventBuilder.InternalCompilerData? = null
): Unit = atTrace {
  message = "exit"
  internalCompilerData = compilerData
}

public fun <T> KLogger.exitWithCompilerData(
  compilerData: KLoggingEventBuilder.InternalCompilerData? = null,
  result: T,
): T where T : Any? {
  atTrace {
    message = "exit($result)"
    internalCompilerData = compilerData
  }
  return result
}

public fun <T> KLogger.throwingWithCompilerData(
  compilerData: KLoggingEventBuilder.InternalCompilerData? = null,
  throwable: T,
): T where T : Throwable {
  atError {
    cause = throwable
    message = "throwing($throwable)"
    internalCompilerData = compilerData
  }
  return throwable
}

public fun <T> KLogger.catchingWithCompilerData(
  compilerData: KLoggingEventBuilder.InternalCompilerData? = null,
  throwable: T,
) where T : Throwable {
  atError {
    cause = throwable
    message = "catching($throwable)"
    internalCompilerData = compilerData
  }
}
