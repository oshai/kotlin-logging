package io.github.oshai.kotlinlogging

public class KLoggingEventBuilder {
  public var message: String? = null
  public var cause: Throwable? = null
  public var payload: Map<String, Any?>? = null
}
