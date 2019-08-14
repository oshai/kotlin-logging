package mu

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoggingWithLocationTest {
    private val appenderWithWriter: AppenderWithWriter = AppenderWithWriter("%p %C{1}.%M(%L) - %m%n")

    init {
        Configurator.setRootLevel(Level.TRACE)
    }

    @Before
    fun setupAppender() {
        addAppender(appenderWithWriter.appender)
    }

    @After
    fun removeAppender() {
        removeAppender(appenderWithWriter.appender)
    }

    @Test
    fun testLoggingWithLocation() {
        ClassWithLoggingForLocationTesting().log()
        Assert.assertEquals(
            "INFO ClassWithLoggingForLocationTesting.log(7) - test", appenderWithWriter.writer.toString().trim()
        )
    }

    @Test
    fun testLazyLoggingWithLocation() {
        ClassWithLoggingForLocationTesting().logLazy()
        Assert.assertEquals(
            "INFO ClassWithLoggingForLocationTesting.logLazy(11) - test", appenderWithWriter.writer.toString().trim()
        )
    }

    @Test
    fun testNullLoggingWithLocation() {
        ClassWithLoggingForLocationTesting().logNull()
        Assert.assertEquals(
            "INFO ClassWithLoggingForLocationTesting.logNull(15) - null", appenderWithWriter.writer.toString().trim()
        )
    }

    @Test
    fun testNullLoggingWithLocationEntryExit() {
        ClassWithLoggingForLocationTesting().logEntry()
        Assert.assertEquals(
            "TRACE ClassWithLoggingForLocationTesting.logEntry(19) -  entry with (1, 2)" + System.lineSeparator() + "INFO " + "ClassWithLoggingForLocationTesting.logEntry(20) - log entry body" + System.lineSeparator() + "TRACE " + "ClassWithLoggingForLocationTesting.logEntry(21) - exit with ((2, 1))",
            appenderWithWriter.writer.toString().trim()
        )
    }

    @Test
    fun testNullLoggingWithLocationEntryExitOpt() {
        ClassWithLoggingForLocationTesting().logExitOpt()
        Assert.assertEquals(
            "TRACE ClassWithLoggingForLocationTesting.logExitOpt(25) -  entry with (1, 2)" + System.lineSeparator() +
                "INFO ClassWithLoggingForLocationTesting.logExitOpt(26) - log entry body" + System.lineSeparator() +
                "TRACE ClassWithLoggingForLocationTesting.logExitOpt(27) - exit with (null)",
            appenderWithWriter.writer.toString().trim()
        )
    }
}


