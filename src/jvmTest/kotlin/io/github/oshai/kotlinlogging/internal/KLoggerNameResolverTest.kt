package io.github.oshai.kotlinlogging.internal

import java.util.stream.Stream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KLoggerNameResolverTest {

  @ParameterizedTest
  @MethodSource("testNames")
  fun testNames(expectedName: String, clazz: Class<*>) {
    assertEquals(expectedName, KLoggerNameResolver.name(clazz))
  }

  private fun testNames(): Stream<Arguments> =
    Stream.of(
      Arguments.of("io.github.oshai.kotlinlogging.internal.BaseClass", BaseClass::class.java),
      Arguments.of("io.github.oshai.kotlinlogging.internal.ChildClass", ChildClass::class.java),
      Arguments.of("io.github.oshai.kotlinlogging.internal.BaseClass", BaseClass.Companion::class.java),
      Arguments.of("io.github.oshai.kotlinlogging.internal.ChildClass", ChildClass.Companion::class.java),
      Arguments.of("io.github.oshai.kotlinlogging.internal.Singleton", Singleton::class.java),
      Arguments.of("io.github.oshai.kotlinlogging.internal.MyInterface", MyInterface::class.java),
      Arguments.of("java.lang.Object", Any().javaClass),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.KLoggerNameResolverTest\$testNames$1",
        object {}.javaClass
      ),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.BaseClass\$InnerClass\$Obj",
        BaseClass.InnerClass.Obj::class.java,
      ),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.BaseClass\$InnerClass\$Obj",
        BaseClass.InnerClass.Obj.javaClass,
      ),
      Arguments.of(
        "io.github.oshai.kotlinlogging.internal.BaseClass\$InnerClass",
        BaseClass.InnerClass.CmpObj::class.java,
      ),
      Arguments.of("io.github.oshai.kotlinlogging.internal.Foo\$Bar", Foo.Bar::class.java),
      Arguments.of("io.github.oshai.kotlinlogging.internal.Foo\$Bar2", Foo.Bar3.javaClass),
      Arguments.of("io.github.oshai.kotlinlogging.internal.PrivateCompanion", PrivateCompanion().companionClass)
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
  val companionClass: Class<*> = Companion::class.java
  private companion object
}
