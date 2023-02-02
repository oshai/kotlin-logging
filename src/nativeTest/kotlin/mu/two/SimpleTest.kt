package io.github.oshai

class SimpleTest {}

private val logger = io.github.oshai.KotlinLogging.logger {}

fun main() {
  logger.info { "Hello!" }
}
