package io.github.oshai.kotlinlogging

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.slf4j.MDC

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KotlinLoggingMDCTest {
  init {
    Configurator.setRootLevel(Level.TRACE)
  }

  @BeforeEach
  fun setUp() {
    MDC.clear()
  }

  @Test
  fun `simple pair withLoggingContext`() {
    assertNull(MDC.get("a"))

    withLoggingContext("a" to "c") {
      withLoggingContext("a" to "b") { assertEquals("b", MDC.get("a")) }

      assertEquals("c", MDC.get("a"))
    }
    assertNull(MDC.get("a"))
  }

  @Test
  fun `simple pair withLoggingContext (restorePrevious=false)`() {
    withLoggingContext("a" to "c") {
      withLoggingContext("a" to "b", restorePrevious = false) { assertEquals("b", MDC.get("a")) }
      assertNull(MDC.get("a"))
    }
    assertNull(MDC.get("a"))

    withLoggingContext("a" to "c", restorePrevious = false) {
      withLoggingContext("a" to "b") { assertEquals("b", MDC.get("a")) }
      assertEquals("c", MDC.get("a"))
    }
    assertNull(MDC.get("a"))
  }

  @Test
  fun `simple nullable pair withLoggingContext`() {
    assertNull(MDC.get("a"))
    withLoggingContext("a" to null) { assertNull(MDC.get("a")) }
    assertNull(MDC.get("a"))

    MDC.put("a", "b")
    assertEquals("b", MDC.get("a"))
    withLoggingContext("a" to null) { assertEquals("b", MDC.get("a")) }
    assertEquals("b", MDC.get("a"))
  }

  @Test
  fun `multiple pair withLoggingContext`() {
    MDC.put("f", "g")

    assertAll(
      { assertNull(MDC.get("a")) },
      { assertNull(MDC.get("c")) },
      { assertNull(MDC.get("e")) },
      { assertEquals("g", MDC.get("f")) },
    )

    withLoggingContext("a" to "h", "c" to "i") {
      assertAll(
        { assertEquals("h", MDC.get("a")) },
        { assertEquals("i", MDC.get("c")) },
        { assertNull(MDC.get("e")) },
        { assertEquals("g", MDC.get("f")) },
      )

      withLoggingContext("a" to "b", "c" to "d", "e" to null, "f" to null) {
        assertAll(
          { assertEquals("b", MDC.get("a")) },
          { assertEquals("d", MDC.get("c")) },
          { assertNull(MDC.get("e")) },
          { assertEquals("g", MDC.get("f")) },
        )
      }
      assertAll(
        { assertEquals("h", MDC.get("a")) },
        { assertEquals("i", MDC.get("c")) },
        { assertNull(MDC.get("e")) },
        { assertEquals("g", MDC.get("f")) },
      )
    }
    assertAll(
      { assertNull(MDC.get("a")) },
      { assertNull(MDC.get("c")) },
      { assertNull(MDC.get("e")) },
      { assertEquals("g", MDC.get("f")) },
    )
  }

  @Test
  fun `multiple pair withLoggingContext (restorePrevious=false)`() {
    MDC.put("f", "g")

    assertAll(
      { assertNull(MDC.get("a")) },
      { assertNull(MDC.get("c")) },
      { assertNull(MDC.get("e")) },
      { assertEquals("g", MDC.get("f")) },
    )

    withLoggingContext("a" to "b", "c" to "d", "e" to null, "f" to null, restorePrevious = false) {
      assertAll(
        { assertEquals("b", MDC.get("a")) },
        { assertEquals("d", MDC.get("c")) },
        { assertNull(MDC.get("e")) },
        { assertEquals("g", MDC.get("f")) },
      )
    }
    assertAll(
      { assertNull(MDC.get("a")) },
      { assertNull(MDC.get("c")) },
      { assertNull(MDC.get("e")) },
      { assertEquals("g", MDC.get("f")) },
    )
  }

  @Test
  @Deprecated(message = "Transferred checks in `multiple pair withLoggingContext`")
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

  @ParameterizedTest
  @ValueSource(booleans = [true, false])
  fun `map withLoggingContext`(restorePrevious: Boolean) {
    assertAll(
      { assertNull(MDC.get("a")) },
      { assertNull(MDC.get("c")) },
      { assertNull(MDC.get("e")) },
      { assertNull(MDC.get("f")) },
      { assertNull(MDC.get("k")) },
    )

    MDC.put("e", "g")
    MDC.put("k", "l")

    withLoggingContext(mapOf("a" to "b", "c" to "d", "e" to null, "f" to "h"), restorePrevious) {
      assertAll(
        { assertEquals("b", MDC.get("a")) },
        { assertEquals("d", MDC.get("c")) },
        { assertEquals("g", MDC.get("e")) },
        { assertEquals("h", MDC.get("f")) },
        { assertEquals("l", MDC.get("k")) },
      )

      withLoggingContext(mapOf("a" to "b", "e" to "i", "f" to "j")) {
        assertAll(
          { assertEquals("b", MDC.get("a")) },
          { assertEquals("d", MDC.get("c")) },
          { assertEquals("i", MDC.get("e")) },
          { assertEquals("j", MDC.get("f")) },
          { assertEquals("l", MDC.get("k")) },
        )
      }

      assertAll(
        { assertEquals("b", MDC.get("a")) },
        { assertEquals("d", MDC.get("c")) },
        { assertEquals("g", MDC.get("e")) },
        { assertEquals("h", MDC.get("f")) },
        { assertEquals("l", MDC.get("k")) },
      )
    }

    assertAll(
      { assertNull(MDC.get("a")) },
      { assertNull(MDC.get("c")) },
      {
        if (restorePrevious) {
          assertEquals("g", MDC.get("e"))
        } else {
          assertNull(MDC.get("e"))
        }
      },
      { assertNull(MDC.get("f")) },
      { assertEquals("l", MDC.get("k")) },
    )
  }
}
