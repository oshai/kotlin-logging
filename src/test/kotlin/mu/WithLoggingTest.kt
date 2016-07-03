package mu

import org.junit.Assert.*
import org.junit.Test

class ClassWithNamedLogging {
    companion object: Any(), HasLogging by WithNamedLogging("mu.ClassWithNamedLogging")
    fun test() {
        logger.info{"hi33"}
        logger.info("hi333")
    }
}
class ClassWithLogging {
    companion object: WithLogging()
    fun test() {
        logger.info{"hi11"}
        logger.info("hi111")
    }
}
class ChildClassWithLogging {
    companion object: WithLogging()
    fun test() {
        ClassWithLogging.logger.info{"hi22"}
        ClassWithLogging.logger.info("hi222")
    }
}
class WithLoggingTest {
    @Test
    fun getLogger() {
        ClassWithLogging().test()
        ChildClassWithLogging().test()
        ClassWithNamedLogging().test()
    }

}
