package io.github.oshai.kotlinlogging.coroutines

import java.util.concurrent.Executors
import kotlin.test.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
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
      restorePrevious = false,
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

  @Test
  fun `withLoggingContextAsync leaks context across coroutines`() = runTest {
    val dispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
    // establish order of events with deferreds:
    // 1. request A will start, set its context, and then wait for request B (before exiting
    // withLoggingContextAsync)
    // 2. request B will start, set its context, and then capture the MDC
    val requestAStarted = CompletableDeferred<Unit>()
    val requestBDone = CompletableDeferred<Unit>()

    val requestADuringBlock = CompletableDeferred<Map<String, String>?>()
    val requestAAfterBlock = CompletableDeferred<Map<String, String>?>()
    val requestBDuringBlock = CompletableDeferred<Map<String, String>?>()
    val requestBAfterBlock = CompletableDeferred<Map<String, String>?>()

    val jobA =
      launch(dispatcher) {
        withLoggingContextAsync("foo" to "original") {
          requestAStarted.complete(Unit)
          requestADuringBlock.complete(MDC.getCopyOfContextMap())
          requestBDone.await() // Suspend while B runs
        }
        // will be empty
        requestAAfterBlock.complete(MDC.getCopyOfContextMap())
      }

    val jobB =
      launch(dispatcher) {
        requestAStarted.await()
        withLoggingContextAsync("foo" to "bar") {
          requestBDuringBlock.complete(MDC.getCopyOfContextMap())
        }
        // Capture MDC state immediately after Request B's block, will NOT be empty due to the bug
        requestBAfterBlock.complete(MDC.getCopyOfContextMap())
        requestBDone.complete(Unit)
      }

    jobA.join()
    jobB.join()

    assertEquals(mapOf("foo" to "original"), requestADuringBlock.await())
    assertEquals(emptyMap(), requestAAfterBlock.await())
    assertEquals(mapOf("foo" to "bar"), requestBDuringBlock.await())
    // THIS IS THE BUG: Request B sees "original" after its own block exits
    // It should be null (no context) but it's leaking Request A's value
    assertEquals(mapOf("foo" to "original"), requestBAfterBlock.await())
  }

  @Test
  fun `withCoroutineLoggingContext does not leak context across coroutines`() = runTest {
    val dispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
    // establish order of events with deferreds:
    // 1. request A will start, set its context, and then wait for request B (before exiting
    // withLoggingContextAsync)
    // 2. request B will start, set its context, and then capture the MDC
    val requestAStarted = CompletableDeferred<Unit>()
    val requestBDone = CompletableDeferred<Unit>()

    val requestADuringBlock = CompletableDeferred<Map<String, String>?>()
    val requestAAfterBlock = CompletableDeferred<Map<String, String>?>()
    val requestBDuringBlock = CompletableDeferred<Map<String, String>?>()
    val requestBAfterBlock = CompletableDeferred<Map<String, String>?>()

    val jobA =
      launch(dispatcher) {
        withCoroutineLoggingContext("foo" to "original") {
          requestAStarted.complete(Unit)
          requestADuringBlock.complete(MDC.getCopyOfContextMap())
          requestBDone.await() // Suspend while B runs
        }
        // will be empty
        requestAAfterBlock.complete(MDC.getCopyOfContextMap())
      }

    val jobB =
      launch(dispatcher) {
        requestAStarted.await()
        withCoroutineLoggingContext("foo" to "bar") {
          requestBDuringBlock.complete(MDC.getCopyOfContextMap())
        }
        // Capture MDC state immediately after Request B's block, will NOT be empty due to the bug
        requestBAfterBlock.complete(MDC.getCopyOfContextMap())
        requestBDone.complete(Unit)
      }

    jobA.join()
    jobB.join()

    assertEquals(mapOf("foo" to "original"), requestADuringBlock.await())
    assertEquals(emptyMap(), requestAAfterBlock.await())
    assertEquals(mapOf("foo" to "bar"), requestBDuringBlock.await())
    assertEquals(emptyMap(), requestBAfterBlock.await())
  }

  @Test
  fun `map withCoroutineLoggingContext`() = runTest {
    assertNull(MDC.get("a"))
    assertNull(MDC.get("c"))
    assertNull(MDC.get("e"))
    assertNull(MDC.get("f"))
    assertNull(MDC.get("k"))

    // original MDC outside of coroutine context
    MDC.put("e", "g")
    MDC.put("k", "l")

    withCoroutineLoggingContext(mapOf("a" to "b", "c" to "d", "e" to null, "f" to "h")) {
      assertEquals("b", MDC.get("a"))
      assertEquals("d", MDC.get("c"))
      // does NOT inherit original MDC
      assertNull(MDC.get("e"))
      assertEquals("h", MDC.get("f"))
      // does NOT inherit original MDC
      assertNull(MDC.get("k"))

      withCoroutineLoggingContext(mapOf("a" to "b", "e" to "i", "f" to "j")) {
        assertEquals("b", MDC.get("a"))
        // DOES inherit previous coroutine context
        assertEquals("d", MDC.get("c"))
        assertEquals("i", MDC.get("e"))
        assertEquals("j", MDC.get("f"))
        assertNull(MDC.get("k"))
      }

      assertEquals("b", MDC.get("a"))
      assertEquals("d", MDC.get("c"))
      assertNull(MDC.get("e"))
      assertEquals("h", MDC.get("f"))
      assertNull(MDC.get("k"))
    }

    assertNull(MDC.get("a"))
    assertNull(MDC.get("c"))

    // original MDC is restored
    assertEquals("g", MDC.get("e"))
    assertNull(MDC.get("f"))
    // original MDC is restored
    assertEquals("l", MDC.get("k"))
  }
}
