package mu

object KotlinLoggingConfiguration {
    var LOG_LEVEL = KotlinLoggingLevel.INFO
    var OUTPUT_PIPES: OutputPipes = ConsoleOutputPipes
    var MESSAGE_FORMATTER: MessageFormatter = DefaultMessageFormatter

}