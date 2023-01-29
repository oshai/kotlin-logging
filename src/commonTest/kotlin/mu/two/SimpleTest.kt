package mu.two

import kotlin.test.Test

class SimpleTest {
  private val logger = mu.two.KotlinLogging.logger {}

  @Test
  fun simpleTest() {
    logger.info { "Hello!" }
  }
}
