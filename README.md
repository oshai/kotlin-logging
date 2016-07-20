# [kotlin.logging](https://github.com/MicroUtils/kotlin.logging)

Lightweight logging framework for Kotlin.
Used as a wrapper for [slf4j](http://www.slf4j.org/) with Kotlin extensions.

[![Build Status](https://travis-ci.org/MicroUtils/kotlin.logging.png?branch=master)](https://travis-ci.org/MicroUtils/kotlin.logging)
[ ![Download](https://api.bintray.com/packages/microutils/kotlin.logging/kotlin.logging/images/download.svg) ](https://bintray.com/microutils/kotlin.logging/kotlin.logging/_latestVersion)

# Install

Add the below dependency to start using kotlin.logging, which is hosted on [bintray](https://bintray.com/microutils/kotlin.logging/kotlin.logging/view).

**Important note:** kotlin.logging depends on slf4j-api, but it is also required to depend on a logging implementation in runtime. More details [here](http://saltnlight5.blogspot.co.il/2013/08/how-to-configure-slf4j-with-different.html).

### Maven
```
<dependency>
  <groupId>microutils</groupId>
  <artifactId>kotlin.logging</artifactId>
  <version>1.1.1</version>
</dependency>
```
### Gradle
```
compile 'microutils:kotlin.logging:1.1.1'
```
### Ivy
```
<dependency org='microutils' name='kotlin.logging' rev='1.1.1'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```

# Getting started
 
```Kotlin
class FooWithLogging {
    companion object: KLogging()
    fun bar() {
        logger.info("hello message")
    }
}
```

# Overview

After seeing many questions like [Idiomatic way of logging in Kotlin](http://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin), It seems like there should be a standard for logging and obtaining a logger in kotlin. kotlin.logging provide a wrapper for slf4j api to be used by kotlin classes with the following advantages:
  - No need to write the logger and class name or logger name boileplates.
  - A straight forward way to log messages with lazy-evaluated string using lambda expression `{}`.
  - All previous slf4j implementation still can be used.

# Usage

The recommended usage is to have the `Companion` object extends `KLogging()` and using the `logger` member in the class like that:
```Kotlin
companion object: KLogging()
```
Then using the `logger`:
```Kotlin
logger.info("hello message")
```
For sequences that are expected to be frequently used prefer lazy evaluated messages:
```Kotlin
logger.debug{"lazy evaluated $hello message"}
```
(String is inside a method and gets evaluated only if debug log level is enabled at runtime)

In cases the `Companion` object already extending other class it is recommend to implement the `KLoggable` interface:
```Kotlin
companion object: Any(), KLoggable {
  override val logger = logger()
  ...
}
```

Other (less recommended) alternatives are:
```Kotlin
companion object: Any(), KLoggable by NamedKLogging("com.MyClass")
```
Or implementing it as a non static member:
```Kotlin
class ClassHasLogging: KLoggable {
    override val logger = logger()
    fun test() {
        logger.info{"hello message"}
    }
}
```
# Support

- Slack channel: https://kotlinlang.slack.com/messages/kotlin-logging/details/
- Issues: https://github.com/MicroUtils/kotlin.logging/issues

# Contributing

Pull requests are welcome!

