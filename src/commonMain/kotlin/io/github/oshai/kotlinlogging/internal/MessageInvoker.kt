package io.github.oshai.kotlinlogging.internal

public fun (() -> Any?).toStringSafe(): String {
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

@Deprecated(
  message = "This is an internal API and should not be used by user code",
  level = DeprecationLevel.HIDDEN,
)
@Suppress("kotlin:S1133")
public fun Any?.castToThrowable(): Throwable? {
  return if (this is Throwable) {
    this
  } else {
    null
  }
}
