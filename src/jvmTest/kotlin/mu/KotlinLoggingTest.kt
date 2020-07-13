package mu

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory


class ForKotlinLoggingTest {
    val loggerInClass = KotlinLogging.logger { }

    companion object {
        val loggerInCompanion = KotlinLogging.logger { }
    }
}

class KotlinLoggingTest {

    @Before
    fun resetState() {
        KotlinLogging.kLoggerFactory = KotlinLogging.defaultLoggerFactory
    }
    @Test
    fun testLoggerName() {
        val logger = KotlinLogging.logger { }
        val loggerFromSlf4j = KotlinLogging.logger(LoggerFactory.getLogger("mu.slf4jLogger"))
        val loggerFromSlf4jExtension = LoggerFactory.getLogger("mu.slf4jLoggerExtension").toKLogger()

        assertEquals("mu.KotlinLoggingTest", logger.name)
        assertEquals("mu.ForKotlinLoggingTest", ForKotlinLoggingTest().loggerInClass.name)
        assertEquals("mu.ForKotlinLoggingTest", ForKotlinLoggingTest.loggerInCompanion.name)
        assertEquals("mu.slf4jLogger", loggerFromSlf4j.name)
        assertEquals("mu.slf4jLoggerExtension", loggerFromSlf4jExtension.name)
    }

    @Test
    fun testNonDefaultLoggerFactory() {
        KotlinLogging.kLoggerFactory = TestLoggerFactory()

        assertEquals("custom-log-name:passed", KotlinLogging.logger("passed").name)
        assertEquals("non-default-function", KotlinLogging.logger { }.name)
        assertEquals("non-default-function", ForKotlinLoggingTest().loggerInClass.name)
    }
}

private class TestLoggerFactory : KLoggerFactory {
    override fun logger(name: String): KLogger = MockedKLogger("custom-log-name:$name")

    override fun logger(func: () -> Unit): KLogger = MockedKLogger("non-default-function")

    override fun logger(loggable: KLoggable): KLogger = MockedKLogger("loggable-based")

}
