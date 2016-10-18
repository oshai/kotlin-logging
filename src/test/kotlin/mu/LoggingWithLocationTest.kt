package mu

import org.apache.log4j.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.StringWriter
import java.util.*

class LoggingWithLocationTest {
    private val appenders: Vector<Appender> = Vector<Appender>(2)
    private val writer = StringWriter()

    @Before
    fun setupAppender() {
        appenders.add(ConsoleAppender(PatternLayout("%p %C{1}.%M(%L) - %m%n")))
        appenders.add(WriterAppender(PatternLayout("%p %C{1}.%M(%L) - %m%n"), writer))
        Logger.getRootLogger().addAppender(appenders[0])
        Logger.getRootLogger().addAppender(appenders[1])
    }

    @After
    fun removeAppender() {
        Logger.getRootLogger().removeAppender(appenders[0])
        Logger.getRootLogger().removeAppender(appenders[1])
    }

    @Test
    fun testLoggingWithLocation() {
        ClassWithLoggingForLocationTesting().log()
        Assert.assertEquals("INFO ClassWithLoggingForLocationTesting.log(7) - test", writer.toString().trim())
    }
    @Test
    fun testLazyLoggingWithLocation() {
        ClassWithLoggingForLocationTesting().logLazy()
        Assert.assertEquals("INFO ClassWithLoggingForLocationTesting.logLazy(11) - test", writer.toString().trim())
    }
}


