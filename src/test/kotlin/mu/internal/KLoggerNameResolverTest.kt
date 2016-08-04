package mu.internal


import org.junit.Assert.*
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

    }
}

open class BaseClass{
    companion object
}
class ChildClass: BaseClass(){
    companion object
}
object Singleton
interface MyInterface

