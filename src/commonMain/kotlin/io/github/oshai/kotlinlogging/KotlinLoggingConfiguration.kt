package io.github.oshai.kotlinlogging

public expect object KotlinLoggingConfiguration {
  /**
   * The global logger factory used by `KotlinLogging.logger`. Change this to swap the underlying
   * logging implementation (e.g., to [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory]
   * on JVM/Darwin).
   */
  public var loggerFactory: KLoggerFactory

  /**
   * Configuration for the **Direct Logging** implementation.
   *
   * On JVM/Android/Darwin, this ONLY applies if [loggerFactory] is set to use
   * [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory]. On Native/JS/Wasm, this is the
   * default mechanism.
   */
  public val direct: DirectLoggingConfiguration

  /**
   * Configuration for the **Direct Logging** implementation.
   *
   * These settings only apply when the [KotlinLoggingConfiguration.loggerFactory] is set to
   * [io.github.oshai.kotlinlogging.internal.DirectLoggerFactory], which is the default on Native,
   * JS, and Wasm, and can be opted-into on JVM, Android, and Darwin.
   */
  public interface DirectLoggingConfiguration {
    public var logLevel: Level
    public var formatter: Formatter
    public var appender: Appender
  }
}
