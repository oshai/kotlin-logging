package mu

import org.junit.Assert.*
import org.junit.Test

class ClassWithLogging {
    companion object: WithLogging()
    fun log(message: String) = logger.info(message)
    fun log(message: () -> String) = logger.info(message)
}
class ChildClassWithLogging {
    companion object: WithLogging()
    fun log(message: String) = logger.info(message)
}
class WithLoggingTest {
    @Test
    fun getLogger() {
        ClassWithLogging().log("hi1")
        ClassWithLogging().log{"hi2"}
        ChildClassWithLogging().log("hi3")
    }

}