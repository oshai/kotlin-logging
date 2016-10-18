package mu

class ClassWithLoggingForLocationTesting {
    companion object : KLogging()

    fun log() {
        logger.info("test")
    }

    fun logLazy() {
        logger.info{"test"}
    }
}
