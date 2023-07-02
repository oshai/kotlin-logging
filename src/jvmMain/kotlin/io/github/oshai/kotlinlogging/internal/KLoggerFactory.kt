package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.jul.internal.JulLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory

/** factory methods to obtain a [KLogger] */
@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerFactory {

  /** get logger by explicit name */
  internal actual inline fun logger(name: String): KLogger {
    if (System.getProperty("kotlin-logging-to-jul") != null) {
      return JulLoggerFactory.wrapJLogger(JulLoggerFactory.jLogger(name))
    }
    // default to slf4j
    return Slf4jLoggerFactory.wrapJLogger(Slf4jLoggerFactory.jLogger(name))
  }
}
