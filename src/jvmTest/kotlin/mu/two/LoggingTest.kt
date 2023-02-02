package io.github.oshai

import java.io.StringWriter
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.appender.WriterAppender
import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.core.layout.PatternLayout
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class ClassWithLogging {
  companion object : KLogging()

  fun test() {
    logger.info { "test ClassWithLogging" }
  }

  fun testThrowable() {
    val ex = Throwable()
    logger.trace(ex) { "test ClassWithLogging" }
  }

  fun testNullableThrowable() {
    val ex: Throwable? = null
    logger.trace(ex) { "test ClassWithLogging" }
  }

  fun testMarker() {
    val marker = KMarkerFactory.getMarker("MARKER")
    logger.trace(marker) { "test ClassWithLogging" }
  }

  fun testMarkerThrowable() {
    val marker = KMarkerFactory.getMarker("MARKER")
    val ex = Throwable()
    logger.trace(marker, ex) { "test ClassWithLogging" }
  }

  fun testFormatting() {
    logger.info("Message: {}", "String with {} curly braces")
  }
}

open class ClassHasLogging : KLoggable {
  override val logger = logger()
  fun test() {
    logger.info { "test ClassHasLogging" }
  }
}

/**
 * This class demonstrates the disadvantage of inheriting KLoggable in a class instead of companion
 * object the logger name will be of the sub-class ie: it is not possible to log with parent class
 * name this way
 */
class ClassInheritLogging : ClassHasLogging()

class ClassWithNamedLogging {
  companion object : Any(), KLoggable by NamedKLogging("io.github.oshai.ClassWithNamedLogging")

  fun test() {
    logger.info { "test ClassWithNamedLogging" }
  }
}

class CompanionHasLogging {
  companion object : Any(), KLoggable {
    override val logger = logger()
  }

  fun test() {
    logger.info { "test CompanionHasLogging" }
  }
}

class ChildClassWithLogging {
  companion object : KLogging()

  fun test() {
    logger.info { "test ChildClassWithLogging" }
  }
}

data class ClassWithIncorrectToString(val someVariable: String? = null) {
  override fun toString(): String {
    return someVariable!!.toString()
  }
}

class LambdaRaisesError {
  companion object : KLogging()

  fun test() {
    val problematicClass = ClassWithIncorrectToString()

    logger.info { " $problematicClass" }
  }
}

fun newPatternLayout(pattern: String): PatternLayout =
    PatternLayout.newBuilder().withPattern(pattern).build()

fun addAppender(appender: Appender) {
  val context = LogManager.getContext(false) as org.apache.logging.log4j.core.LoggerContext
  context.configuration.rootLogger.addAppender(appender, null, null)
  appender.start()
}

fun removeAppender(appender: Appender) {
  val context = LogManager.getContext(false) as org.apache.logging.log4j.core.LoggerContext
  context.configuration.rootLogger.removeAppender(appender.name)
  appender.stop()
}

class LoggingTest {
  private val appenderWithWriter: AppenderWithWriter = AppenderWithWriter()

  init {
    Configurator.setRootLevel(Level.TRACE)
  }

  @BeforeEach
  fun setupAppender() {
    addAppender(appenderWithWriter.appender)
  }

  @AfterEach
  fun removeAppender() {
    removeAppender(appenderWithWriter.appender)
  }

  @Test
  fun testMessages0() {
    ClassWithLogging().apply {
      test()
      testThrowable()
      testNullableThrowable()
    }
    appenderWithWriter.writer.flush()
    val lines =
        appenderWithWriter.writer
            .toString()
            .trim()
            .replace("\r", "\n")
            .replace("\n\n", "\n")
            .split("\n")
    assertAll(
        { assertEquals("INFO  io.github.oshai.ClassWithLogging  - test ClassWithLogging", lines[0].trim()) },
        { assertEquals("TRACE io.github.oshai.ClassWithLogging  - test ClassWithLogging", lines[1].trim()) },
        { assertEquals("java.lang.Throwable: null", lines[2].trim()) },
        { assertTrue(lines[3].trim().startsWith("at io.github.oshai.ClassWithLogging.testThrowable(")) },
        {
          assertEquals(
              "TRACE io.github.oshai.ClassWithLogging  - test ClassWithLogging",
              lines[lines.size - 1].trim())
        },
    )
  }

