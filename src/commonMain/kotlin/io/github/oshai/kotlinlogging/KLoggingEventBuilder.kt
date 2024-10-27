package io.github.oshai.kotlinlogging

public class KLoggingEventBuilder {
  public var message: String? = null
  public var cause: Throwable? = null
  public var payload: Map<String, Any?>? = null
  public var internalCompilerData: InternalCompilerData? = null

  public class InternalCompilerData(
    public val messageTemplate: String? = null,
    public val className: String? = null,
    public val methodName: String? = null,
    public val lineNumber: Int? = null,
    public val fileName: String? = null,
  )
}
