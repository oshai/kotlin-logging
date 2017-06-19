package mu.internal

@Suppress("NOTHING_TO_INLINE")
internal inline fun (() -> Any?).toStringSafe(): String {
    try {
        return invoke().toString()
    } catch (e: Exception) {
        return "Log message invocation failed: $e"
    }
}
