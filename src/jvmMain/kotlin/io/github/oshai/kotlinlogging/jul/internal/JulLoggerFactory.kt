package io.github.oshai.kotlinlogging.jul.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggerFactory
import java.util.logging.Logger

internal object JulLoggerFactory : KLoggerFactory {

  /** get a java logger by name */
  internal fun jLogger(name: String): Logger = Logger.getLogger(name)

  override fun logger(name: String): KLogger = wrapJLogger(jLogger(name))

  /** wrap java logger based on location awareness */
  internal fun wrapJLogger(jLogger: Logger): KLogger = JulLoggerWrapper(jLogger)
}
