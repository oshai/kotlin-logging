package mu

object KotlinLoggingConfiguration {
    var LOG_LEVEL = KotlinLoggingLevel.INFO
    var APPENDER: Appender = ConsoleOutputAppender
    var FORMATTER: Formatter = DefaultMessageFormatter

}
