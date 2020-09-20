package mu

public object KotlinLoggingConfiguration {
    public var LOG_LEVEL: KotlinLoggingLevel = KotlinLoggingLevel.INFO
    public var APPENDER: Appender = ConsoleOutputAppender
    public var FORMATTER: Formatter = DefaultMessageFormatter

}