  @Test
  fun testMessages1() {
    ClassWithLogging().apply {
      testMarker()
      testMarkerThrowable()
    }
    appenderWithWriter.writer.flush()
    val lines =
        appenderWithWriter.writer
            .toString()
            .trim()
            .replace("\r", "\n")
            .replace("\n\n", "\n")
            .split("\n")
    assertAll(
        {
          assertEquals(
              "TRACE io.github.oshai.ClassWithLogging MARKER - test ClassWithLogging", lines[0].trim())
        },
        {
          assertEquals(
              "TRACE io.github.oshai.ClassWithLogging MARKER - test ClassWithLogging", lines[1].trim())
        },
        { assertEquals("java.lang.Throwable: null", lines[2].trim()) },
        {
          assertTrue(lines[3].trim().startsWith("at io.github.oshai.ClassWithLogging.testMarkerThrowable("))
        },
    )
  }

  @Test
  fun testMessages2() {
    ClassInheritLogging().test()
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.ClassInheritLogging  - test ClassHasLogging",
        appenderWithWriter.writer.toString().trim())
  }

  @Test
  fun testMessages3() {
    ChildClassWithLogging().test()
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.ChildClassWithLogging  - test ChildClassWithLogging",
        appenderWithWriter.writer.toString().trim())
  }

  @Test
  fun testMessages4() {
    ClassWithNamedLogging().test()
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.ClassWithNamedLogging  - test ClassWithNamedLogging",
        appenderWithWriter.writer.toString().trim())
  }

  @Test
  fun testMessages5() {
    ClassHasLogging().test()
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.ClassHasLogging  - test ClassHasLogging",
        appenderWithWriter.writer.toString().trim())
  }

  @Test
  fun testMessages6() {
    CompanionHasLogging().test()
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.CompanionHasLogging  - test CompanionHasLogging",
        appenderWithWriter.writer.toString().trim())
  }

  @Test
  fun shouldNotFailForFailingLambdas() {
    LambdaRaisesError().test()
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.LambdaRaisesError  - Log message invocation failed: java.lang.NullPointerException",
        appenderWithWriter.writer.toString().trim())
  }

  @Test
  fun placeholderFormatting() {
    ClassWithLogging().testFormatting()
    appenderWithWriter.writer.flush()
    assertEquals(
        "INFO  io.github.oshai.ClassWithLogging  - Message: String with {} curly braces",
        appenderWithWriter.writer.toString().trim())
  }

  @Test
  fun `check underlyingLogger property`() {
    assertEquals(
        "io.github.oshai.ClassHasLogging",
        (ClassHasLogging().logger.underlyingLogger as org.slf4j.Logger).name)
  }
}

class LoggingNameTest {
  @Test
  fun testNames() {
    assertAll(
        { assertEquals("io.github.oshai.ClassWithLogging", ClassWithLogging.logger.name) },
        { assertEquals("io.github.oshai.ClassInheritLogging", ClassInheritLogging().logger.name) },
        { assertEquals("io.github.oshai.ChildClassWithLogging", ChildClassWithLogging.logger.name) },
        { assertEquals("io.github.oshai.ClassWithNamedLogging", ClassWithNamedLogging.logger.name) },
        { assertEquals("io.github.oshai.ClassHasLogging", ClassHasLogging().logger.name) },
        { assertEquals("io.github.oshai.CompanionHasLogging", CompanionHasLogging.logger.name) },
    )
  }
}

data class AppenderWithWriter(
    val pattern: String = "%-5p %c %marker - %m%n",
    val writer: StringWriter = StringWriter(),
    val appender: Appender =
        WriterAppender.createAppender(
            newPatternLayout(pattern), null, writer, "writer", false, true)
)
