package io.github.oshai.internal

internal actual object ErrorMessageProducer {
  actual fun getErrorLog(e: Exception): String {
    if (System.getProperties().containsKey("kotlin-logging.throwOnMessageError")) {
      throw e
    } else {
      return "Log message invocation failed: $e"
    }
  }
}
