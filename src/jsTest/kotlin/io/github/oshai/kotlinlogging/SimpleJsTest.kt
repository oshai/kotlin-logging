package io.github.oshai.kotlinlogging

import kotlin.test.*

private val logger = KotlinLogging.logger("SimpleJsTest")

@Suppress("DEPRECATION")
class SimpleJsTest {
  private lateinit var appender: SimpleAppender

  @BeforeTest
  fun setup() {
    appender = createAppender()
    KotlinLoggingConfiguration.direct.appender = appender
  }

  @AfterTest
  fun cleanup() {
    KotlinLoggingConfiguration.direct.appender = ConsoleOutputAppender()
    KotlinLoggingConfiguration.direct.logLevel = Level.INFO
  }

  @Test
  fun simpleJsTest() {
    assertEquals("SimpleJsTest", logger.name)
    logger.info { "info msg" }
    assertEquals("INFO: [SimpleJsTest] info msg", appender.lastMessage)
    assertEquals("info", appender.lastLevel)
  }

  @Test
  fun logThrowableTest() {
    val errorLog = "Something Bad Happened"
    val outerMessage = "Outer Message"
    val innerMessage = "Inner Message"
    val throwable = Throwable(message = outerMessage, cause = Throwable(message = innerMessage))
    logger.error(throwable) { errorLog }
    assertEquals(
      "ERROR: [SimpleJsTest] $errorLog, Caused by: '$outerMessage', Caused by: '$innerMessage'",
      appender.lastMessage,
    )
  }

  @Test
  fun offLevelJsTest() {
    KotlinLoggingConfiguration.direct.logLevel = Level.OFF
    assertTrue(logger.isLoggingOff())
    logger.error { "error msg" }
    assertEquals("NA", appender.lastMessage)
    assertEquals("NA", appender.lastLevel)
  }

  @Test
  fun loggerNameTest() {
    assertEquals("MyClass", MyClass().logger2.name)
  }

  private fun createAppender(): SimpleAppender = SimpleAppender()

  class SimpleAppender : Appender {
    var lastMessage: String = "NA"
    var lastLevel: String = "NA"

    override fun log(loggingEvent: KLoggingEvent) {
      lastMessage = DefaultMessageFormatter(includePrefix = true).formatMessage(loggingEvent)
      lastLevel = loggingEvent.level.name.toLowerCase()
    }
  }
}

class MyClass {
  val logger2 by KotlinLogging.logger()
}
