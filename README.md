# <img height="30" width="30" alt="kotlin-logging" src="https://raw.githubusercontent.com/MicroUtils/kotlin-logging/master/misc/images/kotlin-logging.png"> [kotlin-logging](https://github.com/MicroUtils/kotlin-logging) [![Build Status](https://travis-ci.org/MicroUtils/kotlin-logging.png?branch=master)](https://travis-ci.org/MicroUtils/kotlin-logging) [![Slack channel](https://img.shields.io/badge/Chat-Slack-blue.svg)](https://kotlinlang.slack.com/messages/kotlin-logging/) [ ![Download](https://api.bintray.com/packages/microutils/kotlin-logging/kotlin-logging/images/download.svg) ](https://bintray.com/microutils/kotlin-logging/kotlin-logging/_latestVersion) [![Apache License V.2](https://img.shields.io/badge/license-Apache%20V.2-blue.svg)](https://github.com/MicroUtils/kotlin-logging/blob/master/LICENSE) [![Pure Kotlin](https://img.shields.io/badge/100%25-kotlin-blue.svg)](https://kotlinlang.org/)

Lightweight logging framework for Kotlin.
A convenient and performant logging library wrapping [slf4j](http://www.slf4j.org/) with Kotlin extensions.

Show your :heart: with a [:star:](https://github.com/MicroUtils/kotlin-logging/stargazers)

* Call log methods, without checking whether the respective log level is enabled:
```Kotlin
logger.debug { "Some $expensive message!" }
```
Behind the scenes the expensive message do not get evaluated if debug is not enabled:
```Kotlin
if (logger.isDebugEnabled()) logger.debug("Some $expensive message!")
```
* Define the logger, without explicitly specifiying the class name:
```Kotlin
val logger = KotlinLogging.logger {}
```
or
```Kotlin
companion object: KLogging()
```
Behind the scenes `val logger` will be created in the class, with the class/file name:
```Kotlin
val logger = LoggerFactory.getLogger("class name")
```

* Log exceptions in a Kotlin-style
```Kotlin
logger.error(exception) { "a $fancy message about the $exception" }
```

## Getting started
 
```Kotlin
val logger = KotlinLogging.logger {} 
class FooWithLogging {
    val message = "world"
    fun bar() {
        logger.info { "hello $message" }
    }
}
```

An `Android` example project with kotlin logging can be found in [kotlin-logging-example-android](https://github.com/MicroUtils/kotlin-logging-example-android).

## Download

**Important note:** kotlin-logging depends on slf4j-api. In runtime, it is also required to depend on a logging implementation. More details [here](http://saltnlight5.blogspot.co.il/2013/08/how-to-configure-slf4j-with-different.html).

### Maven
```xml
<dependency>
  <groupId>io.github.microutils</groupId>
  <artifactId>kotlin-logging</artifactId>
  <version>1.4.1</version>
</dependency>
```
See full example in [kotlin-logging-example-maven](https://github.com/MicroUtils/kotlin-logging-example-maven).  

### Gradle
```Groovy
compile 'io.github.microutils:kotlin-logging:1.4.1'
```

Or alternatively, download jar from [github](https://github.com/MicroUtils/kotlin-logging/releases/latest) or [bintray](https://dl.bintray.com/microutils/kotlin-logging/io/github/microutils/kotlin-logging/) or [maven-central](http://repo1.maven.org/maven2/io/github/microutils/kotlin-logging/).


## Overview

After seeing many questions like [Idiomatic way of logging in Kotlin](http://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin) and [Best practices for loggers](https://discuss.kotlinlang.org/t/best-practices-for-loggers/226/15), It seems like there should be a standard for logging and obtaining a logger in kotlin. kotlin-logging provide a wrapper for slf4j api to be used by kotlin classes with the following advantages:
  - No need to write the logger and class name or logger name boileplates.
  - A straight forward way to log messages with lazy-evaluated string using lambda expression `{}`.
  - All previous slf4j implementation still can be used.

## FAQ

- Why not use plain slf4j? kotlin-logging has better native kotlin support. It adds more functionality and enable less boilerplates.
- Is all slf4j implementation supported (Markers, params, etc')? Yes, KLogger inherits Logger and all methods are supported.
- Is location logging possible? Yes, location awerness was added in kotlin-logging 1.4.

## Usage

- See [wiki](https://github.com/MicroUtils/kotlin-logging/wiki) for more examples.

## Support

- Open an issue here: https://github.com/MicroUtils/kotlin-logging/issues
- Ask a question in StackOverflow with [kotlin-logging tag](http://stackoverflow.com/tags/kotlin-logging/info).
- Chat on Slack channel: https://kotlinlang.slack.com/messages/kotlin-logging

## More links

- kotlin-logging is hosted on [bintray](https://bintray.com/microutils/kotlin-logging/kotlin-logging/view) and [maven central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22kotlin-logging%22).
- https://medium.com/@OhadShai/logging-in-kotlin-95a4e76388f2
- [kotlin-logging vs AnkoLogger for Android](https://medium.com/@OhadShai/logging-in-android-ankologger-vs-kotlin-logging-bb693671442a)
- [How kotlin-logging was published](https://medium.com/@OhadShai/no-forks-one-star-now-what-how-i-published-my-open-source-projects-8a5b5ae35d2c#.e3ygj6uf3)

## Contributing

- Pull requests are welcome!


