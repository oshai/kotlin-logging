package mu

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.appender.WriterAppender
import org.apache.logging.log4j.core.layout.PatternLayout
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
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
        logger.trace(ex){"test ClassWithLogging"}
    }
    fun testNullableThrowable() {
        val ex:Throwable? = null
        logger.trace(ex){"test ClassWithLogging"}
    }
    fun testMarker() {
        val marker = KMarkerFactory.getMarker("MARKER")
        logger.trace(marker){"test ClassWithLogging"}
    }
    fun testMarkerThrowable() {
        val marker = KMarkerFactory.getMarker("MARKER")
        val ex = Throwable()
        logger.trace(marker, ex){"test ClassWithLogging"}
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

data class ClassWithIncorrectToString(val someVariable : String? = null){
    override fun toString(): String {
        return someVariable!!.toString()
    }
}

class LambdaRaisesError {
    companion object: KLogging()
    fun test() {
        val problematicClass = ClassWithIncorrectToString()

        logger.info{" $problematicClass"}
    }
}

fun newPatternLayout(pattern: String): PatternLayout = PatternLayout.newBuilder().withPattern(pattern).build()

fun addAppender(appender: Appender) {
    val context = LogManager.getContext(false) as org.apache.logging.log4j.core.LoggerContext
    context.configuration.rootLogger.addAppender(appender, null, null)
}

fun removeAppender(appender: Appender) {
    val context = LogManager.getContext(false) as org.apache.logging.log4j.core.LoggerContext
    context.configuration.rootLogger.removeAppender(appender.name)
}

class LoggingTest {
    private val appenderWithWriter: AppenderWithWriter = AppenderWithWriter()

    @Before
    fun setupAppender() {
        addAppender(appenderWithWriter.appender)
    }

    @After
    fun removeAppender() {
        removeAppender(appenderWithWriter.appender)
    }

    @Test
    fun testMessages0() {
        ClassWithLogging().apply {
            test()
            testThrowable()
            testNullableThrowable()
        }
        val lines = appenderWithWriter.writer.toString().trim().replace("\r", "\n").replace("\n\n", "\n").split("\n")
        Assert.assertEquals("INFO  mu.ClassWithLogging  - test ClassWithLogging", lines[0].trim())
        Assert.assertEquals("TRACE mu.ClassWithLogging  - test ClassWithLogging", lines[1].trim())
        Assert.assertEquals("java.lang.Throwable: null", lines[2].trim())
        Assert.assertTrue(lines[3].trim().startsWith("at mu.ClassWithLogging.testThrowable("))
        Assert.assertEquals("TRACE mu.ClassWithLogging  - test ClassWithLogging", lines[lines.size-1].trim())
    }
    @Test
    fun testMessages1() {
        ClassWithLogging().apply {
            testMarker()
            testMarkerThrowable()
        }
        val lines = appenderWithWriter.writer.toString().trim().replace("\r", "\n").replace("\n\n", "\n").split("\n")
        Assert.assertEquals("TRACE mu.ClassWithLogging  - test ClassWithLogging", lines[0].trim())
        Assert.assertEquals("TRACE mu.ClassWithLogging  - test ClassWithLogging", lines[1].trim())
        Assert.assertEquals("java.lang.Throwable: null", lines[2].trim())
        Assert.assertTrue(lines[3].trim().startsWith("at mu.ClassWithLogging.testMarkerThrowable("))
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

    @Test
    fun shouldNotFailForFailingLambdas(){
        LambdaRaisesError().test()
        Assert.assertEquals("INFO  mu.LambdaRaisesError  - Log message invocation failed: kotlin.KotlinNullPointerException", appenderWithWriter.writer.toString().trim())
    }
    @Test
    fun `check underlyingLogger property`() {
        Assert.assertEquals("mu.ClassHasLogging", ClassHasLogging().logger.underlyingLogger.name)
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
data class AppenderWithWriter(
        val pattern: String = "%-5p %c  - %m%n",
        val writer: StringWriter = StringWriter(),
        val appender: Appender = WriterAppender.createAppender(newPatternLayout(pattern), null, writer, "writer", false, true)
)
