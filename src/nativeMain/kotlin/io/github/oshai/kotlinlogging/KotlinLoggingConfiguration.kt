package io.github.oshai.kotlinlogging

import kotlin.native.concurrent.AtomicReference

public expect val DefaultAppender: Appender

public object KotlinLoggingConfiguration {
  private val _logLevel = AtomicReference(Level.INFO)
  public var logLevel: Level
    get() = _logLevel.value
    set(value) {
      _logLevel.value = value
    }
  private val _appender = AtomicReference(DefaultAppender)
  public var appender: Appender
    get() = _appender.value
    set(value) {
      _appender.value = value
    }
  private val _formatter = AtomicReference<Formatter>(DefaultMessageFormatter)
  public var formatter: Formatter
    get() = _formatter.value
    set(value) {
      _formatter.value = value
    }
}
