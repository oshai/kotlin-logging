package mu

import mu.internal.IAppender
import mu.internal.Info
import mu.internal.ZfLogAppender
import mu.internal.ZfLogLevels

object KotlinLoggingConfiguration {
    var LOG_LEVEL: ZfLogLevels = Info
    var APPENDER: IAppender = ZfLogAppender
}
