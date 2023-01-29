package mu.two

public expect object KotlinLogging {
  /**
   * This method allow defining the logger in a file in the following way:
   * ```
   * val logger = KotlinLogging.logger {}
   * ```
   */
  public fun logger(func: () -> Unit): mu.two.KLogger

  public fun logger(name: String): mu.two.KLogger
}
