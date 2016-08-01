package mu.internal


import org.junit.Assert.*
import org.junit.Test


class LogHelperTest {

    @Test
    fun testNames() {
        assertEquals("mu.internal.BaseClass", LogHelper.name(BaseClass::class.java))
        assertEquals("mu.internal.ChildClass", LogHelper.name(ChildClass::class.java))
        assertEquals("mu.internal.BaseClass", LogHelper.name(BaseClass.Companion::class.java))
        assertEquals("mu.internal.ChildClass", LogHelper.name(ChildClass.Companion::class.java))
        assertEquals("mu.internal.Singleton", LogHelper.name(Singleton::class.java))
        assertEquals("mu.internal.MyInterface", LogHelper.name(MyInterface::class.java))

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

