package io.github.oshai.kotlinlogging.slf4j

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.addAppender
import io.github.oshai.kotlinlogging.removeAppender
import kotlin.test.assertEquals
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.NullAppender
import org.apache.logging.slf4j.Log4jMarker
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

private class TestAppender(
  val appender: Appender = NullAppender.createAppender("testAppender"),
  var lastEvent: LogEvent? = null,
) : Appender by appender {
  override fun append(event: LogEvent) {
    lastEvent = event.toImmutable()
    appender.append(event)
  }
}

class Slf4jMarkerTest {

  private val testAppender = TestAppender()

  @BeforeEach
  fun setupAppender() {
    addAppender(testAppender)
  }

  @AfterEach
  fun removeAppender() {
    removeAppender(testAppender)
  }

  @Test
  fun `a slf4j Marker can be supplied to the logger`() {

    val log4jMarker =
      object : org.apache.logging.log4j.Marker {
        override fun addParents(
          vararg markers: org.apache.logging.log4j.Marker?
        ): org.apache.logging.log4j.Marker = TODO("Not yet implemented")

        override fun getName(): String = "foo"

        override fun getParents(): Array<org.apache.logging.log4j.Marker> =
          TODO("Not yet implemented")

        override fun hasParents(): Boolean = false

        override fun isInstanceOf(m: org.apache.logging.log4j.Marker?): Boolean = false

        override fun isInstanceOf(name: String?): Boolean = false

        override fun remove(marker: org.apache.logging.log4j.Marker?): Boolean =
          TODO("Not yet implemented")

        override fun setParents(
          vararg markers: org.apache.logging.log4j.Marker?
        ): org.apache.logging.log4j.Marker = TODO("Not yet implemented")
      }

    val slf4jMarker = Log4jMarker(null, log4jMarker)

    logger.atError(slf4jMarker.toKotlinLogging()) { message = "bar" }

    assertEquals(testAppender.lastEvent?.marker, log4jMarker)
  }
}
