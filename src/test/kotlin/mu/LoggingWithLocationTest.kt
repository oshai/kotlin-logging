package mu

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.apache.log4j.*
import org.apache.log4j.spi.LoggingEvent
import org.junit.Assert
import org.mockito.Matchers.*
import org.slf4j.LoggerFactory
import java.io.StringWriter
import java.util.*


class LoggingWithLocationTest {
    private var appenders: Vector<Appender>? = null
    private var writer = StringWriter()

    @Before
    fun setupAppender() {
        appenders = Vector<Appender>(2)
        // 1. just a printout appender:
        appenders!!.add(ConsoleAppender(PatternLayout("%p %C{1}.%M(%L) - %m%n")))
        // 2. the appender to test against:
        writer = StringWriter()
        appenders!!.add(WriterAppender(PatternLayout("%p %C{1}.%M(%L) - %m%n"), writer))
        Logger.getRootLogger().addAppender(appenders!![0])
        Logger.getRootLogger().addAppender(appenders!![1])
    }

    @After
    fun removeAppender() {
        Logger.getRootLogger().removeAppender(appenders!![0])
        Logger.getRootLogger().removeAppender(appenders!![1])
    }

    @Test
    fun testMethod() {
        ClassWithLoggingForLocationTesting().log()
        Assert.assertEquals("INFO ClassWithLoggingForLocationTesting.log(51) - test", writer.toString().trim())
    }
}

class ClassWithLoggingForLocationTesting {
//    companion object: KLogging()
    val logger: org.slf4j.Logger = LoggerFactory.getLogger(this.javaClass)
    fun log() {
        logger.info("test")
    }
}
