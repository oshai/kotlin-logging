package mu

import org.junit.Assert.*
import org.junit.Test

class ClassWithLogging {
    companion object: KLogging()
    fun test() {
        logger.info{"test ClassWithLogging"}
    }
}
class ClassHasLogging: KLoggable {
    override val logger = logger()
    fun test() {
        logger.info{"test ClassHasLogging"}
    }
}
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

    @Test
    fun testNames() {
        assertEquals("mu.ClassWithLogging", ClassWithLogging.logger.name)
        assertEquals("mu.ChildClassWithLogging", ChildClassWithLogging.logger.name)
        assertEquals("mu.ClassWithNamedLogging", ClassWithNamedLogging.logger.name)
        assertEquals("mu.ClassHasLogging", ClassHasLogging().logger.name)
        assertEquals("mu.CompanionHasLogging", CompanionHasLogging.logger.name)
    }

    @Test
    fun testMessages() {
        ClassWithLogging().test()
        ChildClassWithLogging().test()
        ClassWithNamedLogging().test()
        ClassHasLogging().test()
        CompanionHasLogging().test()
    }

}
