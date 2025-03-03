package io.github.oshai.kotlinlogging

import io.github.oshai.kotlinlogging.internal.KLoggerFactory
import io.github.oshai.kotlinlogging.internal.KLoggerNameResolver
import kotlin.js.JsName

/**
 * Since this library is intended to be multiplatform,
 * there are several ways to create a logger depending on your programming language.
 *
 * Please note, that creating a logger by reference involves reflection or, in some cases,
 * stack trace analysis. Therefore, if performance is a priority, avoid creating loggers
 * in frequently executed code sections.
 *
 * A logger created by reference will take the name of the class of the reference.
 * If the reference is a "companion object," the name of its enclosing class will be used instead.
 *
 * ```kotlin
 * val topLevelNamedLogger = KotlinLogging.logger("TopLevelNamedLogger")
 * val topLevelLambdaLogger = KotlinLogging.logger {}
 *
 * class MyClass {
 *   val classNamedLogger = KotlinLogging.logger("MyClass")
 *   val classLambdaLogger = KotlinLogging.logger {}
 *   val classRefLogger = KotlinLogging.logger(this)
 *
 *   companion object {
 *     val companionNamedLogger = KotlinLogging.logger("MyClassCompanion")
 *     val companionLambdaLogger = KotlinLogging.logger {}
 *     val companionRefLogger = KotlinLogging.logger(this)
 *   }
 * }
 * ```
 */
public object KotlinLogging {
  @JsName("kotlinLoggerByRef")
  public fun logger(ref: Any): KLogger = logger(KLoggerNameResolver.name(ref))

  @JsName("kotlinLoggerByName")
  public fun logger(name: String): KLogger = KLoggerFactory.logger(name)
}
