package io.github.oshai.kotlinlogging.internal

import kotlin.test.Test
import kotlin.test.assertEquals

class MessageInvokerTest {

  @Test
  fun toStringSafeChecks() {
    assertEquals(Unit.toString(), {}.toStringSafe())
    assertEquals("null", { null }.toStringSafe())
    assertEquals("hi", { "hi" }.toStringSafe())
  }
}
