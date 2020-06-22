package mu

import kotlin.native.concurrent.AtomicReference

object KotlinLoggingConfiguration {
    val logLevel: AtomicReference<KotlinLoggingLevel> = AtomicReference(KotlinLoggingLevel.INFO)
    val appender: AtomicReference<Appender> = AtomicReference(ConsoleOutputAppender)
    val formatter: AtomicReference<Formatter> = AtomicReference(DefaultMessageFormatter)
}
