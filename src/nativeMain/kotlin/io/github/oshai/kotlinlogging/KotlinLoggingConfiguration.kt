package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.DirectLoggerFactory
import kotlin.concurrent.AtomicReference

public actual object KotlinLoggingConfiguration {
  private val _logLevel = AtomicReference(Level.INFO)
  public actual var logLevel: Level
    get() = _logLevel.value
    set(value) {
      _logLevel.value = value
    }

  private val _appender = AtomicReference(DefaultAppender)
  public actual var appender: Appender
    get() = _appender.value
    set(value) {
      _appender.value = value
    }

  private val _formatter = AtomicReference<Formatter>(DefaultMessageFormatter(includePrefix = true))
  public actual var formatter: Formatter
    get() = _formatter.value
    set(value) {
      _formatter.value = value
    }

  private val _logFactory = AtomicReference<KLoggerFactory>(DirectLoggerFactory)
  public actual var logFactory: KLoggerFactory
    get() = _logFactory.value
    set(value) {
      _logFactory.value = value
    }
}
