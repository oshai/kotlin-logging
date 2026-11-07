package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import kotlin.test.*

class DarwinFormatterTest {

  @Test
  fun simpleTest() {
    KLoggingEventBuilder()
      .apply { message = "msg1" }
      .run { assertEquals("msg1", DarwinFormatter.getFormattedMessage(this, null)) }
  }
}
