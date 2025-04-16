package io.github.oshai.kotlinlogging.internal

import kotlin.reflect.KClass

/** Resolves name of java classes */
internal actual object KLoggerNameResolver {

  /** get class name for function by the package of the function */
  internal actual fun name(func: () -> Unit): String {
    return name(func::class)
  }

  internal fun name(clazz: KClass<*>): String {
    return clazz.java.name.toCleanClassName()
  }

  private val classNameEndings = listOf("Kt$", "$")

  private fun String.toCleanClassName(): String {
    classNameEndings.forEach { ending ->
      val indexOfEnding = this.indexOf(ending)
      if (indexOfEnding != -1) {
        return this.substring(0, indexOfEnding)
      }
    }
    return this
  }
}
