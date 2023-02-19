package io.github.oshai.internal

@Suppress("NOTHING_TO_INLINE")
internal actual object KLoggerNameResolver {

  internal actual inline fun name(noinline func: () -> Unit): String {
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
