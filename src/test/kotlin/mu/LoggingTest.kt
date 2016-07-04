package mu

import org.junit.Assert.*
import org.junit.Test

class ClassWithLogging {
    companion object: WithLogging()
    fun test() {
        logger.info{"test ClassWithLogging"}
    }
}
class ClassHasLogging: HasLogging {
    override val logger = logger()
    fun test() {
        logger.info{"test ClassHasLogging"}
    }
}
class ClassWithNamedLogging {
    companion object: Any(), HasLogging by WithNamedLogging("mu.ClassWithNamedLogging")
    fun test() {
        logger.info{"test ClassWithNamedLogging"}
    }
}
class ChildClassWithLogging {
    companion object: WithLogging()
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
    }

    @Test
    fun testMessages() {
        ClassWithLogging().test()
        ChildClassWithLogging().test()
        ClassWithNamedLogging().test()
        ClassHasLogging().test()
    }

}
