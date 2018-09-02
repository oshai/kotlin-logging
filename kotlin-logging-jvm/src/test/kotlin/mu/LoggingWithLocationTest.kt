package mu

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoggingWithLocationTest {
    private val appenderWithWriter: AppenderWithWriter = AppenderWithWriter("%p %C{1}.%M(%L) - %m%n")

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
        Assert.assertEquals("INFO ClassWithLoggingForLocationTesting.log(7) - test", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testLazyLoggingWithLocation() {
        ClassWithLoggingForLocationTesting().logLazy()
        Assert.assertEquals("INFO ClassWithLoggingForLocationTesting.logLazy(11) - test", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testNullLoggingWithLocation() {
        ClassWithLoggingForLocationTesting().logNull()
        Assert.assertEquals("INFO ClassWithLoggingForLocationTesting.logNull(15) - null", appenderWithWriter.writer.toString().trim())
    }
}


