package io.github.oshai.kotlinlogging.internal

import com.oracle.svm.core.annotate.Substitute
import com.oracle.svm.core.annotate.TargetClass
import com.oracle.svm.core.annotate.TargetElement
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.jul.internal.JulLoggerFactory
import io.github.oshai.kotlinlogging.slf4j.internal.Slf4jLoggerFactory
import java.util.function.BooleanSupplier

/**
 * This class is a substitution class as described in
 * [https://build-native-java-apps.cc/developer-guide/substitution/]. It fixes an issue where the
 * native build (GraalVM native-image) fails if Logback is not on the classpath. See
 * [https://github.com/oshai/kotlin-logging/issues/465]. The weird-looking class name is according
 * to convention.
 */
@Suppress("unused")
internal class Target_io_github_oshai_kotlinlogging_internal_KLoggerFactory {
  @TargetClass(
    className = "io.github.oshai.kotlinlogging.internal.KLoggerFactory",
    onlyWith = [KLoggerFactoryExistsAndLogbackNotOnClasspath::class],
  )
  companion object {
    @Substitute
    @TargetElement(name = "logger\$kotlin_logging")
    fun logger(name: String): KLogger {
      if (System.getProperty("kotlin-logging-to-jul") != null) {
        return JulLoggerFactory.wrapJLogger(JulLoggerFactory.jLogger(name))
      }
      // Intentionally leave out the logback branch as logback is not on the classpath.
      // default to SLF4J
      return Slf4jLoggerFactory.wrapJLogger(Slf4jLoggerFactory.jLogger(name))
    }
  }
}

/**
 * Condition that returns true only when:
 * 1. The substitution target class (`io.github.oshai.kotlinlogging.internal.KLoggerFactory`)
 *    actually exists on the classpath (it was removed in v8.x), AND
 * 2. Logback is not on the classpath.
 *
 * This prevents a GraalVM native image build error when the target class no longer exists. See
 * [https://github.com/oshai/kotlin-logging/issues/385].
 */
internal class KLoggerFactoryExistsAndLogbackNotOnClasspath : BooleanSupplier {
  override fun getAsBoolean(): Boolean {
    // First check if the substitution target class exists (it doesn't in v8.x+).
    // If it doesn't exist, the substitution must be inactive to avoid a native build error.
    try {
      Class.forName("io.github.oshai.kotlinlogging.internal.KLoggerFactory")
    } catch (_: ClassNotFoundException) {
      return false
    }
    // Target class exists; only apply substitution when Logback is not on the classpath.
    try {
      Class.forName("ch.qos.logback.classic.LoggerContext")
      return false
    } catch (_: ClassNotFoundException) {
      return true
    }
  }
}
