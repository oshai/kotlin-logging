package mu.internal

import org.junit.Test
import kotlin.test.assertEquals

class MessageInvokerJavaTest {

    @Test
    fun toStringSafeChecks() {
        assertEquals("hi", { "hi" }.toStringSafe())
    }

    @Test
    fun toStringSafeChecksThrowException() {
        assertEquals("Log message invocation failed: java.lang.Exception: hi", { throw Exception("hi") }.toStringSafe())
    }

    @Test(expected = Exception::class)
    fun toStringSafeChecksThrowExceptionWithSystemProperty() {
        System.setProperty("kotlin-logging.throwOnMessageError", "")
        try {
            { throw Exception("hi") }.toStringSafe()
        } finally {
          System.clearProperty("kotlin-logging.throwOnMessageError")
        }
    }
}
