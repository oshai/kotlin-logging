package mu.internal

import mu.KotlinLoggingConfiguration

interface INativeLogLevel {
    val number: Int
    val name: String
}

sealed class NativeLogLevels : INativeLogLevel {
    fun isLoggingEnabled(message: Any?, config: KotlinLoggingConfiguration, logFunction: (String?) -> Unit) {
        if (this.number >= config.logLevel.number) {
            logFunction(message as String?)
        }
    }
}

object Verbose : NativeLogLevels() {
    override val number = 1
    override val name = "Verbose"
}

object Debug : NativeLogLevels() {
    override val number = 2
    override val name = "Debug"
}

object Info : NativeLogLevels() {
    override val number = 3
    override val name = "Info"
}

object Warn : NativeLogLevels() {
    override val number = 4
    override val name = "Warn"
}

object Error : NativeLogLevels() {
    override val number = 5
    override val name = "Error"
}

object Fatal : NativeLogLevels() {
    override val number = 6
    override val name = "Fatal"
}
