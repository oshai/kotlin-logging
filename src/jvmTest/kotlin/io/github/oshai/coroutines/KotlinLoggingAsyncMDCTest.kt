package io.github.oshai.coroutines


/**
 * ## Example
 *
 * Add a wrapper to a code block so that the SLF4J [MDC](https://logback.qos.ch/manual/mdc.html) uses a specific context
 * for that block of suspending code with an option to avoid restoring the context after the code block has executed.
 *
 * ```kotlin
 * withLoggingContextAsync("userId" to "A_USER_ID") {
 *     // The MDC context will contain the mapping of "userId"=>"A_USER_ID"
 *     // during this log statement.
 *     logger.info { "..." }
 * }
 * // The block will restore The MDC context so that it no longer contains
 * // the mapping of "userId"=>"A_USER_ID"
 * withLoggingContextAsync("userId" to "ANOTHER_USER_ID", restorePrevious = false) {
 *     logger.info { "..." }
 * }
 * // The MDC context will retain the mapping of "userId"=>"ANOTHER_USER_ID",
 * // as the previous context restoration was disabled.
 * ```
 */



import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.apache.logging.log4j.*
import org.apache.logging.log4j.core.config.*
import org.junit.jupiter.api.BeforeEach
import org.slf4j.*
import kotlin.test.*
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class KotlinLoggingAsyncMDCTest {
    init {
        Configurator.setRootLevel(Level.TRACE)
    }

    @BeforeEach
    fun beforeEach() {
        MDC.clear()
    }

    @Test
    fun `simple pair withLoggingContext`() = runTest {
        assertNull(MDC.get("a"))

        withLoggingContextAsync("a" to "c") {

            withLoggingContextAsync("a" to "b") {
                assertEquals("b", MDC.get("a"))
            }

            assertEquals("c", MDC.get("a"))
        }
        assertNull(MDC.get("a"))
    }

    @Test
    fun `simple pair withLoggingContext (restorePrevious=false)`() = runTest {
        withLoggingContextAsync("a" to "c") {
            withLoggingContextAsync("a" to "b", restorePrevious = false) {
                assertEquals("b", MDC.get("a"))
            }
            assertNull(MDC.get("a"))
        }
        assertNull(MDC.get("a"))

        withLoggingContextAsync("a" to "c", restorePrevious = false) {
            withLoggingContextAsync("a" to "b") {
                assertEquals("b", MDC.get("a"))
            }
            assertEquals("c", MDC.get("a"))
        }
        assertNull(MDC.get("a"))
    }

    @Test
    fun `simple nullable pair withLoggingContext`() = runTest {
        assertNull(MDC.get("a"))
        withLoggingContextAsync("a" to null) {
            assertNull(MDC.get("a"))
        }
        assertNull(MDC.get("a"))

        MDC.put("a", "b")
        assertEquals("b", MDC.get("a"))
        withLoggingContextAsync("a" to null) {
            assertEquals("b", MDC.get("a"))
        }
        assertEquals("b", MDC.get("a"))
    }

    @Test
    fun `multiple pair withLoggingContext`() = runTest {
        MDC.put("f", "g")

        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        assertNull(MDC.get("e"))
        assertEquals("g", MDC.get("f"))

        withLoggingContextAsync("a" to "h", "c" to "i") {
            assertEquals("h", MDC.get("a"))
            assertEquals("i", MDC.get("c"))
            assertNull(MDC.get("e"))
            assertEquals("g", MDC.get("f"))

            withLoggingContextAsync("a" to "b", "c" to "d", "e" to null, "f" to null) {
                assertEquals("b", MDC.get("a"))
                assertEquals("d", MDC.get("c"))
                assertNull(MDC.get("e"))
                assertEquals("g", MDC.get("f"))
            }
            assertEquals("h", MDC.get("a"))
            assertEquals("i", MDC.get("c"))
            assertNull(MDC.get("e"))
            assertEquals("g", MDC.get("f"))
        }
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        assertNull(MDC.get("e"))
        assertEquals("g", MDC.get("f"))
    }

    @Test
    fun `multiple pair withLoggingContext (restorePrevious=false)`() = runTest {
        MDC.put("f", "g")

        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        assertNull(MDC.get("e"))
        assertEquals("g", MDC.get("f"))

        withLoggingContextAsync(
            "a" to "b",
            "c" to "d",
            "e" to null,
            "f" to null,
            restorePrevious = false
        ) {
            assertEquals("b", MDC.get("a"))
            assertEquals("d", MDC.get("c"))
            assertNull(MDC.get("e"))
            assertEquals("g", MDC.get("f"))
        }
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        assertNull(MDC.get("e"))
        assertEquals("g", MDC.get("f"))
    }

    @Test
    fun `map withLoggingContext`() = runTest {
        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        assertNull(MDC.get("e"))
        assertNull(MDC.get("f"))
        assertNull(MDC.get("k"))

        MDC.put("e", "g")
        MDC.put("k", "l")

        withLoggingContextAsync(mapOf("a" to "b", "c" to "d", "e" to null, "f" to "h")) {
            assertEquals("b", MDC.get("a"))
            assertEquals("d", MDC.get("c"))
            assertEquals("g", MDC.get("e"))
            assertEquals("h", MDC.get("f"))
            assertEquals("l", MDC.get("k"))

            withLoggingContextAsync(mapOf("a" to "b", "e" to "i", "f" to "j")) {
                assertEquals("b", MDC.get("a"))
                assertEquals("d", MDC.get("c"))
                assertEquals("i", MDC.get("e"))
                assertEquals("j", MDC.get("f"))
                assertEquals("l", MDC.get("k"))
            }

            assertEquals("b", MDC.get("a"))
            assertEquals("d", MDC.get("c"))
            assertEquals("g", MDC.get("e"))
            assertEquals("h", MDC.get("f"))
            assertEquals("l", MDC.get("k"))
        }

        assertNull(MDC.get("a"))
        assertNull(MDC.get("c"))
        assertEquals("g", MDC.get("e"))
        assertNull(MDC.get("f"))
        assertEquals("l", MDC.get("k"))
    }
}
