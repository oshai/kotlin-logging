package io.github.oshai.kotlinlogging.internal

private const val NO_CLASS = ""

internal actual object KLoggerNameResolver {
  private val kotlinLoggingRegex = Regex("\\.KotlinLogging\\.logger\\s")
  private val topLevelPropertyRegex = Regex("<init properties (\\S+)\\.kt>")
  private val classPropertyRegex = Regex("\\.(\\S+)\\.<init>")

  internal actual fun name(func: () -> Unit): String {
    val stackTrace = Exception().stackTraceToString().split("\n")
    val invokingClassLine = stackTrace.indexOfFirst(kotlinLoggingRegex::containsMatchIn) + 1
    return if (invokingClassLine in 1 ..< stackTrace.size) {
      getInvokingClass(stackTrace[invokingClassLine])
    } else {
      NO_CLASS
    }
  }

  private fun getInvokingClass(line: String): String {
    return topLevelPropertyRegex.find(line)?.let { it.groupValues[1].split(".").last() }
      ?: classPropertyRegex.find(line)?.let { it.groupValues[1].split(".").last() }
      ?: NO_CLASS
  }
}
