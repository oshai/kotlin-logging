package io.github.oshai.kotlinlogging

import kotlin.test.Test

@Suppress("DEPRECATION")
class SimpleTest {
  private val logger = KotlinLogging.logger {}

  @Test
  fun simpleTest() {
    logger.info { "Hello!" }
  }

  @Test
  fun shouldSupportMarkers() {

    val marker = KMarkerFactory.getMarker("someMarker")

    logger.info(marker = marker) { "info" }
    logger.debug(marker = marker) { "debug" }
    logger.error(marker = marker) { "error" }
    logger.error(marker = marker) { "warn" }
    logger.trace(marker = marker) { "trace" }
  }
}
