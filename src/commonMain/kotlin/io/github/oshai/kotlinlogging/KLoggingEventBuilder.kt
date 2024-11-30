package io.github.oshai.kotlinlogging

public class KLoggingEventBuilder {
  public var message: String? = null
  public var cause: Throwable? = null
  public var payload: Map<String, Any?>? = null
  /**
   * Arguments passed as is to underlying impl. API stability is not guaranteed.
   */
  public var arguments: Array<Any?>? = null

  /**
   * Internal data that is used by compiler plugin to provide additional information about the log
   * site. Not intended for use by user code, API stability is not guaranteed.
   */
  public var internalCompilerData: InternalCompilerData? = null

  public class InternalCompilerData(
    public val messageTemplate: String? = null,
    public val className: String? = null,
    public val methodName: String? = null,
    public val lineNumber: Int? = null,
    public val fileName: String? = null,
  )
}
