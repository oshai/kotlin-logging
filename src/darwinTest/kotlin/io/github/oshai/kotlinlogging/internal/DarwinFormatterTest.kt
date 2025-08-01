package io.github.oshai.kotlinlogging.internal

import kotlin.test.*

class DarwinFormatterTest {

  @Test
  fun simpleTest() {
    KLoggingEventBuilder().apply { message = "msg" }.run {
      assertEquals("msg", DarwinFormatter.getFormattedMessage(this, null))
    }
  }
}
