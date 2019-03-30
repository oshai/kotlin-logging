package mu


expect interface KXLogger : KLogger {

    fun entry(vararg argArray: Any)
    fun exit()
    fun <T> exit(retval: T): T where T : Any
    fun <T> throwing(throwable: T): T where T : Throwable
    fun <T> catching(throwable: T) where T : Throwable
}
