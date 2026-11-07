package io.github.oshai.kotlinlogging.logback.internal

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.CallerData
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.IThrowableProxy
import ch.qos.logback.classic.spi.LoggerContextVO
import ch.qos.logback.classic.spi.ThrowableProxy
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.logback.toLogbackLevel
import java.time.Instant
import java.util.concurrent.atomic.AtomicLong
import org.slf4j.MDC
import org.slf4j.Marker
import org.slf4j.event.KeyValuePair

internal class LogbackLogEvent(
  private val fqcn: String,
  private val logger: Logger,
  private val level: io.github.oshai.kotlinlogging.Level,
  private val kLoggingEvent: KLoggingEventBuilder,
) : ILoggingEvent {

  private val creationTime = System.currentTimeMillis()
  private val markers = mutableListOf<Marker>()
  private val keyValuePairs = mutableListOf<KeyValuePair>()

  fun addMarker(marker: Marker) {
    markers.add(marker)
  }

  fun addKeyValuePair(kvp: KeyValuePair) {
    keyValuePairs.add(kvp)
  }

  override fun getThreadName(): String = Thread.currentThread().name

  override fun getLevel(): Level = level.toLogbackLevel()

  override fun getMessage(): String =
    kLoggingEvent.internalCompilerData?.messageTemplate ?: kLoggingEvent.message ?: ""

  @Suppress("UNCHECKED_CAST")
  override fun getArgumentArray(): Array<Object>? = kLoggingEvent.arguments as? Array<Object>

  override fun getFormattedMessage(): String? = kLoggingEvent.message

  override fun getLoggerName(): String = logger.name

  override fun getLoggerContextVO(): LoggerContextVO? = logger.loggerContext.loggerContextRemoteView

  override fun getThrowableProxy(): IThrowableProxy? =
    kLoggingEvent.cause?.let { ThrowableProxy(it) }

  override fun getTimeStamp(): Long = creationTime

  override fun getInstant(): Instant = Instant.ofEpochMilli(creationTime)

  override fun getNanoseconds(): Int = 0

  override fun getSequenceNumber(): Long = sequenceNumberGenerator.getAndIncrement()

  override fun getKeyValuePairs(): List<KeyValuePair> = keyValuePairs

  override fun prepareForDeferredProcessing() {}

  override fun getMDCPropertyMap(): Map<String, String> = MDC.getCopyOfContextMap() ?: emptyMap()

  @Deprecated("Deprecated in ILoggingEvent")
  override fun getMdc(): Map<String, String> = mdcPropertyMap

  override fun getMarkerList(): List<Marker> = markers

  @Deprecated("Deprecated in ILoggingEvent")
  override fun getMarker(): Marker? = markers.firstOrNull()

  override fun hasCallerData(): Boolean =
    if (kLoggingEvent.internalCompilerData?.fileName != null) {
      true
    } else {
      getCallerData().isNotEmpty()
    }

  override fun getCallerData(): Array<StackTraceElement> {
    if (kLoggingEvent.internalCompilerData?.fileName != null) {
      return arrayOf(
        StackTraceElement(
          kLoggingEvent.internalCompilerData?.className ?: "",
          kLoggingEvent.internalCompilerData?.methodName ?: "",
          kLoggingEvent.internalCompilerData?.fileName,
          kLoggingEvent.internalCompilerData?.lineNumber ?: 0,
        )
      )
    }
    return CallerData.extract(Throwable(), fqcn, 8, logger.loggerContext.frameworkPackages)
  }

  override fun toString(): String {
    return "LogbackLogEvent(level=${level}, message=${message})"
  }

  companion object {
    private val sequenceNumberGenerator = AtomicLong(0)
  }
}
