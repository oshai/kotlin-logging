# [kotlin.logging](https://github.com/MicroUtils/kotlin.logging)

Logging framework for kotlin.
Used as a wrapper for [slf4j](http://www.slf4j.org/) with kotlin extensions.

# Install

Add the below dependency to start using kotlin-logging, which is hosted on bintray.

**Important Note: kotlin-logging already depends on slf4j-api, but it is also required to depend on logging implementation like explained [here](http://saltnlight5.blogspot.co.il/2013/08/how-to-configure-slf4j-with-different.html).**

## Maven
```
<dependency>
  <groupId>microutils</groupId>
  <artifactId>kotlin.logging</artifactId>
  <version>0.1</version>
  <type>pom</type>
</dependency>
```
## Gradle
```
compile 'microutils:kotlin.logging:0.1'
```
## Ivy
```
<dependency org='microutils' name='kotlin.logging' rev='0.1'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```

# Getting started
 
```Kotlin
class FooWithLogging {
    companion object: WithLogging()
    fun bar() {
        logger.info("info FooWithLogging")
    }
}
```

# Overview

After seeing many questions like [Idiomatic way of logging in Kotlin](http://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin), It seems like there should be a standard for that in the kotlin language. kotlin-logging provide a wrapper for slf4j api to be used by kotlin classes with the following advantages:
  - No need to write the logger and class name or logger name boileplates.
  - A straight forward way to log messages with lazy-evaluated string using lambda expression `{}`.
  - All previous slf4j implementation still can be used.

# Usage

The recommended usage is have the `Companion` object extends `WithLogging()` and using the `logger` value in the class:
```Kotlin
companion object: WithLogging()
```
The using freely the `logger`:
```Kotlin
logger.info("test ClassWithLogging")
```
And also for debugging of lazy evaluated messages:
```Kotlin
logger.debug{"lazy eavluated $message"}
```
(gets evaluated only if the log level is enabled at runtime)

In cases the `Companion` object already extending other class it is recommend to implement the `HasLogging` interface:
```Kotlin
companion object: Any(), HasLogging {
  override val logger = logger()
  ...
}
```

Other (less recommended) alternatives are:
```Kotlin
companion object: Any(), HasLogging by WithNamedLogging("mu.ClassWithNamedLogging")
```
Or implementing it as a non static member:
```Kotlin
class ClassHasLogging: HasLogging {
    override val logger = logger()
    fun test() {
        logger.info{"test ClassHasLogging"}
    }
}
```
