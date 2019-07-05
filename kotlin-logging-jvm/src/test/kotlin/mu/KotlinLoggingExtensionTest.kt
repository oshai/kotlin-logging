package mu

import org.junit.Assert
import org.junit.Test

class TestClass01 {
    val loggerInClass = logger()
    val logPropertyInClass = log

    companion object {
        val loggerInCompanion = logger()
        val logPropertyInCompanion = log
    }
}

class TestClass02 {
    val loggerInClass = logger()
    val logPropertyInClass = log

    companion object {
        val loggerInCompanion = logger()
        val logPropertyInCompanion = log
    }
}

class KotlinLoggingExtensionTest {
    private val loggerInTester = logger()
    private val logPropertyInTester = log

    @Test
    fun testLoggerName() {
        val loggerNameTesterClass = "mu.KotlinLoggingExtensionTest"
        Assert.assertEquals(loggerNameTesterClass, loggerInTester.name)
        Assert.assertEquals(loggerNameTesterClass, logPropertyInTester.name)
        Assert.assertEquals(loggerNameTesterClass, log.name)

        val loggerNameTestClass01 = "mu.TestClass01"
        val instance01 = TestClass01()
        Assert.assertEquals(loggerNameTestClass01, instance01.loggerInClass.name)
        Assert.assertEquals(loggerNameTestClass01, instance01.logPropertyInClass.name)
        Assert.assertEquals(loggerNameTestClass01, instance01.log.name)
        Assert.assertEquals(loggerNameTestClass01, TestClass01.loggerInCompanion.name)
        Assert.assertEquals(loggerNameTestClass01, TestClass01.logPropertyInCompanion.name)
        Assert.assertEquals(loggerNameTestClass01, TestClass01.log.name)

        val loggerNameTestClass02 = "mu.TestClass02"
        val instance02 = TestClass02()
        Assert.assertEquals(loggerNameTestClass02, instance02.loggerInClass.name)
        Assert.assertEquals(loggerNameTestClass02, instance02.logPropertyInClass.name)
        Assert.assertEquals(loggerNameTestClass02, instance02.log.name)
        Assert.assertEquals(loggerNameTestClass02, TestClass02.loggerInCompanion.name)
        Assert.assertEquals(loggerNameTestClass02, TestClass02.logPropertyInCompanion.name)
        Assert.assertEquals(loggerNameTestClass02, TestClass02.log.name)
    }
}
