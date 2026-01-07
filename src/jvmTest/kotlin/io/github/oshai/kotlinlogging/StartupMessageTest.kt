package io.github.oshai.kotlinlogging

import kotlin.test.Test
import kotlin.test.assertTrue

class StartupMessageTest {

  @Test
  fun testApiExistence() {
    // Verify that the API exists and is mutable
    assertTrue(KotlinLoggingConfiguration.logStartupMessage)
    KotlinLoggingConfiguration.logStartupMessage = false
    assertTrue(!KotlinLoggingConfiguration.logStartupMessage)
    // Restore default
    KotlinLoggingConfiguration.logStartupMessage = true
  }
}
