package mu

import org.junit.Assert.*
import org.junit.Test

val logger = KotlinLogging.logger {  }

class KotlinLoggingTest {

    val loggerInClass = KotlinLogging.logger {  }
    companion object {
        val loggerInCompanion = KotlinLogging.logger {  }
    }

    @Test fun testLoggerName() {
        assertEquals("mu.KotlinLoggingTest", logger.name)
        assertEquals("mu.KotlinLoggingTest", loggerInClass.name)
        assertEquals("mu.KotlinLoggingTest", loggerInCompanion.name)
    }
}
