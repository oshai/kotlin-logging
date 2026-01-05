package io.github.oshai.kotlinlogging

public expect object KotlinLoggingConfiguration {
  /**
   * The global logger factory used by `KotlinLogging.logger`. Change this to swap the underlying
   * logging implementation (e.g., to [DirectLoggerFactory] on JVM/Darwin).
   */
  public var loggerFactory: KLoggerFactory

  /**
   * Configuration for the **Direct Logging** implementation.
   *
   * On JVM/Android/Darwin, this ONLY applies if [loggerFactory] is set to use
   * [DirectLoggerFactory]. On Native/JS/Wasm, this is the default mechanism.
   */
  public val direct: DirectLoggingConfiguration

  /** Configuration interface for the **Direct Logging** implementation. */
  public interface DirectLoggingConfiguration {
    public var logLevel: Level
    public var formatter: Formatter
    public var appender: Appender
  }
}

internal fun internalCheckFactory(name: String, loggerFactory: KLoggerFactory) {
  if (loggerFactory != DirectLoggerFactory) {
    println(
      "kotlin-logging: [WARN] configuring 'direct.$name' but the active logger factory is not 'DirectLoggerFactory' (active: ${loggerFactory::class.simpleName}). This config might be ignored."
    )
  }
}
