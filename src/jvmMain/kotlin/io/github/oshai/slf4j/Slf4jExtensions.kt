package io.github.oshai.slf4j

import io.github.oshai.KLogger
import io.github.oshai.KotlinLogging
import io.github.oshai.Marker
import io.github.oshai.slf4j.internal.Slf4jLoggerFactory
import org.slf4j.Logger
import org.slf4j.MarkerFactory

public fun Marker.toSlf4j(): org.slf4j.Marker = MarkerFactory.getMarker(this.getName())

@Suppress("UnusedReceiverParameter")
public fun KotlinLogging.logger(underlyingLogger: Logger): KLogger =
  Slf4jLoggerFactory.wrapJLogger(underlyingLogger)

public fun Logger.toKLogger(): KLogger = KotlinLogging.logger(this)
