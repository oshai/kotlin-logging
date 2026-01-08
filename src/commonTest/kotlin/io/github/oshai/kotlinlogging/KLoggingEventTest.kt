package io.github.oshai.kotlinlogging

import kotlin.test.Test
import kotlin.test.assertTrue

class KLoggingEventTest {
  @Test
  fun testTimestampIsRecent() {
    val before = io.github.oshai.kotlinlogging.internal.getCurrentTime()
    // Wait a small amount to avoid exact equality if resolution is low, or just accept >=
    val event = KLoggingEvent(Level.INFO, null, "test")
    val after = io.github.oshai.kotlinlogging.internal.getCurrentTime()

    assertTrue(
      event.timestamp >= before,
      "Timestamp ${event.timestamp} should be >= before $before",
    )
    assertTrue(event.timestamp <= after, "Timestamp ${event.timestamp} should be <= after $after")
  }
}
