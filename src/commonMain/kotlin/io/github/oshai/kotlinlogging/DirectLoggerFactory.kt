package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.KLoggerDirect

/**
 * A [KLoggerFactory] that creates [io.github.oshai.kotlinlogging.internal.KLoggerDirect] instances.
 *
 * "Direct" logging means that log events are sent directly to the configured
 * [io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.direct], which defaults to writing to
 * the console (stdout/stderr).
 *
 * This factory is the default on Native, JS, and Wasm targets. On JVM, Android, and Darwin, it can
 * be used to opt-in to the "Direct" logging implementation, which uses
 * [io.github.oshai.kotlinlogging.KotlinLoggingConfiguration.direct].
 */
public object DirectLoggerFactory : KLoggerFactory {

  /** Returns a [io.github.oshai.kotlinlogging.internal.KLoggerDirect] with the given [name]. */
  override fun logger(name: String): KLogger {
    return KLoggerDirect(name)
  }
}
