package mu

import org.slf4j.Logger

/**
 * An extension for [Logger] with Lazy message evaluation
 * example:
 * logger.info{"this is $lazy evaluated string"}
 */
actual interface KXLogger : KLogger {

  actual fun entry(vararg argArray: Any)
  actual fun exit()
  actual fun <T> exit(retval: T): T where T : Any
  actual fun <T> throwing(throwable: T): T where T : Throwable
  actual fun <T> catching(throwable: T) where T : Throwable
}
