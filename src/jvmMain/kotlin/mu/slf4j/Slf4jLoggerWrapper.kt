package mu.slf4j

import mu.KLogger
import mu.Marker
import org.slf4j.Logger

internal abstract class Slf4jLoggerWrapper(private val slf4jLogger: Logger) : KLogger {


    override fun getName(): String? {
        return slf4jLogger.name
    }

    override val isTraceEnabled: Boolean
        get() = slf4jLogger.isTraceEnabled

    override fun isTraceEnabled(marker: Marker?): Boolean {
        return slf4jLogger.isTraceEnabled(marker?.toSlf4j())
    }

    override val isDebugEnabled: Boolean
        get() = slf4jLogger.isDebugEnabled

    override fun isDebugEnabled(marker: Marker?): Boolean {
        return slf4jLogger.isDebugEnabled(marker?.toSlf4j())
    }

    override val isInfoEnabled: Boolean
        get() = slf4jLogger.isInfoEnabled

    override fun isInfoEnabled(marker: Marker?): Boolean {
        return slf4jLogger.isInfoEnabled(marker?.toSlf4j())
    }

    override val isWarnEnabled: Boolean
        get() = slf4jLogger.isWarnEnabled

    override fun isWarnEnabled(marker: Marker?): Boolean {
        return slf4jLogger.isWarnEnabled(marker?.toSlf4j())
    }

    override val isErrorEnabled: Boolean
        get() = slf4jLogger.isErrorEnabled

    override fun isErrorEnabled(marker: Marker?): Boolean {
        return slf4jLogger.isErrorEnabled(marker?.toSlf4j())
    }
}
