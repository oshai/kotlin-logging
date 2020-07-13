package mu

class ClassWithLoggingForLocationTesting {
    companion object : KLogging()

    fun log() {
        logger.info { "test" }
    }

    fun logLazy() {
        logger.info { "test" }
    }

    fun logNull() {
        logger.info { null }
    }

    fun logEntry(): Pair<Int, Int> {
        logger.entry(1, 2)
        logger.info { "log entry body" }
        return logger.exit(2 to 1)
    }

    fun logExitOpt(): Pair<Int, Int>? {
        logger.entry(1, 2)
        logger.info { "log entry body" }
        return logger.exit(null)
    }
}
