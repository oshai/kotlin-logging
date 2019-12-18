package mu.internal

interface IAppender {
    fun debug(message: String?)
    fun info(message: String?)
    fun warn(message: String?)
    fun error(message: String?)
    fun fatal(message: String?)
}
