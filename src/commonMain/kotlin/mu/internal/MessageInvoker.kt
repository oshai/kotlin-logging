package mu.internal

@Suppress("NOTHING_TO_INLINE")
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
