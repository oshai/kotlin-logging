package io.github.oshai.kotlinlogging

import platform.Foundation.NSUUID
import kotlin.test.Test

class Issue588Test {
  @Test
  fun testHistoricalLogging() {
    val uuid = NSUUID().UUIDString
    val logger = KotlinLogging.logger("issue588.repro")
    logger.info { "Test message execution $uuid" }
    println("Logged message with UUID: $uuid")
  }
}
