package io.github.oshai.kotlinlogging.coroutines

import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.test.*
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
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
  fun `entries do not stay on the thread the block started on`() {
    runAcrossThreads { hop ->
      withLoggingContextAsync("a" to "b") {
        hop()
        assertEquals("b", MDC.get("a"))
      }
    }
    assertNull(MDC.get("a"))
  }

  @Test
  fun `entries do not stay on the thread the block started on (restorePrevious=false)`() {
    runAcrossThreads { hop ->
      withLoggingContextAsync("a" to "b", restorePrevious = false) {
        hop()
        assertEquals("b", MDC.get("a"))
      }
    }
    assertNull(MDC.get("a"))
  }

  @Test
  fun `a previous value is restored on the thread the block started on only`() {
    MDC.put("a", "x")

    runAcrossThreads { hop ->
      withLoggingContextAsync("a" to "b") {
        hop()
        assertEquals("b", MDC.get("a"))
      }
    }

    assertEquals("x", MDC.get("a"))
  }

  @Test
  fun `pair withLoggingContext keeps a key whose new value is null (restorePrevious=false)`() =
    runTest {
      MDC.put("a", "x")

      withLoggingContextAsync("a" to null, restorePrevious = false) {
        assertEquals("x", MDC.get("a"))
      }

      assertEquals("x", MDC.get("a"))
    }

  @Test
  fun `entries do not stay on either thread when the block throws`() {
    val failure =
      assertFailsWith<IllegalStateException> {
        runAcrossThreads { hop ->
          withLoggingContextAsync("a" to "b") {
            hop()
            error("failed inside the block")
          }
        }
      }
    assertEquals("failed inside the block", failure.message)
    assertNull(MDC.get("a"))
  }

  @Test
  fun `a key is removed when the block throws (restorePrevious=false)`() {
    MDC.put("a", "x")

    assertFailsWith<IllegalStateException> {
      runBlocking {
        withLoggingContextAsync("a" to "b", restorePrevious = false) { error("failed") }
      }
    }

    assertNull(MDC.get("a"))
  }

  @Test
  fun `map withLoggingContext removes a key whose new value is null (restorePrevious=false)`() =
    runTest {
      MDC.put("e", "g")

      withLoggingContextAsync(mapOf("e" to null), restorePrevious = false) {
        assertEquals("g", MDC.get("e"))
      }

      assertNull(MDC.get("e"))
    }

  /**
   * Runs [block] on this thread until it calls `hop` once, then continues it on another thread. The
   * other thread resumes it only after it has suspended, so the thread always changes. Both threads
   * must end with an empty MDC.
   */
  private fun runAcrossThreads(block: suspend (hop: suspend () -> Unit) -> Unit) {
    val executor = Executors.newSingleThreadExecutor()
    val leftOnExecutor =
      try {
        var parked: CancellableContinuation<Unit>? = null
        runBlocking(Dispatchers.Unconfined) {
          val result =
            async(start = CoroutineStart.UNDISPATCHED) {
              block {
                val suspendedOn = Thread.currentThread()
                suspendCancellableCoroutine { parked = it }
                assertNotSame(suspendedOn, Thread.currentThread())
              }
            }
          val continuation = checkNotNull(parked) { "the block finished without calling hop" }
          executor.execute { continuation.resume(Unit) }
          result.await()
        }
        // read on the test thread: after the hop, the code after await() runs on the executor, so
        // get() there would wait for itself
        executor.submit<Map<String, String>?> { MDC.getCopyOfContextMap() }.get()
      } finally {
        executor.shutdown()
      }
    assertEquals(emptyMap(), leftOnExecutor.orEmpty())
  }
}
