package io.github.oshai.kotlinlogging

public expect object KotlinLoggingConfiguration {
  /**
   * The global logger factory used by `KotlinLogging.logger`. Change this to swap the underlying
   * logging implementation (e.g., to [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory]
   * on JVM/Darwin).
   */
  public var logFactory: KLoggerFactory

  /**
   * Configuration for the **Direct Logging** implementation. On JVM/Android/Darwin, this ONLY
   * applies if [logFactory] is set to use
   * [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory]. On Native/JS/Wasm, this is the
   * default mechanism.
   */
  public var logLevel: Level

  /**
   * Configuration for the **Direct Logging** implementation. On JVM/Android/Darwin, this ONLY
   * applies if [logFactory] is set to use
   * [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory].
   */
  public var formatter: Formatter

  /**
   * Configuration for the **Direct Logging** implementation. On JVM/Android/Darwin, this ONLY
   * applies if [logFactory] is set to use
   * [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory].
   */
  public var appender: Appender
}
