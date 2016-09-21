package mu.internal


import org.junit.Assert.*
import org.junit.Test


class KLoggerNameResolverTest {

    @Test
    fun testNames() {
        assertEquals("mu.internal.BaseClass", KLoggerNameResolver.name(BaseClass()))
        assertEquals("mu.internal.ChildClass", KLoggerNameResolver.name(ChildClass()))
        assertEquals("mu.internal.BaseClass", KLoggerNameResolver.name(BaseClass.Companion))
        assertEquals("mu.internal.ChildClass", KLoggerNameResolver.name(ChildClass.Companion))
        assertEquals("mu.internal.Singleton", KLoggerNameResolver.name(Singleton))
        assertEquals("java.lang.Object", KLoggerNameResolver.name(Any()))
        assertEquals("mu.internal.KLoggerNameResolverTest\$testNames$1", KLoggerNameResolver.name(object {}))
        assertEquals("mu.internal.BaseClass\$InnerClass\$Obj", KLoggerNameResolver.name(BaseClass.InnerClass.Obj))
        assertEquals("mu.internal.BaseClass\$InnerClass\$Obj", KLoggerNameResolver.name(BaseClass.InnerClass.Obj))
        assertEquals("mu.internal.BaseClass\$InnerClass", KLoggerNameResolver.name(BaseClass.InnerClass.CmpObj))
        assertEquals("mu.internal.BaseClass\$InnerClass", KLoggerNameResolver.name(BaseClass.InnerClass.CmpObj))
        assertEquals("mu.internal.Foo\$Bar", KLoggerNameResolver.name(Foo.Bar))
        assertEquals("""
                        This is a known issue that we currently do not have a solution for
                        Foo.Bar2 is not a companion object, but still unwrapping occurs
                        """, "mu.internal.Foo", KLoggerNameResolver.name(Foo.Bar2))

    }
}

open class BaseClass{
    companion object
    class InnerClass {
        object Obj
        companion object CmpObj
    }
}
class ChildClass: BaseClass(){
    companion object
}
object Singleton

@Suppress("unused")
class Foo {
    object Bar
    object Bar2
    val z = Bar2

    companion object {
        @JvmField
        val Bar = this

        @JvmField
        val Bar2 = Foo().z
    }
}
