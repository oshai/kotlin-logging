package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger

internal object Slf4jLoggerFactory : KLoggerFactory {

  /** get a java logger by name */
  internal fun jLogger(name: String): Logger = LoggerFactory.getLogger(name)

  override fun logger(name: String): KLogger = wrapJLogger(jLogger(name))

  /** wrap java logger based on location awareness */
  internal fun wrapJLogger(jLogger: Logger): KLogger =
    if (jLogger is LocationAwareLogger) LocationAwareKLogger(jLogger)
    else LocationIgnorantKLogger(jLogger)
}
