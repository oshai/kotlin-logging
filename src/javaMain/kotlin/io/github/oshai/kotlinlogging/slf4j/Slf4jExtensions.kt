package io.github.oshai.kotlinlogging.slf4j

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory
import org.slf4j.Logger
import org.slf4j.MarkerFactory

public fun Marker.toSlf4j(): org.slf4j.Marker = MarkerFactory.getMarker(this.getName())

public fun Level.toSlf4j(): Int = TODO()

@Suppress("UnusedReceiverParameter")
public fun KotlinLogging.logger(underlyingLogger: Logger): KLogger =
  Slf4jLoggerFactory.wrapJLogger(underlyingLogger)

public fun Logger.toKLogger(): KLogger = KotlinLogging.logger(this)
