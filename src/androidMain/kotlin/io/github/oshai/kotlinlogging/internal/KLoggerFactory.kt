package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory

internal actual object KLoggerFactory {

  internal actual fun logger(name: String): KLogger {
    if (System.getProperty("kotlin-logging-to-android-native") != null) {
      return KLoggerAndroid(name)
    }
    // default to slf4j
    return Slf4jLoggerFactory.wrapJLogger(Slf4jLoggerFactory.jLogger(name))
  }
}
