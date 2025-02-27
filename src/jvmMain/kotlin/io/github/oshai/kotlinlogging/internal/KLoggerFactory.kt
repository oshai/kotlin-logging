package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.jul.internal.JulLoggerFactory
import io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory
import org.slf4j.ILoggerFactory
import org.slf4j.LoggerFactory

/** factory methods to obtain a [KLogger] */
internal actual object KLoggerFactory {

  /** get logger by explicit name */
  internal actual fun logger(name: String): KLogger {
    if (System.getProperty("kotlin-logging-to-jul") != null) {
      return JulLoggerFactory.wrapJLogger(JulLoggerFactory.jLogger(name))
    }
    // Try Logback and if that fails then default to plain SLF4J
    return if (LoggerFactory.getILoggerFactory().isLogback()) {
      LogbackLoggerFactory.logbackLogger(name)
    } else {
      Slf4jLoggerFactory.wrapJLogger(Slf4jLoggerFactory.jLogger(name))
    }
  }
}

private fun ILoggerFactory.isLogback() =
  System.getProperty("kotlin-logging-to-logback") == "true" &&
    this.javaClass.name.startsWith("ch.qos.logback.classic")
