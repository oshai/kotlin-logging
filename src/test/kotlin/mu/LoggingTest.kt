package mu

import org.apache.log4j.*
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.StringWriter

class ClassWithLogging {
    companion object: KLogging()
    fun test() {
        logger.info{"test ClassWithLogging"}
    }
    fun testThrowable() {
        val ex = Throwable()
        logger.trace(ex){"test ChildClassWithLogging"}
    }
}
open class ClassHasLogging: KLoggable {
    override val logger = logger()
    fun test() {
        logger.info{"test ClassHasLogging"}
    }
}

/**
 * This class demonstrates the disadvantage of inheriting KLoggable in a class instead of companion object
 * the logger name will be of the sub-class
 * ie: it is not possible to log with parent class name this way
 */
class ClassInheritLogging: ClassHasLogging()

class ClassWithNamedLogging {
    companion object: Any(), KLoggable by NamedKLogging("mu.ClassWithNamedLogging")
    fun test() {
        logger.info{"test ClassWithNamedLogging"}
    }
}
class CompanionHasLogging {
    companion object: Any(), KLoggable {
        override val logger = logger()
    }
    fun test() {
        logger.info{"test CompanionHasLogging"}
    }
}
class ChildClassWithLogging {
    companion object: KLogging()
    fun test() {
        logger.info{"test ChildClassWithLogging"}
    }
}
class LoggingTest {
    private lateinit var appenderWithWriter: AppenderWithWriter

    @Before
    fun setupAppender() {
        appenderWithWriter = AppenderWithWriter()
        Logger.getRootLogger().addAppender(appenderWithWriter.appender)
    }

    @After
    fun removeAppender() {
        Logger.getRootLogger().removeAppender(appenderWithWriter.appender)
    }

    @Test
    fun testMessages1() {
        ClassWithLogging().apply {
            test()
            testThrowable()
        }
        val lines = appenderWithWriter.writer.toString().trim().replace("\r", "\n").replace("\n\n", "\n").split("\n")
        Assert.assertEquals("INFO  mu.ClassWithLogging  - test ClassWithLogging", lines[0].trim())
        Assert.assertEquals("TRACE mu.ClassWithLogging  - test ChildClassWithLogging", lines[1].trim())
        Assert.assertEquals("java.lang.Throwable", lines[2].trim())
        Assert.assertTrue(lines[3].trim().startsWith("at mu.ClassWithLogging.testThrowable("))
    }
    @Test
    fun testMessages2() {
        ClassInheritLogging().test()
        Assert.assertEquals("INFO  mu.ClassInheritLogging  - test ClassHasLogging", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testMessages3() {
        ChildClassWithLogging().test()
        Assert.assertEquals("INFO  mu.ChildClassWithLogging  - test ChildClassWithLogging", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testMessages4() {
        ClassWithNamedLogging().test()
        Assert.assertEquals("INFO  mu.ClassWithNamedLogging  - test ClassWithNamedLogging", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testMessages5() {
        ClassHasLogging().test()
        Assert.assertEquals("INFO  mu.ClassHasLogging  - test ClassHasLogging", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun testMessages6() {
        CompanionHasLogging().test()
        Assert.assertEquals("INFO  mu.CompanionHasLogging  - test CompanionHasLogging", appenderWithWriter.writer.toString().trim())
    }
}
class LoggingNameTest {
    @Test
    fun testNames() {
        assertEquals("mu.ClassWithLogging", ClassWithLogging.logger.name)
        assertEquals("mu.ClassInheritLogging", ClassInheritLogging().logger.name)
        assertEquals("mu.ChildClassWithLogging", ChildClassWithLogging.logger.name)
        assertEquals("mu.ClassWithNamedLogging", ClassWithNamedLogging.logger.name)
        assertEquals("mu.ClassHasLogging", ClassHasLogging().logger.name)
        assertEquals("mu.CompanionHasLogging", CompanionHasLogging.logger.name)
    }
}
data class AppenderWithWriter(val writer: StringWriter = StringWriter(), val appender: Appender =  WriterAppender(PatternLayout("%-5p %c %x - %m%n"), writer)) {

}
