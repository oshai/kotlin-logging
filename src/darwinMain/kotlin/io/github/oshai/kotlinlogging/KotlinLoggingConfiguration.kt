package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.DefaultLoggerFactory
import kotlin.concurrent.AtomicReference

public actual object KotlinLoggingConfiguration {
  // Existing Darwin-specific properties
  public var subsystem: AtomicReference<String?> = AtomicReference(null)
  public var category: AtomicReference<String?> = AtomicReference(null)

  // Common properties
  private val _logLevel = AtomicReference(Level.INFO)
  public actual var logLevel: Level
    get() = _logLevel.value
    set(value) {
      _logLevel.value = value
    }

  private val _formatter = AtomicReference<Formatter>(DefaultMessageFormatter(includePrefix = true))
  public actual var formatter: Formatter
    get() = _formatter.value
    set(value) {
      _formatter.value = value
    }

  private val _appender = AtomicReference(DefaultAppender)
  public actual var appender: Appender
    get() = _appender.value
    set(value) {
      _appender.value = value
    }

  private val _logFactory = AtomicReference<KLoggerFactory>(DefaultLoggerFactory)
  public actual var LOG_FACTORY: KLoggerFactory
    get() = _logFactory.value
    set(value) {
      _logFactory.value = value
    }
}
