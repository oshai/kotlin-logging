package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggerFactory
import io.github.oshai.kotlinlogging.jul.internal.JulLoggerFactory
import io.github.oshai.kotlinlogging.logback.internal.LogbackLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory

/** factory methods to obtain a [KLogger] */
internal object DefaultLoggerFactory : KLoggerFactory {

  /** get logger by explicit name */
  override fun logger(name: String): KLogger {
    // Note: any changes here might have to be also applied to
    // [Target_io_github_oshai_kotlinlogging_internal_KLoggerFactory].
    if (System.getProperty("kotlin-logging-to-jul") != null) {
      return JulLoggerFactory.wrapJLogger(JulLoggerFactory.jLogger(name))
    } else if (System.getProperty("kotlin-logging-to-logback") == "true") {
      return LogbackLoggerFactory.logbackLogger(name)
    } else if (System.getProperty("kotlin-logging-to-direct") == "true") {
      return DirectLoggerFactory.logger(name)
    }
    // default to SLF4J
    return Slf4jLoggerFactory.wrapJLogger(Slf4jLoggerFactory.jLogger(name))
  }
}
