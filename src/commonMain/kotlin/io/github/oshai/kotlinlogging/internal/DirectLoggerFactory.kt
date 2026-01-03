package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggerFactory

public object DirectLoggerFactory : KLoggerFactory {
  override fun logger(name: String): KLogger {
    return KLoggerDirect(name)
  }
}
