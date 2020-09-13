package mu

import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.windows.CloseHandle
import platform.windows.CreateFileW
import platform.windows.FILE_ATTRIBUTE_NORMAL
import platform.windows.GENERIC_WRITE
import platform.windows.HANDLE
import platform.windows.INVALID_HANDLE_VALUE
import platform.windows.OPEN_ALWAYS
import platform.windows.WriteFile
import platform.windows._OVERLAPPED

actual class FileOutputAppender actual constructor(fileName: String)
    : AbstractFileOutputAppender({ saveToFile(fileName, it) })

// fopen doesn't work with non-ANSI file names in Windows
private fun saveToFile(fileName: String, text: String) {
    val cstr = text.cstr
    val cstrLen = cstr.size - 1 // last 0 removed
    val hFile = openFile(fileName, OPEN_ALWAYS)
    try {
        memScoped {
            // Write to end of file
            val overlapped = alloc<_OVERLAPPED>()
            overlapped.Offset = 0xFFFFFFFFu
            overlapped.OffsetHigh = 0xFFFFFFFFu
            WriteFile(hFile, cstr.ptr, cstrLen.toUInt(), null, overlapped.ptr)
        }
    } finally {
        CloseHandle(hFile)
    }
}

@ExperimentalUnsignedTypes
private fun openFile(fileName: String, mode: Int): HANDLE {
    val hFile = requireNotNull(
        CreateFileW(
            fileName,
            GENERIC_WRITE.toUInt(),
            0u,
            null,
            mode.toUInt(),
            FILE_ATTRIBUTE_NORMAL.toUInt(),
            null
        )
    )
    if (hFile == INVALID_HANDLE_VALUE) {
        throw RuntimeException(" Unable to create file $fileName")
    }
    return hFile
}
