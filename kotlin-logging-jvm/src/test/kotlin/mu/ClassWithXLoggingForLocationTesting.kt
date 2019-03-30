package mu

class ClassWithXLoggingForLocationTesting {
    companion object : KXLogging()

    fun log() {
        xlogger.entry(1)
        xlogger.info("test")
        xlogger.exit()
    }

    fun logLazy() {
        xlogger.entry(2)
        xlogger.info { "test" }
        xlogger.exit()
    }

    fun logException() {
        xlogger.entry(3)
        try {
            xlogger.throwing(RuntimeException("sample exception"))
        } catch (x: Throwable) {
            xlogger.catching(x)
        }
        xlogger.exit()
    }

    fun logNull() {
        xlogger.entry(3)
        xlogger.info(null)
        xlogger.exit()
    }
}
