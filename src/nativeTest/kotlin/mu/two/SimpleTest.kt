package mu.two

class SimpleTest {}

private val logger = mu.two.KotlinLogging.logger {}

fun main() {
  logger.info { "Hello!" }
}
