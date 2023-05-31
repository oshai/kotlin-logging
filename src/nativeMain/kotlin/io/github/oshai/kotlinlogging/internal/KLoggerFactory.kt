package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger

@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerFactory {

  actual inline fun logger(name: String): KLogger = KLoggerNative(name)
}
