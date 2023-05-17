package io.github.oshai.internal

import io.github.oshai.DoNothingDecorator
import io.github.oshai.KLogger
import io.github.oshai.KLoggerDecorator

internal expect object KLoggerFactory {


  /** get logger by explicit name */
  internal inline fun logger(name: String, decorator: KLoggerDecorator = DoNothingDecorator): KLogger
}
