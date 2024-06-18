package io.github.oshai.kotlinlogging.internal

import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Marker

internal object DarwinFormatter {

  internal fun getFormattedMessage(builder: KLoggingEventBuilder, marker: Marker?): String {
    return buildString {
      marker?.getName()?.let {
        append(it)
        append(" ")
      }
      append(builder.message)
      builder.cause?.stackTraceToString()?.let {
        append('\n')
        append(it)
      }
    }
  }
}
