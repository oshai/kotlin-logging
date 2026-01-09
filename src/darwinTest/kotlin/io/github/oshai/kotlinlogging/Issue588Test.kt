package io.github.oshai.kotlinlogging

import platform.Foundation.NSUUID
import kotlin.test.Test
import kotlin.test.assertTrue

class Issue588Test {
  @Test
  fun testHistoricalLogging() {
    val uuid = NSUUID().UUIDString
    val logger = KotlinLogging.logger("issue588.repro")

    logger.info { "Test message execution $uuid" }
    logger.error { "Test error execution $uuid" }
  }
  }
}
