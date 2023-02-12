package io.github.oshai.internal

import io.github.oshai.KLogger

internal expect object KLoggerFactory {

  internal inline fun logger(name: String): KLogger
}
