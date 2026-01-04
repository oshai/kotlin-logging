package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggerFactory

internal object AndroidNativeLoggerFactory : KLoggerFactory {
  override fun logger(name: String): KLogger = KLoggerAndroid(name)
}
