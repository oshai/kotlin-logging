package mu

object KotlinLoggingConfiguration {
    var LOG_LEVEL = KotlinLoggingLevel.INFO
    var APPENDER: OutputPipes = ConsoleOutputPipes
    var FORMATTER: MessageFormatter = DefaultMessageFormatter

}