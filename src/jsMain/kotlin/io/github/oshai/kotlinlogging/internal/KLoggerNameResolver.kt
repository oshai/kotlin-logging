package io.github.oshai.kotlinlogging.internal

internal actual object KLoggerNameResolver {
  private const val DEFAULT_LOGGER_NAME = "root-logger"
  private const val LOGGER_FUNCTION_NAME = "kotlinLoggerByFunc"
  private const val COMPANION_GET_INSTANCE_SUFFIX = "_getInstance"
  private val TOP_LEVEL_INIT_PROPERTIES_REGEX = Regex("_init_properties_(\\S+)_kt_")
  private val CLASS_LEVEL_INIT_PROPERTIES_REGEX = Regex("new (\\S+)")

  internal actual fun name(func: () -> Unit): String {
    return findLoggerCallerClassName() ?: DEFAULT_LOGGER_NAME
  }

  private fun findLoggerCallerClassName(): String? {
    val stackTrace = Throwable().stackTraceToString().split('\n')
    val invokeLoggerLine = stackTrace.indexOfFirst { it.contains(LOGGER_FUNCTION_NAME) }
    if (invokeLoggerLine == -1 || invokeLoggerLine + 1 >= stackTrace.size) return null
    val callerLine = invokeLoggerLine + 1
    return resolveAsTopLevelProperty(stackTrace, callerLine)
      ?: resolveAsClassLevelProperty(stackTrace, callerLine)
  }

  private fun resolveAsTopLevelProperty(stackTrace: List<String>, callerLine: Int): String? {
    val found = TOP_LEVEL_INIT_PROPERTIES_REGEX.find(stackTrace[callerLine]) ?: return null
    return found.groupValues[1]
  }

  private fun resolveAsClassLevelProperty(stackTrace: List<String>, callerLine: Int): String? {
    val found = CLASS_LEVEL_INIT_PROPERTIES_REGEX.find(stackTrace[callerLine]) ?: return null
    val className = found.groupValues[1]
    // find enclosing class in case of Companion object:
    // new MyCompanion() <- found class name
    // MyCompanion_getInstance()
    // new MyClass()     <- enclosing class
    if (
      callerLine + 2 >= stackTrace.size ||
        !stackTrace[callerLine + 1].contains("$className$COMPANION_GET_INSTANCE_SUFFIX")
    ) {
      return className
    }
    val enclosingFound =
      CLASS_LEVEL_INIT_PROPERTIES_REGEX.find(stackTrace[callerLine + 2]) ?: return className
    return enclosingFound.groupValues[1]
  }
}
