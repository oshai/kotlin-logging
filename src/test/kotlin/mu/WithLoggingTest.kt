package mu

import org.junit.Assert.*
import org.junit.Test

class ClassWithLogging {
    companion object: Any(), HasLogging by WithLogging()
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
    }

}
