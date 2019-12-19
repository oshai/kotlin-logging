package mu.internal

import mu.KLogger
import mu.KotlinLoggingConfiguration

import mu.Marker

internal class NativeLogger(
    private val loggerName: String,
    private val config: KotlinLoggingConfiguration
) : KLogger {

    override fun entry(vararg argArray: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> exit(result: T): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Throwable> throwing(throwable: T): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Throwable> catching(throwable: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun trace(msg: () -> Any?) = Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun debug(msg: () -> Any?) = Debug.isLoggingEnabled(msg(), config) { config.appender.debug(it) }

    override fun info(msg: () -> Any?) = Info.isLoggingEnabled(msg(), config) { config.appender.info(it) }

    override fun warn(msg: () -> Any?) = Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun error(msg: () -> Any?) = Error.isLoggingEnabled(msg(), config) { config.appender.error(it) }

    // With Throwable
    override fun trace(t: Throwable?, msg: () -> Any?) =
        Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun debug(t: Throwable?, msg: () -> Any?) =
        Debug.isLoggingEnabled(msg(), config) { config.appender.debug(it) }

    override fun info(t: Throwable?, msg: () -> Any?) =
        Info.isLoggingEnabled(msg(), config) { config.appender.info(it) }

    override fun warn(t: Throwable?, msg: () -> Any?) =
        Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun error(t: Throwable?, msg: () -> Any?) =
        Error.isLoggingEnabled(msg(), config) { config.appender.error(it) }
    // End Throwable

    // With Marker
    override fun trace(marker: Marker?, msg: () -> Any?) =
        Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun debug(marker: Marker?, msg: () -> Any?) =
        Debug.isLoggingEnabled(msg(), config) { config.appender.debug(it) }

    override fun info(marker: Marker?, msg: () -> Any?) =
        Info.isLoggingEnabled(msg(), config) { config.appender.info(it) }

    override fun warn(marker: Marker?, msg: () -> Any?) =
        Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun error(marker: Marker?, msg: () -> Any?) =
        Error.isLoggingEnabled(msg(), config) { config.appender.error(it) }
    // End Marker

    // With Marker & Throwable
    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        Debug.isLoggingEnabled(msg(), config) { config.appender.debug(it) }

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        Info.isLoggingEnabled(msg(), config) { config.appender.info(it) }

    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        Warn.isLoggingEnabled(msg(), config) { config.appender.warn(it) }

    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) =
        Error.isLoggingEnabled(msg(), config) { config.appender.error(it) }
    // End Marker && Throwable
}
