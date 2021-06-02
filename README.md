# <img height="30" width="30" alt="kotlin-logging" src="https://raw.githubusercontent.com/MicroUtils/kotlin-logging/master/misc/images/kotlin-logging.png"> [kotlin-logging](https://github.com/MicroUtils/kotlin-logging) [![Build Status](https://travis-ci.com/MicroUtils/kotlin-logging.png?branch=master)](https://travis-ci.com/MicroUtils/kotlin-logging) [![Slack channel](https://img.shields.io/badge/Chat-Slack-blue.svg)](https://kotlinlang.slack.com/messages/kotlin-logging/) [![Maven Central](https://img.shields.io/maven-central/v/io.github.microutils/kotlin-logging.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.github.microutils%22) [![Apache License V.2](https://img.shields.io/badge/license-Apache%20V.2-blue.svg)](https://github.com/MicroUtils/kotlin-logging/blob/master/LICENSE)

Lightweight logging framework for Kotlin, written in [![Pure Kotlin](https://img.shields.io/badge/100%25-kotlin-blue.svg)](https://kotlinlang.org/).  
A convenient and performant logging library wrapping [slf4j](http://www.slf4j.org/) with Kotlin extensions.

#### Call log methods, without checking whether the respective log level is enabled
```Kotlin
logger.debug { "Some $expensive message!" }
```

Behind the scenes the expensive message do not get evaluated if debug is not enabled:
```Kotlin
// This is what happens when you write the above ^^^
if (logger.isDebugEnabled) logger.debug("Some $expensive message!")
```

#### Define the logger, without explicitly specifiying the class name
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

## Getting started
 
```Kotlin
import mu.KotlinLogging
private val logger = KotlinLogging.logger {} 
class FooWithLogging {
    val message = "world"
    fun bar() {
        logger.debug { "hello $message" }
    }
}
```

An `Android` example project with kotlin-logging can be found in [kotlin-logging-example-android](https://github.com/MicroUtils/kotlin-logging-example-android).

## Download

**Important note:** kotlin-logging depends on slf4j-api (in the JVM artifact). In runtime, it is also required to depend on a logging implementation. More details in [how-to-configure-slf4j](http://saltnlight5.blogspot.co.il/2013/08/how-to-configure-slf4j-with-different.html). And an excellent detailed explanation in [a-guide-to-logging-in-java](https://www.marcobehler.com/guides/a-guide-to-logging-in-java).  

In short, if you just want to log statements to stdout, it's possible to add the following dependency: `org.slf4j:slf4j-simple:1.7.29`.

### Maven
```xml
<dependency>
  <groupId>io.github.microutils</groupId>
  <artifactId>kotlin-logging-jvm</artifactId>
  <version>2.0.8</version>
</dependency>
```
or
```xml
<dependency>
  <groupId>io.github.microutils</groupId>
  <artifactId>kotlin-logging</artifactId>
  <version>1.12.5</version>
</dependency>
```
See the full example in [kotlin-logging-example-maven](https://github.com/MicroUtils/kotlin-logging-example-maven).  

### Gradle
```Groovy
implementation 'io.github.microutils:kotlin-logging-jvm:2.0.8'
```
or
```Groovy
implementation 'io.github.microutils:kotlin-logging:1.12.5'
```

Alternatively, download the JAR from [github](https://github.com/MicroUtils/kotlin-logging/releases/latest) or [bintray](https://dl.bintray.com/microutils/kotlin-logging/io/github/microutils/kotlin-logging/) or [maven-central](http://repo1.maven.org/maven2/io/github/microutils/kotlin-logging/).

### Version 2.x vs 1.x

**There are currently two supported branches: 1.x and 2.x.**  

The main incompatible change is in the artifact names. In version `1.x` the jvm artifact is called `kotlin-logging` while in version `2.x` it's called `kotlin-logging-jvm` to comply with the multiplatform schema. In addition, version 2.x supports only Kotlin >= 1.4.

Therefore, for jvm library owners it is still recommended to use 1.x, as for the rest of the use cases 2.x is recommended.

#### Multiplatform

An experimental common & JS & linux-x64 support is available.  
More information is available on the [wiki](https://github.com/MicroUtils/kotlin-logging/wiki/Multiplatform-support) and issues [#21](https://github.com/MicroUtils/kotlin-logging/issues/21) [#45](https://github.com/MicroUtils/kotlin-logging/issues/45).

## Overview

After seeing many questions like [Idiomatic way of logging in Kotlin](http://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin) and [Best practices for loggers](https://discuss.kotlinlang.org/t/best-practices-for-loggers/226/15), it seems like there should be a standard for logging and obtaining a logger in Kotlin. kotlin-logging provides a wrapper for slf4j-api to be used by Kotlin classes with the following advantages:
  - No need to write the logger and class name or logger name boilerplate code.
  - A straight forward way to log messages with lazy-evaluated string using lambda expression `{}`.
  - All previous slf4j implementation can still be used.

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
- Is all slf4j implementation supported (Markers, params, etc')? Yes, kotlin-logging inherits Logger and all methods are supported.
- Is location logging possible? Yes, location awareness was added in kotlin-logging 1.4.
- When I do `logger.debug`, my IntelliJ IDEA run console doesn't show any output. Do you know how I could set the console logger to debug or trace levels? Is this an IDE setting, or can it be set in the call to KLogging()? Setting log level is done per implementation. kotlin-logging and slf4j are just facades for the underlying logging lib (log4j, logback etc') more details [here](http://stackoverflow.com/questions/43146977/how-to-configure-kotlin-logging-logger).
- Can I access the actual logger? Yes, via `KLogger.underlyingLogger` property.

## Usage

- See [wiki](https://github.com/MicroUtils/kotlin-logging/wiki) for more examples.

It is possible to configure IntelliJ live templates. For file level logger configure the following:
- Text template: `private val logger = mu.KotlinLogging.logger {}`.
- Applicable in `Kotlin: top-level`.

## Support

- Open an issue here: https://github.com/MicroUtils/kotlin-logging/issues
- Ask a question in StackOverflow with [kotlin-logging tag](http://stackoverflow.com/tags/kotlin-logging/info).
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

## Contributors

Any contribution is appreciated. See the contributors list in: https://github.com/MicroUtils/kotlin-logging/graphs/contributors

## Contributing

Pull requests are welcome!  
[Show your ❤ with a ★](https://github.com/MicroUtils/kotlin-logging/stargazers)


