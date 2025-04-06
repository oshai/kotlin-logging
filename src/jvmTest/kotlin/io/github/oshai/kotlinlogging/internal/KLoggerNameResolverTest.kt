package io.github.oshai.kotlinlogging.internal

import java.util.stream.Stream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KClass

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KLoggerNameResolverTest {

  @ParameterizedTest
  @MethodSource("testNames")
  fun testNames(expectedName: String, clazz: KClass<*>) {
    assertEquals(expectedName, KLoggerNameResolver.name(clazz))
  }

  private fun testNames(): Stream<Arguments> =
    Stream.of(
      Arguments.of("io.github.oshai.kotlinlogging.internal.BaseClass", BaseClass::class),
      Arguments.of("io.github.oshai.kotlinlogging.internal.ChildClass", ChildClass::class),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.BaseClass",
        BaseClass.Companion::class,
      ),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.ChildClass",
        ChildClass.Companion::class,
      ),
      Arguments.of("io.github.oshai.kotlinlogging.internal.Singleton", Singleton::class),
      Arguments.of("io.github.oshai.kotlinlogging.internal.MyInterface", MyInterface::class),
      Arguments.of("java.lang.Object", Any()::class),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.KLoggerNameResolverTest",
        object {}::class,
      ),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.BaseClass",
        BaseClass.InnerClass.Obj::class,
      ),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.BaseClass",
        BaseClass.InnerClass.CmpObj::class,
      ),
      Arguments.of("io.github.oshai.kotlinlogging.internal.Foo", Foo.Bar::class),
      Arguments.of("io.github.oshai.kotlinlogging.internal.Foo", Foo.Bar3::class),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.PrivateCompanion",
        PrivateCompanion().companionClass,
      ),
    )
}

open class BaseClass {
  companion object

  class InnerClass {
    object Obj

    companion object CmpObj
  }
}

class ChildClass : BaseClass() {
  companion object
}

object Singleton

interface MyInterface

@Suppress("unused")
class Foo {
  object Bar

  object Bar2

  val z = Bar2

  companion object {
    @JvmField val Bar3 = Foo().z
  }
}

class PrivateCompanion {
  val companionClass: KClass<*> = Companion::class

  private companion object
}
