package io.github.oshai.kotlinlogging.jul.internal

import io.github.oshai.kotlinlogging.KLogger
import java.util.logging.Logger

internal object JulLoggerFactory {

  /** get a java logger by name */
  internal fun jLogger(name: String): Logger = Logger.getLogger(name)

  /** wrap java logger based on location awareness */
  internal fun wrapJLogger(jLogger: Logger): KLogger = JulLoggerWrapper(jLogger)
}
