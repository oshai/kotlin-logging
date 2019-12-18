package mu.internal

import zfLog.*

internal object ZfLogAppender : IAppender {
    private fun emitLog(level: ZfLogLevels, message: String?) =
        _zf_log_write(level.number, message, "")


    override fun info(message: String?) = emitLog(Info, message)
    override fun error(message: String?) = emitLog(Error, message)
    override fun warn(message: String?) = emitLog(Warn, message)
    override fun debug(message: String?) = emitLog(Debug, message)
    override fun fatal(message: String?) = emitLog(Fatal, message)
}
