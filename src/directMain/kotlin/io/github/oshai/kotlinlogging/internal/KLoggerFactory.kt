package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger

internal actual object KLoggerFactory {

  actual fun logger(name: String): KLogger = KLoggerDirect(name)
}
