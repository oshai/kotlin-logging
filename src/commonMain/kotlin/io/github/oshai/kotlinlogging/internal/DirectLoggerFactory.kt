package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggerFactory

/**
 * A [KLoggerFactory] that creates [KLoggerDirect] instances.
 *
 * "Direct" logging means that log events are sent directly to the configured
 * [io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.appender], which defaults to writing to
 * the console (stdout/stderr).
 *
 * This factory is the default on Native, JS, and Wasm targets. On JVM, Android, and Darwin, it can
 * be used to opt-in to the "Direct" logging implementation, which uses
 * [io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.appender],
 * [io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.formatter], and
 * [io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.logLevel].
 */
public object DirectLoggerFactory : KLoggerFactory {

  /** Returns a [KLoggerDirect] with the given [name]. */
  override fun logger(name: String): KLogger {
    return KLoggerDirect(name)
  }
}
