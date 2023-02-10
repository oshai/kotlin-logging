package io.github.oshai.internal

public actual object ErrorMessageProducer {
    public actual fun getErrorLog(e: Exception): String = "Log message invocation failed: $e"
}
