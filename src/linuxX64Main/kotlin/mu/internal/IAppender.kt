package mu.internal

import mu.KotlinLoggingConfiguration

interface IAppender {
    fun debug(config: KotlinLoggingConfiguration, message: String?)
    fun info(config: KotlinLoggingConfiguration, message: String?)
    fun warn(config: KotlinLoggingConfiguration, message: String?)
    fun error(config: KotlinLoggingConfiguration, message: String?)
    fun fatal(config: KotlinLoggingConfiguration, message: String?)
}
