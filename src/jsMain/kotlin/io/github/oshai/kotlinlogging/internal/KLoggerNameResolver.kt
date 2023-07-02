package io.github.oshai.kotlinlogging.internal

internal actual object KLoggerNameResolver {

  internal actual fun name(func: () -> Unit): String {
    var found = false
    val exception = Exception()
    for (line in exception.stackTraceToString().split("\n")) {
      if (found) {
        return line.substringBefore(".kt").substringAfterLast(".").substringAfterLast("/")
      }
      if (line.contains("at KotlinLogging")) {
        found = true
      }
    }
    return ""
  }
}
