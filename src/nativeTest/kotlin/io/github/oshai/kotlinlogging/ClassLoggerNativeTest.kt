package io.github.oshai.kotlinlogging

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassLoggerNativeTest {

  private val logger = KotlinLogging.logger {}

  @Test
  fun loggerNameIsCorrectlyInferredFromClass() {
    println(
      "Asserting logger name from class: Expected 'io.github.oshai.kotlinlogging.ClassLoggerNativeTest', was '${logger.name}'"
    )
    assertEquals("io.github.oshai.kotlinlogging.ClassLoggerNativeTest", logger.name)
  }
}
