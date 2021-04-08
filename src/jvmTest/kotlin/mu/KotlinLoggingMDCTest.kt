package mu

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.slf4j.MDC

class KotlinLoggingMDCTest {
    init {
        Configurator.setRootLevel(Level.TRACE)
    }

    @Test
    fun `simple pair withLoggingContext`() {
        assertNull(MDC.get("a"))
        withLoggingContext("a" to "b") {
            assertEquals("b", MDC.get("a"))
        }
        assertNull(MDC.get("a"))
    }

    @Test
    fun `simple nullable pair withLoggingContext`() {
        assertNull(MDC.get("a"))
        withLoggingContext("a" to null) {
            assertNull(MDC.get("a"))
        }
        assertNull(MDC.get("a"))
    }

    @Test
    fun `simple toString pair withLoggingContext`() {
        assertNull(MDC.get("a"))
        withLoggingContext("a" to 15) {
            assertEquals("15", MDC.get("a"))
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
    fun `multiple nullable pair withLoggingContext`() {
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        MDC.put("e", "f")
        withLoggingContext("a" to "b", "c" to null, "e" to null) {
            assertEquals("b", MDC.get("a"))
            assertNull(MDC.get("c"))
            assertEquals("f", MDC.get("e"))
        }
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        assertEquals("f", MDC.get("e"))
    }

    @Test
    fun `multiple toString pair withLoggingContext`() {
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        withLoggingContext("a" to true, "c" to ToStringObject()) {
            assertEquals("true", MDC.get("a"))
            assertEquals("string", MDC.get("c"))
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

private class ToStringObject {
    override fun toString(): String {
        return "string"
    }
}
