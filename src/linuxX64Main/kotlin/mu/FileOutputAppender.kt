package mu

actual class FileOutputAppender actual constructor(fileName: String)
    : AbstractFileOutputAppender({ posixSaveToFile(fileName, it) })
