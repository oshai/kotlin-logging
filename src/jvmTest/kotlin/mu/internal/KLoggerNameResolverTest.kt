package mu.internal


import org.junit.Assert.assertEquals
import org.junit.Test


class KLoggerNameResolverTest {

    @Test
    fun testNames() {
        assertEquals("mu.internal.BaseClass", KLoggerNameResolver.name(BaseClass::class.java))
        assertEquals("mu.internal.ChildClass", KLoggerNameResolver.name(ChildClass::class.java))
        assertEquals("mu.internal.BaseClass", KLoggerNameResolver.name(BaseClass.Companion::class.java))
        assertEquals("mu.internal.ChildClass", KLoggerNameResolver.name(ChildClass.Companion::class.java))
        assertEquals("mu.internal.Singleton", KLoggerNameResolver.name(Singleton::class.java))
        assertEquals("mu.internal.MyInterface", KLoggerNameResolver.name(MyInterface::class.java))
        assertEquals("java.lang.Object", KLoggerNameResolver.name(Any().javaClass))
        assertEquals("mu.internal.KLoggerNameResolverTest\$testNames$1", KLoggerNameResolver.name(object {}.javaClass))
        assertEquals(
            "mu.internal.BaseClass\$InnerClass\$Obj",
            KLoggerNameResolver.name(BaseClass.InnerClass.Obj::class.java)
        )
        assertEquals(
            "mu.internal.BaseClass\$InnerClass\$Obj",
            KLoggerNameResolver.name(BaseClass.InnerClass.Obj.javaClass)
        )
        assertEquals(
            "mu.internal.BaseClass\$InnerClass",
            KLoggerNameResolver.name(BaseClass.InnerClass.CmpObj::class.java)
        )
        assertEquals(
            "mu.internal.BaseClass\$InnerClass",
            KLoggerNameResolver.name(BaseClass.InnerClass.CmpObj::class.java)
        )
        assertEquals("mu.internal.Foo\$Bar", KLoggerNameResolver.name(Foo.Bar::class.java))
        assertEquals("mu.internal.Foo\$Bar2", KLoggerNameResolver.name(Foo.Bar3.javaClass))
        assertEquals("mu.internal.PrivateCompanion", KLoggerNameResolver.name(PrivateCompanion().companionClass))
    }
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
        @JvmField
        val Bar3 = Foo().z
    }
}

class PrivateCompanion {
    val companionClass: Class<*> = Companion::class.java
    private companion object
}
