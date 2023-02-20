package io.github.oshai.coroutines

import kotlin.test.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.apache.logging.log4j.*
import org.apache.logging.log4j.core.config.*
import org.junit.jupiter.api.BeforeEach
import org.slf4j.*

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
      withLoggingContextAsync("a" to "b") { assertEquals("b", MDC.get("a")) }

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
      withLoggingContextAsync("a" to "b") { assertEquals("b", MDC.get("a")) }
      assertEquals("c", MDC.get("a"))
    }
    assertNull(MDC.get("a"))
  }

  @Test
  fun `simple nullable pair withLoggingContext`() = runTest {
    assertNull(MDC.get("a"))
    withLoggingContextAsync("a" to null) { assertNull(MDC.get("a")) }
    assertNull(MDC.get("a"))

    MDC.put("a", "b")
    assertEquals("b", MDC.get("a"))
    withLoggingContextAsync("a" to null) { assertEquals("b", MDC.get("a")) }
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
