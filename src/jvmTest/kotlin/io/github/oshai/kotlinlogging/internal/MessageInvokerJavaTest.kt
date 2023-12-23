@file:Suppress("TooGenericExceptionThrown")

package io.github.oshai.kotlinlogging.internal

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MessageInvokerJavaTest {

  @Test
  fun toStringSafeChecks() {
    assertEquals("hi", { "hi" }.toStringSafe())
  }

  @Test
  fun toStringSafeChecksThrowException() {
    assertEquals(
      "Log message invocation failed: java.lang.Exception: hi",
      { throw Exception("hi") }.toStringSafe()
    )
  }

  @Test
  fun toStringSafeChecksThrowExceptionWithSystemProperty() {
    assertThrows<Exception> {
      System.setProperty("kotlin-logging.throwOnMessageError", "")
      try {
        { throw Exception("hi") }.toStringSafe()
      } finally {
        System.clearProperty("kotlin-logging.throwOnMessageError")
      }
    }
  }
}
