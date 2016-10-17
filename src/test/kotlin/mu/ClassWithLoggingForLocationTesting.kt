package mu

import org.slf4j.LoggerFactory

class ClassWithLoggingForLocationTesting {
    companion object : KLogging()

    fun log() {
        logger.info("test")
    }

    fun logLazy() {
        logger.info{"test"}
    }
}
