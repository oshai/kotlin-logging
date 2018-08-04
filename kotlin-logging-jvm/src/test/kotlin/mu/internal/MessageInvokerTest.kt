package mu.internal

import org.junit.Test
import kotlin.test.assertEquals

//import kotlin.test.Test
//import kotlin.test.assertEquals


class MessageInvokerTest {

    @Test
    fun `toStringSafe checks`() {
       assertEquals(Unit.toString(), {} .toStringSafe())
       assertEquals("null", {null} .toStringSafe())
       assertEquals("hi", {"hi"} .toStringSafe())
    }
}
