package mu

import org.slf4j.LoggerFactory

class ClassWithLoggingForLocationTesting {
    //    companion object: KLogging()
    val logger: org.slf4j.Logger = LoggerFactory.getLogger(this.javaClass)
    fun log() {
        logger.info("test")
    }
}
