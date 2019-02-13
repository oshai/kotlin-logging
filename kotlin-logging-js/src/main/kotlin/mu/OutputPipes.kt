package mu

interface OutputPipes {
    fun trace(message: Any?)
    fun debug(message: Any?)
    fun info(message: Any?)
    fun warn(message: Any?)
    fun error(message: Any?)
}