package io.github.oshai.kotlinlogging.internal

private const val NO_CLASS = ""

internal actual object KLoggerNameResolver {
  // In WASI the stacktrace is often empty; derive from lambda's synthetic class name when possible.
  internal actual fun name(func: () -> Unit): String {
    val qn = func::class.qualifiedName
    // Examples:
    //  - "SimpleWasmWasiTest$anonymousClassPropLogger$lambda" -> "SimpleWasmWasiTest"
    //  - "anonymousFilePropLogger$lambda" -> unknown (should NOT use this)
    if (qn != null) {
      val base = qn.substringBefore('$')
      // Heuristic: class names in Kotlin usually start with uppercase; avoid returning synthetic
      // top-level property names like 'anonymousFilePropLogger'.
      if (base.isNotEmpty() && base.first().isUpperCase()) {
        return base
      }
    }

    // Fallback: Try to derive from stack by locating a .kt source file and using its base name.
    val fileName = tryExtractFileNameFromJsStack()
    return fileName ?: NO_CLASS
  }

  private fun tryExtractFileNameFromJsStack(): String? {
    val st = Exception().stackTraceToString()
    if (st.isBlank()) return null
    // Try to find something like '/path/.../SimpleWasmWasiTest.kt' or 'SimpleWasmWasiTest.kt'
    val regex = Regex("""([A-Za-z0-9_]+)\.kt""")
    for (line in st.lineSequence()) {
      val match = regex.find(line)
      if (match != null) {
        val base = match.groupValues[1]
        if (base.isNotEmpty()) return base
      }
    }
    // Fallback similar to jsMain approach: take the token after last '/' and before '.kt'.
    for (line in st.lineSequence()) {
      if (".kt" in line) {
        val preKt = line.substringBefore(".kt")
        val afterSlash = preKt.substringAfterLast('/')
        val afterBackslash = afterSlash.substringAfterLast('\\')
        val afterDot = afterBackslash.substringAfterLast('.')
        if (afterDot.isNotEmpty()) return afterDot
      }
    }
    return null
  }
}
