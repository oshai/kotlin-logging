package mu

import org.junit.Assert.*
import org.junit.Test

private val logger = KotlinLogging.logger {  }

class ForKotlinLoggingTest {
    val loggerInClass = KotlinLogging.logger {  }
    companion object {
        val loggerInCompanion = KotlinLogging.logger {  }
    }
}
class KotlinLoggingTest {

    @Test fun testLoggerName() {
        assertEquals("mu.KotlinLoggingTest", logger.name)
        assertEquals("mu.ForKotlinLoggingTest", ForKotlinLoggingTest().loggerInClass.name)
        assertEquals("mu.ForKotlinLoggingTest", ForKotlinLoggingTest.loggerInCompanion.name)
    }
}
