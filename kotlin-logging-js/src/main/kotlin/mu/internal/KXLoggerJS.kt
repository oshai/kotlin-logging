package mu.internal

import mu.*
import mu.KotlinLoggingConfiguration.APPENDER
import mu.KotlinLoggingConfiguration.FORMATTER
import mu.KotlinLoggingLevel.DEBUG
import mu.KotlinLoggingLevel.ERROR
import mu.KotlinLoggingLevel.INFO
import mu.KotlinLoggingLevel.TRACE
import mu.KotlinLoggingLevel.WARN

internal class KXLoggerJS(
    private val loggerName: String
) : KLoggerJS(loggerName), KXLogger {
    override fun entry(vararg argArray: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T: Any> exit(result: T): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Throwable> throwing(result: T): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Throwable> catching(result: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
