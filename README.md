# [kotlin-logging](https://github.com/oshai/kotlin-logging) [![CI](https://github.com/oshai/kotlin-logging/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/oshai/kotlin-logging/actions/workflows/ci.yml) [![OpenSSF Scorecard](https://api.scorecard.dev/projects/github.com/oshai/kotlin-logging/badge)](https://scorecard.dev/viewer/?uri=github.com/oshai/kotlin-logging) [![Slack channel](https://img.shields.io/badge/Chat-Slack-blue.svg)](https://kotlinlang.slack.com/messages/kotlin-logging/) [![Maven Central](https://img.shields.io/maven-central/v/io.github.oshai/kotlin-logging.svg)](https://central.sonatype.com/namespace/io.github.oshai) [![Apache License V.2](https://img.shields.io/badge/license-Apache%20V.2-blue.svg)](https://github.com/oshai/kotlin-logging/blob/master/LICENSE) [![Kotlin](https://img.shields.io/badge/kotlin-multiplatform-7f52ff?logo=kotlin)](https://kotlinlang.org/) [![GitHub stars](https://img.shields.io/github/stars/oshai/kotlin-logging?style=social)](https://github.com/oshai/kotlin-logging) <img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/04d2c80d-934d-4d02-a3b2-32ad73cb8542" />

Lightweight Multiplatform logging framework for Kotlin, written in 
[![Pure Kotlin](https://img.shields.io/badge/100%25-kotlin-blue.svg)](https://kotlinlang.org/).  
A convenient and performant logging facade.  

#### Call log methods, without checking whether the respective log level is enabled
```Kotlin
logger.debug { "Some $expensive message!" }
```

Behind the scenes the expensive message do not get evaluated if debug is not enabled:
```Kotlin
// This is what happens when you write the above ^^^
if (logger.isDebugEnabled) logger.debug("Some $expensive message!")
```

#### Define the logger, without explicitly specifying the class name
```Kotlin
// Place definition above class declaration to make field static
private val logger = KotlinLogging.logger {}
```

Behind the scenes `val logger` will be created in the class, with the class/file name:
```Kotlin
// This is what happens when you write the above ^^^
val logger = LoggerFactory.getLogger("package.ClassName")
```

#### Log exceptions in a Kotlin-style
```Kotlin
// exception as first parameter with message as lambda
logger.error(exception) { "a $fancy message about the $exception" }
```

#### Fluent logging in a Kotlin-style
```kotlin
logger.atWarn {
    message    = "foo $bar"
    cause      = exception
    payload    = buildMap(capacity = 3) {
        put("foo", 1)
        put("bar", "x")
        put("obj", Pair(2, 3))
    }
}
```

## Getting started
 
```Kotlin
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {} 

class FooWithLogging {
    val message = "world"
    fun bar() {
        logger.debug { "hello $message" }
    }
}
```

## Download

**Important note:** On JVM, `kotlin-logging-jvm` wraps `slf4j-api` (`compileOnly` since version 5, supporting both SLF4J 2.x and 1.7.x). At runtime, add an SLF4J logging implementation such as Logback or Log4j2. More details in the [SLF4J user manual](https://www.slf4j.org/manual.html) and [a-guide-to-logging-in-java](https://www.marcobehler.com/guides/a-guide-to-logging-in-java).

In short, if you just want to log statements to stdout, add `org.slf4j:slf4j-simple:2.0.20`.

### Gradle (Kotlin DSL)
```kotlin
dependencies {
    implementation("io.github.oshai:kotlin-logging-jvm:8.0.7")
}
```

### Gradle Version Catalog (`gradle/libs.versions.toml`)
```toml
[versions]
kotlin-logging = "8.0.7"

[libraries]
kotlin-logging-jvm = { module = "io.github.oshai:kotlin-logging-jvm", version.ref = "kotlin-logging" }
# For Kotlin Multiplatform commonMain:
kotlin-logging = { module = "io.github.oshai:kotlin-logging", version.ref = "kotlin-logging" }
```

### Multiplatform (`commonMain`)
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.oshai:kotlin-logging:8.0.7")
        }
    }
}
```

### Gradle (Groovy)
```groovy
implementation 'io.github.oshai:kotlin-logging-jvm:8.0.7'
```

### Maven
```xml
<dependency>
  <groupId>io.github.oshai</groupId>
  <artifactId>kotlin-logging-jvm</artifactId>
  <version>8.0.7</version>
