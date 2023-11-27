package io.github.oshai.kotlinlogging

import kotlin.native.concurrent.AtomicReference

public object KotlinLoggingConfiguration {
  public var subsystem: AtomicReference<String?> = AtomicReference(null)
  public var category: AtomicReference<String?> = AtomicReference(null)
}
