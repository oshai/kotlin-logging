package mu.internal

import mu.LoggingCallback
import mu.Marker

internal class JvmLoggingCallback(
    private val logMethod: (marker: Marker?, message: String, t: Throwable?) -> Unit
) : LoggingCallback {
    override fun invoke(message: String) {
        invoke(null, null, message)
    }

    override fun invoke(t: Throwable?, message: String) {
        invoke(null, t, message)
    }

    override fun invoke(marker: Marker?, message: String) {
        invoke(marker, null, message)
    }

    override fun invoke(marker: Marker?, t: Throwable?, message: String) {
        logMethod(marker, message, t)
    }
}
