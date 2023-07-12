package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import io.github.oshai.kotlinlogging.slf4j.toSlf4j
import org.slf4j.Logger

@Suppress("DeprecatedCallableAddReplaceWith")
public abstract class Slf4jLogger<T: Logger> : KLogger, DelegatingKLogger<T> {

  // we don't move more methods to here because if it will appear on stacktrace
  // it will break fqcn for class name in location aware loggers
  // (tests are also failing when doing this)

  protected fun isLoggingEnabledFor(
    underlyingLogger: Logger,
    level: Level,
    marker: Marker?
  ): Boolean {
    return when (level) {
      Level.TRACE -> underlyingLogger.isTraceEnabled(marker?.toSlf4j())
      Level.DEBUG -> underlyingLogger.isDebugEnabled(marker?.toSlf4j())
      Level.INFO -> underlyingLogger.isInfoEnabled(marker?.toSlf4j())
      Level.WARN -> underlyingLogger.isWarnEnabled(marker?.toSlf4j())
      Level.ERROR -> underlyingLogger.isErrorEnabled(marker?.toSlf4j())
      Level.OFF -> false
    }
  }

    ///
    /// The methods below are deprecated and will be removed in the future
    ///


    @Deprecated("Use trace {} instead") public override fun trace(msg: String?, arg: Any?): Unit =
        underlyingLogger.trace(msg, arg)


    @Deprecated("Use trace {} instead")
    public override fun trace(msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.trace(msg, arg1, arg2)


    @Deprecated("Use trace {} instead")
    public override fun trace(msg: String?, vararg arguments: Any?): Unit = underlyingLogger.trace(msg, *arguments)


    @Deprecated("Use trace {} instead")
    public override fun trace(marker: Marker?, msg: String?, arg: Any?): Unit = underlyingLogger.trace(marker?.toSlf4j(), msg, arg)


    @Deprecated("Use trace {} instead")
    public override fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.trace(marker?.toSlf4j(), msg, arg1, arg2)


    @Deprecated("Use trace {} instead")
    public override fun trace(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = underlyingLogger.trace(marker?.toSlf4j(), msg, *arguments)




    @Deprecated("Use debug {} instead") public override fun debug(msg: String?, arg: Any?): Unit = underlyingLogger.debug(msg, arg)


    @Deprecated("Use debug {} instead")
    public override fun debug(msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.debug(msg, arg1, arg2)


    @Deprecated("Use debug {} instead")
    public override fun debug(msg: String?, vararg arguments: Any?): Unit = underlyingLogger.debug(msg, *arguments)



    @Deprecated("Use debug {} instead")
    public override fun debug(marker: Marker?, msg: String?, arg: Any?): Unit = underlyingLogger.debug(marker?.toSlf4j(), msg, arg)


    @Deprecated("Use debug {} instead")
    public override fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.debug(marker?.toSlf4j(), msg, arg1, arg2)


    @Deprecated("Use debug {} instead")
    public override fun debug(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = underlyingLogger.debug(marker?.toSlf4j(), msg, *arguments)



    @Deprecated("Use info {} instead") public override fun info(msg: String?, arg: Any?): Unit = underlyingLogger.info(msg, arg)


    @Deprecated("Use info {} instead")
    public override fun info(msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.info(msg, arg1, arg2)


    @Deprecated("Use info {} instead")
    public override fun info(msg: String?, vararg arguments: Any?): Unit = underlyingLogger.info(msg, *arguments)




    @Deprecated("Use info {} instead")
    public override fun info(marker: Marker?, msg: String?, arg: Any?): Unit = underlyingLogger.info(marker?.toSlf4j(), msg, arg)


    @Deprecated("Use info {} instead")
    public override fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.info(marker?.toSlf4j(), msg, arg1, arg2)


    @Deprecated("Use info {} instead")
    public override fun info(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = underlyingLogger.info(marker?.toSlf4j(), msg, *arguments)



    @Deprecated("Use warn {} instead") public override fun warn(msg: String?, arg: Any?): Unit = underlyingLogger.warn(msg, arg)


    @Deprecated("Use warn {} instead")
    public override fun warn(msg: String?, vararg arguments: Any?): Unit = underlyingLogger.warn(msg, *arguments)


    @Deprecated("Use warn {} instead")
    public override fun warn(msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.warn(msg, arg1, arg2)



    @Deprecated("Use warn {} instead")
    public override fun warn(marker: Marker?, msg: String?, arg: Any?): Unit = underlyingLogger.warn(marker?.toSlf4j(), msg, arg)


    public override fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.warn(marker?.toSlf4j(), msg, arg1, arg2)


    @Deprecated("Use warn {} instead")
    public override fun warn(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = underlyingLogger.warn(marker?.toSlf4j(), msg, *arguments)


    @Deprecated("Use error {} instead") public override fun error(msg: String?, arg: Any?): Unit = underlyingLogger.error(msg, arg)


    @Deprecated("Use error {} instead")
    public override fun error(msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.error(msg, arg1, arg2)


    @Deprecated("Use error {} instead")
    public override fun error(msg: String?, vararg arguments: Any?): Unit = underlyingLogger.error(msg, *arguments)


        public override fun error(marker: Marker?, msg: String?, arg: Any?): Unit = underlyingLogger.error(marker?.toSlf4j(), msg, arg)


    @Deprecated("Use error(marker){} instead")
    public override fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit = underlyingLogger.error(marker?.toSlf4j(), msg, arg1, arg2)


    @Deprecated("Use error(marker){} instead")
    public override fun error(marker: Marker?, msg: String?, vararg arguments: Any?): Unit = underlyingLogger.error(marker?.toSlf4j(), msg, *arguments)

}
