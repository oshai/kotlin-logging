# Kotlin (IR) compiler plugin for kotlin-logging

Kotlin (IR) compiler plugin for kotlin-logging that adds the following features when used with [Logback](https://logback.qos.ch) backend
(see also [ILoggingEvent](https://github.com/qos-ch/logback/blob/master/logback-classic/src/main/java/ch/qos/logback/classic/spi/ILoggingEvent.java)):

* Collect (during compile-time) and fill in log message template returned from `ILoggingEvent.getMessage()` method.
  Fully formatted message is returned from `ILoggingEvent.getFormattedMessage()` method.
  As a side-effect of this feature, `ILoggingEvent.getArgumentArray()` will no longer be returning the argument values
  (as the message template is merged with arguments during compile-time).
* Collect (during compile-time) and fill in caller data returned from `ILoggingEvent.getCallerData()` method. Typically,
  this is implemented by throwing an exception at runtime and capturing the stack trace. This plugin will capture the call site
  during compile-time and fill in the caller data (so you get it for free at runtime).

In short, it transforms code like this:
```kotlin
package test
import io.github.oshai.kotlinlogging.*

class MainTest {
  val logger = KotlinLogging.logger {}
  fun main() {
    val name = "world"
    logger.info { "Hello, $name" }
  }
}
```
into this:
```kotlin
package test
import io.github.oshai.kotlinlogging.*

class MainTest {
  val logger = KotlinLogging.logger {}
  fun main() {
    val name = "world"
    logger.at(Level.INFO) {
      message = "Hello, $name"
      cause = throwable
      internalCompilerData = KLoggingEventBuilder.InternalCompilerData(
        messageTemplate = "\"Hello, \$name\"",
        className = "test.MainTest",
        methodName = "main",
        fileName = "test.kt",
        lineNumber = 8
      )
    }
  }
}
```

For more details, see [What does it do (in detail)?](#what-does-it-do-in-detail) section.

## Usage

Add the following to the `build.gradle.kts` of your Kotlin project:

```kotlin
plugins {
  id("io.github.oshai.kotlinlogging.kotlin-ir-plugin") version "<fill in latest version>"
}

dependencies {
  // Add kotlin-logging dependency
  // Optional: add Logback dependency
}

// Optional: configure the plugin (by default all features are enabled)
kotlinlogging {
  disableAll = false
  disableTransformingDeprecatedApi = false
  disableTransformingNotImplementedApi = false
  disableTransformingEntryExitApi = false
  disableTransformingThrowingCatchingApi = false
  disableCollectingCallSiteInformation = false
}
```

**Note:** in order to use log message template feature you will need to use Logback as your logging backend (and
to set `kotlin-logging-to-logback` system property to `true` in your application JVM). Other logging backends do not
support passing through the message template to the log appender.

## Feature flags

The plugin can be configured using the following flags:

* `disableAll` - disables all features of the plugin (default: `false`), all code is left unchanged.
* `disableTransformingDeprecatedApi` - disables transforming deprecated `KLogger` API calls (default: `false`).
* `disableTransformingNotImplementedApi` - disables transforming `KLogger` API calls that have not been implemented (default: `false`).
* `disableTransformingEntryExitApi` - disables transforming `KLogger` API calls for entry/exit logging (default: `false`).
* `disableTransformingThrowingCatchingApi` - disables transforming `KLogger` API calls for throwing/catching exceptions (default: `false`).
* `disableCollectingCallSiteInformation` - disables collecting call site information (default: `false`).

## What does it do (in detail)?

The plugin is implemented using Kotlin IR compiler plugin API. It hooks into the Kotlin compiler and modifies the AST
(Abstract Syntax Tree) of the project code. The plugin is applied to the whole project and it modifies all the classes that
use the [https://github.com/oshai/kotlin-logging/blob/master/src/commonMain/kotlin/io/github/oshai/kotlinlogging/KLogger.kt](KLogger) API
from `kotlin-logging` library (other classes are not touched).

### In detail

Part of the `kotlin-logging` `KLogger` API is the [https://github.com/oshai/kotlin-logging/blob/master/src/commonMain/kotlin/io/github/oshai/kotlinlogging/KLoggingEventBuilder.kt](KLoggingEventBuilder)
class that is used to lazily build the log event. In order to support this compiler plugin, this class has been extended
with a field of type `InternalCompilerData` - this allows the compile-time message template and caller data to be passed 
from project code to the `kotlin-logging` backend.

The main transformation performed by the plugin is to rewrite all logging calls to the `KLogger` API by redirecting them to 
```kotlin
fun at(level: Level, marker: Marker? = null, block: KLoggingEventBuilder.() -> Unit)
```
Internal compiler data in `KLoggingEventBuilder` is filled with the message template and caller data, collected from the source code.

Depending on the original logging API call, the plugin does some additional transformations, see below.

For even more details, you can check the [code-samples-from-tests.md](code-samples-from-tests.md) file - it is generated
by the plugin test-suite and it contains all the test cases that the plugin is tested against with before+after code
snippets and how different feature flags affect the transformation.

#### Message-builder lambda API calls

```kotlin
fun trace(message: () -> Any?): Unit
fun debug(message: () -> Any?): Unit
fun info(message: () -> Any?): Unit
fun warn(message: () -> Any?): Unit
fun error(message: () -> Any?): Unit

fun trace(throwable: Throwable?, message: () -> Any?): Unit
fun debug(throwable: Throwable?, message: () -> Any?): Unit
fun info(throwable: Throwable?, message: () -> Any?): Unit
fun warn(throwable: Throwable?, message: () -> Any?): Unit
fun error(throwable: Throwable?, message: () -> Any?): Unit

fun trace(throwable: Throwable? = null, marker: Marker?, message: () -> Any?): Unit
fun debug(throwable: Throwable? = null, marker: Marker?, message: () -> Any?): Unit
fun info(throwable: Throwable? = null, marker: Marker?, message: () -> Any?): Unit
fun warn(throwable: Throwable? = null, marker: Marker?, message: () -> Any?): Unit
fun error(throwable: Throwable? = null, marker: Marker?, message: () -> Any?): Unit

// deprecated
fun trace(marker: Marker?, msg: () -> Any?): Unit
fun debug(marker: Marker?, msg: () -> Any?): Unit
fun info(marker: Marker?, msg: () -> Any?): Unit
fun warn(marker: Marker?, msg: () -> Any?): Unit
fun error(marker: Marker?, msg: () -> Any?): Unit

// deprecated
fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit
fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit
fun info(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit
fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit
fun error(marker: Marker?, t: Throwable?, msg: () -> Any?): Unit
```

Transformed to the `at` API with the message template filled in:
```kotlin
    logger.at(Level.TRACE|DEBUG|INFO|WARN|ERROR) {
      message = message.toStringSafe()
      cause = throwable
      internalCompilerData = KLoggingEventBuilder.InternalCompilerData(
        messageTemplate = "<source of message-builder lambda expression>",
        // other fields omitted for brevity
      )
    }
```

#### Event-builder lambda API calls

```kotlin
fun atTrace(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit
fun atDebug(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit
fun atInfo(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit
fun atWarn(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit
fun atError(marker: Marker?, block: KLoggingEventBuilder.() -> Unit): Unit

fun atTrace(block: KLoggingEventBuilder.() -> Unit): Unit
fun atDebug(block: KLoggingEventBuilder.() -> Unit): Unit
fun atInfo(block: KLoggingEventBuilder.() -> Unit): Unit
fun atWarn(block: KLoggingEventBuilder.() -> Unit): Unit
fun atError(block: KLoggingEventBuilder.() -> Unit): Unit
```

As this API is already using the `KLoggingEventBuilder` class, the plugin only fills in the compiler data and attaches
it to the `KLoggingEventBuilder` instance.
```kotlin
    // logging call remains as-is
      internalCompilerData = KLoggingEventBuilder.InternalCompilerData(
        messageTemplate = "<source of message from event-builder lambda expression>",
        // other fields omitted for brevity
      )
```

#### Log-message-as-plain-String API calls, non-parameterized

```kotlin
fun trace(msg: String?): Unit
fun debug(msg: String?): Unit
fun info(msg: String?): Unit
fun warn(msg: String?): Unit
fun error(msg: String?): Unit

// deprecated
fun trace(msg: String?, t: Throwable?): Unit
fun debug(msg: String?, t: Throwable?): Unit
fun info(msg: String?, t: Throwable?): Unit
fun warn(msg: String?, t: Throwable?): Unit
fun error(msg: String?, t: Throwable?): Unit

// deprecated
fun trace(marker: Marker?, msg: String?): Unit
fun debug(marker: Marker?, msg: String?): Unit
fun info(marker: Marker?, msg: String?): Unit
fun warn(marker: Marker?, msg: String?): Unit
fun error(marker: Marker?, msg: String?): Unit

// deprecated
fun trace(marker: Marker?, msg: String?, t: Throwable?): Unit
fun debug(marker: Marker?, msg: String?, t: Throwable?): Unit
fun info(marker: Marker?, msg: String?, t: Throwable?): Unit
fun warn(marker: Marker?, msg: String?, t: Throwable?): Unit
fun error(marker: Marker?, msg: String?, t: Throwable?): Unit
```

Transformed to the `at` API with the message template filled in:
```kotlin
    logger.at(Level.TRACE|DEBUG|INFO|WARN|ERROR) {
      message = message
      cause = throwable
      internalCompilerData = KLoggingEventBuilder.InternalCompilerData(
        messageTemplate = "<source of message expression>",
        // other fields omitted for brevity
      )
    }
```

#### Log-message-as-plain-String API calls, parameterized

```kotlin
// not implemented by KLogger, throws exception
fun trace(msg: String?, arg: Any?): Unit
fun debug(msg: String?, arg: Any?): Unit
fun info(msg: String?, arg: Any?): Unit
fun warn(msg: String?, arg: Any?): Unit
fun error(msg: String?, arg: Any?): Unit

// not implemented by KLogger, throws exception
fun trace(msg: String?, arg1: Any?, arg2: Any?): Unit
fun debug(msg: String?, arg1: Any?, arg2: Any?): Unit
fun info(msg: String?, arg1: Any?, arg2: Any?): Unit
fun warn(msg: String?, arg1: Any?, arg2: Any?): Unit
fun error(msg: String?, arg1: Any?, arg2: Any?): Unit

// not implemented by KLogger, throws exception
fun trace(msg: String?, vararg arguments: Any?): Unit
fun debug(msg: String?, vararg arguments: Any?): Unit
fun info(msg: String?, vararg arguments: Any?): Unit
fun warn(msg: String?, vararg arguments: Any?): Unit
fun error(msg: String?, vararg arguments: Any?): Unit

// not implemented by KLogger, throws exception
fun trace(marker: Marker?, msg: String?, arg: Any?): Unit
fun debug(marker: Marker?, msg: String?, arg: Any?): Unit
fun info(marker: Marker?, msg: String?, arg: Any?): Unit
fun warn(marker: Marker?, msg: String?, arg: Any?): Unit
fun error(marker: Marker?, msg: String?, arg: Any?): Unit

// not implemented by KLogger, throws exception
fun trace(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit
fun debug(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit
fun info(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit
fun warn(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit
fun error(marker: Marker?, msg: String?, arg1: Any?, arg2: Any?): Unit

// not implemented by KLogger, throws exception
fun trace(marker: Marker?, msg: String?, vararg arguments: Any?): Unit
fun debug(marker: Marker?, msg: String?, vararg arguments: Any?): Unit
fun info(marker: Marker?, msg: String?, vararg arguments: Any?): Unit
fun warn(marker: Marker?, msg: String?, vararg arguments: Any?): Unit
fun error(marker: Marker?, msg: String?, vararg arguments: Any?): Unit
```

These API calls are actually not implemented by `KLogger` and without the compiler plugin they will throw an exception at runtime.
The plugin will transform them also to the `at` API with the compiler data filled in. It will also merge the message template
with the argument values and will extract `Throwable` if possible.

```kotlin
    logger.at(Level.TRACE|DEBUG|INFO|WARN|ERROR) {
      message = "<message with {} placeholders replaced>"
      cause = lastArgument?.castToThrowable()
      internalCompilerData = KLoggingEventBuilder.InternalCompilerData(
        messageTemplate = "<source of message>",
        // other fields omitted for brevity
      )
    }
```

#### Specialized API calls (entry/exit, throwing/catching)

```kotlin
fun entry(vararg arguments: Any?): Unit
fun exit(): Unit
fun <T> exit(result: T): T where T : Any?
fun <T> throwing(throwable: T): T where T : Throwable
fun <T> catching(throwable: T) where T : Throwable
```

As some of these methods return a value then we do not delegate these calls to the `at` API (as that function does not return a value).
Instead, we transform to overloaded `*WithCompilerData` (extension) API calls with the extra compiler data filled in:

```kotlin
fun KLogger.entryWithCompilerData(compilerData: KLoggingEventBuilder.InternalCompilerData? = null, vararg arguments: Any?): Unit
fun KLogger.exitWithCompilerData(compilerData: KLoggingEventBuilder.InternalCompilerData? = null): Unit
fun <T> KLogger.exitWithCompilerData(compilerData: KLoggingEventBuilder.InternalCompilerData? = null, result: T): T where T : Any?
fun <T> KLogger.throwingWithCompilerData(compilerData: KLoggingEventBuilder.InternalCompilerData? = null, throwable: T): T where T : Throwable
fun <T> KLogger.catchingWithCompilerData(compilerData: KLoggingEventBuilder.InternalCompilerData? = null, throwable: T) where T : Throwable
```

#### isEnabled API calls

```kotlin
fun isTraceEnabled(marker: Marker? = null): Boolean
fun isDebugEnabled(marker: Marker? = null): Boolean
fun isInfoEnabled(marker: Marker? = null): Boolean
fun isWarnEnabled(marker: Marker? = null): Boolean
fun isErrorEnabled(marker: Marker? = null): Boolean
fun isLoggingOff(marker: Marker? = null): Boolean
fun isLoggingEnabledFor(level: Level, marker: Marker? = null): Boolean

val isTraceEnabled: Boolean
val isDebugEnabled: Boolean
val isInfoEnabled: Boolean
val isWarnEnabled: Boolean
val isErrorEnabled: Boolean
val isLoggingOff: Boolean
```

These remain unchanged.
