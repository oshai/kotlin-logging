package mu.internal

import mu.KotlinLoggingConfiguration.LOG_LEVEL
import kotlin.reflect.KSuspendFunction1

interface IZfLogLevel {
    val number: Int
}

sealed class ZfLogLevels : IZfLogLevel {
    fun isLoggingEnabled(message: Any?, logFunction: (String?) -> Unit) {
        if (this.number >= LOG_LEVEL.number) {
            logFunction(message as String?)
        }
        val x = ZfLogAppender::info
    }
}

object Verbose : ZfLogLevels() {
    override val number = 1
}

object Debug : ZfLogLevels() {
    override val number = 2
}

object Info : ZfLogLevels() {
    override val number = 3
}

object Warn : ZfLogLevels() {
    override val number = 4
}

object Error : ZfLogLevels() {
    override val number = 5
}

object Fatal : ZfLogLevels() {
    override val number = 6
}
