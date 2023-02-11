package io.github.oshai

import kotlin.test.Test

class SimpleTest {
  private val logger = KotlinLogging.logger {}

  @Test
  fun simpleTest() {
    logger.info { "Hello!" }
  }
}
