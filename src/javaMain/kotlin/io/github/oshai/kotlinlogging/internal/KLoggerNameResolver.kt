package io.github.oshai.kotlinlogging.internal

import java.lang.reflect.Modifier

/** Resolves name of java classes */
internal actual object KLoggerNameResolver {

  /** get class name for function by the package of the function */
  internal actual fun name(ref: Any): String {
    return ref::class.java.name.toCleanClassName()
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

  /** get class name for java class (that usually represents kotlin class) */
  internal fun <T : Any> name(forClass: Class<T>): String = unwrapCompanionClass(forClass).name

  /** unwrap companion class to enclosing class given a Java Class */
  private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
    return clazz.enclosingClass?.let { enclosingClass ->
      try {
        enclosingClass.declaredFields
          .find { field ->
            field.name == clazz.simpleName &&
              Modifier.isStatic(field.modifiers) &&
              field.type == clazz
          }
          ?.run { enclosingClass }
      } catch (se: SecurityException) {
        // The security manager isn't properly set up, so it won't be possible
        // to search for the target declared field.
        null
      }
    } ?: clazz
  }
}
