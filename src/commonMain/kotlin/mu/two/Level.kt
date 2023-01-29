package mu.two

public enum class Level(private val levelInt: Int, private val levelStr: String) {
  TRACE(mu.two.Levels.TRACE_INT, "TRACE"),
  DEBUG(mu.two.Levels.DEBUG_INT, "DEBUG"),
  INFO(mu.two.Levels.INFO_INT, "INFO"),
  WARN(mu.two.Levels.WARN_INT, "WARN"),
  ERROR(mu.two.Levels.ERROR_INT, "ERROR"),
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

  public fun intToLevel(levelInt: Int): mu.two.Level {
    return when (levelInt) {
      mu.two.Levels.TRACE_INT -> mu.two.Level.TRACE
      mu.two.Levels.DEBUG_INT -> mu.two.Level.DEBUG
      mu.two.Levels.INFO_INT -> mu.two.Level.INFO
      mu.two.Levels.WARN_INT -> mu.two.Level.WARN
      mu.two.Levels.ERROR_INT -> mu.two.Level.ERROR
      else -> throw IllegalArgumentException("Level integer [$levelInt] not recognized.")
    }
  }
}