</dependency>
```

See the full example in [kotlin-logging-example-maven](https://github.com/microutils/kotlin-logging-example-maven).

Alternatively, download the JAR from [GitHub Releases](https://github.com/oshai/kotlin-logging/releases/latest) or [Maven Central](https://central.sonatype.com/namespace/io.github.oshai).

## Kotlin Multiplatform

`kotlin-logging` provides full production Kotlin Multiplatform support across 20+ targets from a single `commonMain` dependency (`io.github.oshai:kotlin-logging:8.0.7`):

- **JVM** — SLF4J (default), direct Logback (`-Dkotlin-logging-to-logback=true`), `java.util.logging` (`-Dkotlin-logging-to-jul=true`), or stdout direct logging (`-Dkotlin-logging-to-direct=true`).
- **Android** (`kotlin-logging-android`) — SLF4J (default) or native `android.util.Log` (`-Dkotlin-logging-to-android-native=true`).
- **JavaScript** — Browser and Node.js (`js`).
- **WebAssembly** — `wasmJs` (Browser) and `wasmWasi` (Node.js / WASI).
- **Apple / Darwin** — `macos` (`Arm64`, `X64`), `ios` (`Arm64`, `SimulatorArm64`, `X64`), `watchos`, and `tvos` backed by Apple Unified Logging (`os_log`).
- **Linux, Windows & Android Native** — `linuxX64`, `linuxArm64`, `mingwX64`, and `androidNative` (`Arm64`, `Arm32`, `X64`, `X86`).

On JS, Wasm, and Native platforms, configure the console log level via `KotlinLoggingConfiguration.direct.logLevel = Level.DEBUG`. More details and platform-specific options are documented on the [Multiplatform Wiki](https://github.com/oshai/kotlin-logging/wiki/Multiplatform-support).

## Overview

After seeing many questions like [Idiomatic way of logging in Kotlin](https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin) and [Best practices for loggers](https://discuss.kotlinlang.org/t/best-practices-for-loggers/226/15), it seems like there should be a standard for logging and obtaining a logger in Kotlin. kotlin-logging provides a wrapper for slf4j-api to be used by Kotlin classes with the following advantages:
  - No need to write the logger and class name or logger name boilerplate code.
  - A straight forward way to log messages with lazy-evaluated string using lambda expression `{}`.
  - All previous slf4j implementation can still be used.


## Version 5 vs. previous versions

Version 5+ is not backward compatible with pre-5 versions (`v3`, `v2`, `v1`). Maven group ID and package names changed, making it possible to use `io.github.oshai` (`5.x`–`8.x`) and legacy `io.github.microutils` (`1.x`–`3.x`) side-by-side in the same dependency graph during incremental migrations.

Main changes are:
- Maven group id changed from `io.github.microutils` -> `io.github.oshai`.
- Root package change from `mu` -> `io.github.oshai.kotlinlogging`.
- Slf4j dependency is not provided anymore (users have to provide it). It means that >= 5.x can work with both slf4j 1 or 2.
- Version `8.0.0` removed deprecated eager `String?` overloads (`logger.info("msg")`, `logger.error("msg", ex)`); use lazy `{}` lambdas (`logger.info { "msg" }`, `logger.error(ex) { "msg" }`) or fluent builders (`logger.atInfo { ... }`).
- There are changes to multiplatform class hierarchy that might break compatibility.

**Automated migration:** See [`docs/openrewrite/README.md`](docs/openrewrite/README.md) for an OpenRewrite recipe that automatically upgrades `io.github.microutils` dependencies and rewrites `mu.*` imports to `io.github.oshai` `8.x`.

More details in issue [#264](https://github.com/oshai/kotlin-logging/issues/264), 
and in the [change log](https://github.com/oshai/kotlin-logging/blob/master/ChangeLog.md)



## Who is using it

- https://www.jetbrains.com/youtrack/ (https://www.jetbrains.com/help/youtrack/standalone/Third-Party-Software-Used-by-YouTrack.html)
- https://github.com/DiUS/pact-jvm
- https://github.com/AsynkronIT/protoactor-kotlin
- https://github.com/square/misk
- https://github.com/openrndr/openrndr
- https://github.com/JetBrains/xodus

And many more... (add your name above)

## FAQ

- Why not use plain slf4j? kotlin-logging has better native Kotlin support. It adds more functionality and enables less boilerplate code.
- Is all slf4j implementation supported (Markers, params, etc')? Yes.
- Is location logging possible? Yes, location awareness was added in kotlin-logging 1.4.
- When I do `logger.debug`, my IntelliJ IDEA run console doesn't show any output. Do you know how I could set the console logger to debug or trace levels? Is this an IDE setting, or can it be set in the call to KLogging()? Setting log level is done per implementation. kotlin-logging and slf4j are just facades for the underlying logging lib (log4j, logback etc') more details [here](https://stackoverflow.com/questions/43146977/how-to-configure-kotlin-logging-logger).
- Can I access the actual logger? In platforms available yes, via `DelegatingKLogger.underlyingLogger` property.
- Why does `KotlinLogging.logger {}` inside a nested or inner class use the outer class name on JVM? On JVM and Android, logger name resolution strips everything from the first `$` in the binary class name so lambdas and `Companion` objects resolve to the enclosing class. To use a separate logger name for a nested or inner class, pass it explicitly: `KotlinLogging.logger(Outer.Inner::class.java.name)` (or `KotlinLogging.logger("package.Outer.Inner")`).

## Usage

- See [wiki](https://github.com/oshai/kotlin-logging/wiki) for more examples.
- API documentation (Dokka): https://oshai.github.io/kotlin-logging/
- AI / LLM coding context: [`llms.txt`](llms.txt) (https://oshai.github.io/kotlin-logging/llms.txt)

It is possible to configure IntelliJ live templates. For file level logger configure the following:
- Text template: `private val logger = io.github.oshai.kotlinlogging.KotlinLogging.logger {}`.
- Applicable in `Kotlin: top-level`.

## Support

- Open an issue here: https://github.com/oshai/kotlin-logging/issues
- Ask a question in StackOverflow with [kotlin-logging tag](https://stackoverflow.com/tags/kotlin-logging/info).
- Chat on Slack channel: https://kotlinlang.slack.com/messages/kotlin-logging
- Chat on Telegram channel: https://t.me/klogging

## More links

- https://medium.com/@OhadShai/logging-in-kotlin-95a4e76388f2
- [kotlin-logging vs AnkoLogger for Android](https://medium.com/@OhadShai/logging-in-android-ankologger-vs-kotlin-logging-bb693671442a)
- [How kotlin-logging was published](https://medium.com/@OhadShai/no-forks-one-star-now-what-how-i-published-my-open-source-projects-8a5b5ae35d2c#.e3ygj6uf3)
- [Kotlin Logging Without the Fuss](https://realjenius.com/2017/08/31/logging-in-kotlin/)
- [DropWizard + Kotlin = Project Kronslott](https://medium.com/@davideriksson_91895/dropwizard-kotlin-project-kronslott-e2aa51b277b8)
- [Logging in Kotlin – the right approach](https://amarszalek.net/blog/2018/05/13/logging-in-kotlin-right-approach/)
- https://bednarek.wroclaw.pl/log4j-in-kotlin/
- https://jaxenter.com/kotlin-logging-168814.html

## Contributing

Any contribution is appreciated.  

Pull requests are welcome! See instructions in https://github.com/oshai/kotlin-logging/blob/master/CONTRIBUTING.md.  

[Show your ❤ with a ★](https://github.com/oshai/kotlin-logging/stargazers)

Thank you to all the developers who have contributed to kotlin-logging!
<a href="https://github.com/oshai/kotlin-logging/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=oshai/kotlin-logging" />
</a>


See the full contributors list in: https://github.com/oshai/kotlin-logging/graphs/contributors




