package io.github.oshai

import kotlin.native.concurrent.AtomicReference

public expect val DefaultAppender: Appender

@Suppress("ObjectPropertyName")
public object KotlinLoggingConfiguration {
  private val _logLevel = AtomicReference(io.github.oshai.Level.INFO)
  public var logLevel: io.github.oshai.Level
    get() = _logLevel.value
    set(value) {
      _logLevel.value = value
    }
  private val _appender = AtomicReference<Appender>(DefaultAppender)
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
