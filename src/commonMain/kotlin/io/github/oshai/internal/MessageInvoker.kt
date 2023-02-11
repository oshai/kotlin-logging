package io.github.oshai.internal

internal inline fun (() -> Any?).toStringSafe(): String {
  return try {
    invoke().toString()
  } catch (e: Exception) {
    ErrorMessageProducer.getErrorLog(e)
  }
}

public expect object ErrorMessageProducer {
  public fun getErrorLog(e: Exception): String
}

public object DefaultErrorMessageProducer {
  public fun getErrorLog(e: Exception): String = "Log message invocation failed: $e"
}
