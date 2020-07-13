package mu

class MockedKLogger(override val name: String) : KLogger {
    override fun trace(msg: () -> Any?) = throw NotImplementedError()

    override fun trace(t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun trace(marker: Marker?, msg: () -> Any?) = throw NotImplementedError()

    override fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun debug(msg: () -> Any?) = throw NotImplementedError()

    override fun debug(t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun debug(marker: Marker?, msg: () -> Any?) = throw NotImplementedError()

    override fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun info(msg: () -> Any?) = throw NotImplementedError()

    override fun info(t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun info(marker: Marker?, msg: () -> Any?) = throw NotImplementedError()

    override fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun warn(msg: () -> Any?) = throw NotImplementedError()

    override fun warn(t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun warn(marker: Marker?, msg: () -> Any?) = throw NotImplementedError()

    override fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun error(msg: () -> Any?) = throw NotImplementedError()

    override fun error(t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun error(marker: Marker?, msg: () -> Any?) = throw NotImplementedError()

    override fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) = throw NotImplementedError()

    override fun entry(vararg argArray: Any?) = throw NotImplementedError()

    override fun exit() = throw NotImplementedError()

    override fun <T> exit(result: T): T = throw NotImplementedError()

    override fun <T : Throwable> throwing(throwable: T): T = throw NotImplementedError()

    override fun <T : Throwable> catching(throwable: T) = throw NotImplementedError()

}
