package io.github.oshai.kotlinlogging

public interface DelegatingKLogger<T> {
  /** The actual logger executing logging */
  public val underlyingLogger: T
}
