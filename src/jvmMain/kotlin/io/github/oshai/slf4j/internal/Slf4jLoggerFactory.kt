package io.github.oshai.slf4j.internal

import io.github.oshai.KLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger

internal object Slf4jLoggerFactory {

  /** get a java logger by name */
  fun jLogger(name: String): Logger = LoggerFactory.getLogger(name)

  /** wrap java logger based on location awareness */
  fun wrapJLogger(jLogger: Logger): KLogger =
    if (jLogger is LocationAwareLogger) LocationAwareKLogger(jLogger)
    else LocationIgnorantKLogger(jLogger)
}
