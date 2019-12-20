package mu.internal

import kotlinx.cinterop.*
import platform.posix.*


abstract class AFormatter {
    abstract fun makeFormat(): String
    override fun toString(): String {
        return makeFormat()
    }
}

/**
 * Gets a date struct, [upstream docs](http://www.cplusplus.com/reference/ctime/tm/)
 */
fun getDateStruct() = memScoped {
    val t = alloc<time_tVar>()
    t.value = time(null)
    val local = gmtime(t.ptr)?.pointed
    local
}

object TimeStamp : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val timeVal = alloc<timeval>()
        gettimeofday(timeVal.ptr, null)
        val sec = timeVal.tv_sec
        val usec = timeVal.tv_usec
        val timStamp = ((sec * 1_000L) + (usec / 1_000L))
        timStamp.toString()
    }
}

object Month : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val local = getDateStruct()
        "${local?.tm_mon?.plus(1)}"
    }
}

object Day : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val local = getDateStruct()
        "${local?.tm_mday}"
    }
}

object Year : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val local = getDateStruct()
        "${local?.tm_year?.plus(1900)}"
    }
}

object TimeZone : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val local = getDateStruct()
        "${local?.tm_zone?.toKString()}"
    }
}

object Hour : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val local = getDateStruct()
        "${local?.tm_hour}"
    }
}

object Minute : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val local = getDateStruct()
        val minute = local?.tm_min ?: 0
        if (minute < 10) {
            "0${minute}"
        } else {
            minute.toString()
        }
    }
}

object Second : AFormatter() {
    override fun makeFormat(): String = memScoped {
        val local = getDateStruct()
        val second = local?.tm_sec ?: 0
        if (second < 10) {
            "0${second}"
        } else {
            second.toString()
        }
    }
}
