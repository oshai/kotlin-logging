package io.github.oshai.internal

internal inline fun (() -> Any?).toStringSafe(): String {
  return try {
    invoke().toString()
  } catch (e: Exception) {
    ErrorMessageProducer.getErrorLog(e)
  }
}

internal expect object ErrorMessageProducer {
  fun getErrorLog(e: Exception): String
}

internal object DefaultErrorMessageProducer {
  fun getErrorLog(e: Exception): String = "Log message invocation failed: $e"
}
