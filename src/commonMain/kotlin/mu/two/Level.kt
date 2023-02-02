package io.github.oshai

public enum class Level(private val levelInt: Int, private val levelStr: String) {
  TRACE(io.github.oshai.Levels.TRACE_INT, "TRACE"),
  DEBUG(io.github.oshai.Levels.DEBUG_INT, "DEBUG"),
  INFO(io.github.oshai.Levels.INFO_INT, "INFO"),
  WARN(io.github.oshai.Levels.WARN_INT, "WARN"),
  ERROR(io.github.oshai.Levels.ERROR_INT, "ERROR"),
  ;

  public fun toInt(): Int {
    return levelInt
  }

  /** Returns the string representation of this Level. */
  override fun toString(): String {
    return levelStr
  }
}

public object Levels {

  public const val TRACE_INT: Int = 0
  public const val DEBUG_INT: Int = 10
  public const val INFO_INT: Int = 20
  public const val WARN_INT: Int = 30
  public const val ERROR_INT: Int = 40

  public fun intToLevel(levelInt: Int): io.github.oshai.Level {
    return when (levelInt) {
      io.github.oshai.Levels.TRACE_INT -> io.github.oshai.Level.TRACE
      io.github.oshai.Levels.DEBUG_INT -> io.github.oshai.Level.DEBUG
      io.github.oshai.Levels.INFO_INT -> io.github.oshai.Level.INFO
      io.github.oshai.Levels.WARN_INT -> io.github.oshai.Level.WARN
      io.github.oshai.Levels.ERROR_INT -> io.github.oshai.Level.ERROR
      else -> throw IllegalArgumentException("Level integer [$levelInt] not recognized.")
    }
  }
}
