package mu

import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fread
import platform.posix.sleep
import platform.posix.unlink
import kotlin.native.concurrent.freeze
import kotlin.test.assertEquals
import kotlin.test.Test

// We can use this test for all desktop platform (even mingw)
// because fileName is ANSI string and posix IO works fine

@Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")
class FileOutputLoggerTest {

    @Test
    fun fileWriteTest() {
        val fileName = "build/test.log"
        // Clear file
        fclose(fopen(fileName, "w")
            ?: throw RuntimeException("Can't open for write file: $fileName"))
        KotlinLoggingConfiguration.appender = FileOutputAppender(fileName).freeze()
        val logger = KotlinLogging.logger {}
        logger.info { "Test1" }
        logger.warn { "Test2" }
        // wait async write to long
        sleep(1)
        val fileContent = readFileContent(fileName)
        val lines = fileContent.split("\n")
        assertEquals(3, lines.size)
        assertEquals("INFO: ", lines[0].split("[")[0])
        assertEquals(" Test1", lines[0].split("]")[1])
        assertEquals("WARN: ", lines[1].split("[")[0])
        assertEquals(" Test2", lines[1].split("]")[1])
        assertEquals("", lines[2])
    }

    private fun readFileContent(fileName: String): String {
        val file = (fopen(fileName, "r")
            ?: throw RuntimeException("Can't open for write file: $fileName"))
        try {
            val builder = StringBuilder()
            memScoped {
                val bufferSize = 4096
                val buffer = ByteArray(bufferSize)
                while (true) {
                    val read = fread(buffer.refTo(0), 1, bufferSize.convert(), file).toInt()
                    if (read <= 0) {
                        break
                    }
                    builder.append(buffer.toKString())
                }
            }
            return builder.toString()
        } finally {
            fclose(file)
        }
    }

}
