package io.github.oshai.jul.internal

import java.util.logging.Logger
import io.github.oshai.KLogger

internal object JulLoggerFactory {

  /** get a java logger by name */
  fun jLogger(name: String): Logger = Logger.getLogger(name)

  /** wrap java logger based on location awareness */
  fun wrapJLogger(jLogger: Logger): KLogger = JulLoggerWrapper(jLogger)
}
