package mu.two.slf4j

import mu.two.KLogger
import mu.two.KotlinLogging
import mu.two.Marker
import mu.two.slf4j.internal.Slf4jLoggerFactory
import org.slf4j.Logger
import org.slf4j.MarkerFactory

public fun Marker.toSlf4j(): org.slf4j.Marker = MarkerFactory.getMarker(this.getName())

@Suppress("UnusedReceiverParameter")
public fun KotlinLogging.logger(underlyingLogger: Logger): KLogger =
    Slf4jLoggerFactory.wrapJLogger(underlyingLogger)

public fun Logger.toKLogger(): KLogger = KotlinLogging.logger(this)
