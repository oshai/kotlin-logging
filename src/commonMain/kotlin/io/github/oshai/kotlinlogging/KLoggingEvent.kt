package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.getCurrentTime

public data class KLoggingEvent(
  public val level: Level,
  public val marker: Marker?,
  public val loggerName: String,
  public val message: String? = null,
  public val cause: Throwable? = null,
  public val payload: Map<String, Any?>? = null,
  public val timestamp: Long = getCurrentTime(),
) {
  public constructor(
    level: Level,
    marker: Marker?,
    loggerName: String,
    eventBuilder: KLoggingEventBuilder,
  ) : this(
    level,
    marker,
    loggerName,
    eventBuilder.message,
    eventBuilder.cause,
    eventBuilder.payload,
  )
}
