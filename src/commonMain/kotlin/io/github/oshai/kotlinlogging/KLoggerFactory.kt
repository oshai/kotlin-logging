package io.github.oshai.kotlinlogging

/** Interface for a factory that creates [KLogger] instances. */
public interface KLoggerFactory {
  /** Return a logger for the given name. */
  public fun logger(name: String): KLogger
}
