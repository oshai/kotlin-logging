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
    @Test
    fun testXLoggingWithLocationNull() {
        ClassWithXLoggingForLocationTesting().logNull()
        Assert.assertEquals("TRACE ClassWithXLoggingForLocationTesting.logNull(29) -  entry with ([3])\n" +
                "INFO ClassWithXLoggingForLocationTesting.logNull(30) - null\n" +
                "TRACE ClassWithXLoggingForLocationTesting.logNull(31) - exit", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testXLoggingWithLocation() {
        ClassWithXLoggingForLocationTesting().log()
        Assert.assertEquals("TRACE ClassWithXLoggingForLocationTesting.log(7) -  entry with ([1])\n" +
                "INFO ClassWithXLoggingForLocationTesting.log(8) - test\n" +
                "TRACE ClassWithXLoggingForLocationTesting.log(9) - exit", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testXLoggingWithLocationException() {
        ClassWithXLoggingForLocationTesting().logException()
        Assert.assertTrue(appenderWithWriter.writer.toString().trim().startsWith("TRACE ClassWithXLoggingForLocationTesting.logException(19) -  entry with ([3])\n" +
                "ERROR ClassWithXLoggingForLocationTesting.logException(21) - throwing\n" +
                "java.lang.RuntimeException: sample exception"))
    }
    @Test
    fun testXLoggingWithLocationLazy() {
        ClassWithXLoggingForLocationTesting().logLazy()
        Assert.assertEquals("TRACE ClassWithXLoggingForLocationTesting.logLazy(13) -  entry with ([2])\n" +
                "INFO ClassWithXLoggingForLocationTesting.logLazy(14) - test\n" +
                "TRACE ClassWithXLoggingForLocationTesting.logLazy(15) - exit", appenderWithWriter.writer.toString().trim())
    }
}


