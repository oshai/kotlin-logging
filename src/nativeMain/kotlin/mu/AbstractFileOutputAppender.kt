package mu

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fputs
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

open class AbstractFileOutputAppender(aSaveMethod: (String) -> Unit) : Appender {

    private val channel = Channel<String>().freeze()

    init {
        // Check file
        aSaveMethod("")
        Worker.start().execute(TransferMode.SAFE, { Pair(channel, aSaveMethod.freeze()) }) {
            val channel = it.first
            val saveMethod = it.second
            while (true) {
                runBlocking {
                    val line = channel.receive()
                    saveMethod("$line\n")
                }
            }
        }
    }

    private fun log(message: Any?) {
        val line = message.toString()
        runBlocking {
            channel.send(line)
        }
    }

    override fun trace(message: Any?) = log(message)
    override fun debug(message: Any?) = log(message)
    override fun info(message: Any?) = log(message)
    override fun warn(message: Any?) = log(message)
    override fun error(message: Any?) = log(message)
}

fun posixSaveToFile(fileName: String, text: String) {
    val fp = fopen(fileName, "a")
        ?: throw RuntimeException("Can't open for write file: $fileName")
    try {
        fputs(text, fp)
    } finally {
        fclose(fp)
    }
}
