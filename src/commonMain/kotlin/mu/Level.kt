package mu

public enum class Level(private val levelInt: Int, private val levelStr: String) {
  TRACE(Levels.TRACE_INT, "TRACE"),
  DEBUG(Levels.DEBUG_INT, "DEBUG"),
  INFO(Levels.INFO_INT, "INFO"),
  WARN(Levels.WARN_INT, "WARN"),
  ERROR(Levels.ERROR_INT, "ERROR"),
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

  public fun intToLevel(levelInt: Int): Level {
    return when (levelInt) {
      TRACE_INT -> Level.TRACE
      DEBUG_INT -> Level.DEBUG
      INFO_INT -> Level.INFO
      WARN_INT -> Level.WARN
      ERROR_INT -> Level.ERROR
      else -> throw IllegalArgumentException("Level integer [$levelInt] not recognized.")
    }
  }
}
