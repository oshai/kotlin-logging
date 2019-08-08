package mu

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.slf4j.MDC

class KotlinLoggingMDCTest {

    @Test
    fun `simple pair withLoggingContext`() {
        assertNull(MDC.get("a"))
        withLoggingContext("a" to "b") {
            assertEquals("b", MDC.get("a"))
        }
        assertNull(MDC.get("a"))
    }

    @Test
    fun `multiple pair withLoggingContext`() {
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        withLoggingContext("a" to "b", "c" to "d") {
            assertEquals("b", MDC.get("a"))
            assertEquals("d", MDC.get("c"))
        }
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
    }

    @Test
    fun `map withLoggingContext`() {
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        withLoggingContext(mapOf("a" to "b", "c" to "d")) {
            assertEquals("b", MDC.get("a"))
            assertEquals("d", MDC.get("c"))
        }
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
    }
}
