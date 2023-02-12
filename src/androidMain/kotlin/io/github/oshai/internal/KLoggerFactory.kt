package io.github.oshai.internal

import io.github.oshai.KLogger

@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerFactory {

  internal actual inline fun logger(name: String): KLogger = KLoggerAndroid(name)
}
