package io.github.oshai.internal

import io.github.oshai.KLoggable
import io.github.oshai.KLogger
import io.github.oshai.jul.internal.JulLoggerFactory
import io.github.oshai.slf4j.internal.Slf4jLoggerFactory

/** factory methods to obtain a [Logger] */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerFactory {

  /** get logger for the class */
  internal inline fun logger(loggable: KLoggable): KLogger =
    logger(KLoggerNameResolver.name(loggable.javaClass))

  /** get logger by explicit name */
  internal inline fun logger(name: String): KLogger {
    if (System.getProperty("kotlin-logging-to-jul") != null) {
      return JulLoggerFactory.wrapJLogger(JulLoggerFactory.jLogger(name))
    }
    // default to slf4j
    return Slf4jLoggerFactory.wrapJLogger(Slf4jLoggerFactory.jLogger(name))
  }

  /** get logger for the method, assuming it was declared at the logger file/class */
  internal inline fun logger(noinline func: () -> Unit): KLogger =
    logger(KLoggerNameResolver.name(func))
}
