package io.github.oshai.kotlinlogging

import java.io.StringWriter
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.appender.WriterAppender
import org.apache.logging.log4j.core.layout.PatternLayout

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

data class AppenderWithWriter(
  val pattern: String = "%-5p %c %marker - %m%n",
  val writer: StringWriter = StringWriter(),
  val appender: Appender =
    WriterAppender.createAppender(newPatternLayout(pattern), null, writer, "writer", false, true)
)
