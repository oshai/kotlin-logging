package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger

internal expect object KLoggerFactory {

  internal fun logger(name: String): KLogger
}
