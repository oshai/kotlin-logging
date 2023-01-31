package mu.two.internal

import mu.two.KLoggable
import mu.two.KLogger
import mu.two.jul.internal.JulLoggerFactory
import mu.two.slf4j.internal.Slf4jLoggerFactory

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
