# <img alt="kotlin-logging" src="https://raw.githubusercontent.com/MicroUtils/kotlin-logging/master/images/kotlin-logging.png"> [kotlin-logging](https://github.com/MicroUtils/kotlin-logging)

Lightweight logging framework for Kotlin.
A wrapper for [slf4j](http://www.slf4j.org/) with Kotlin extensions.

[![Build Status](https://travis-ci.org/MicroUtils/kotlin-logging.png?branch=master)](https://travis-ci.org/MicroUtils/kotlin-logging)
[![Slack channel](https://img.shields.io/badge/Chat-Slack-blue.svg)](https://kotlinlang.slack.com/messages/kotlin-logging/)
[ ![Download](https://api.bintray.com/packages/microutils/kotlin.logging/kotlin-logging/images/download.svg) ](https://bintray.com/microutils/kotlin.logging/kotlin-logging/_latestVersion)
[![Apache License V.2](https://img.shields.io/github/license/MicroUtils/kotlin-logging.svg)](https://github.com/MicroUtils/kotlin-logging/blob/master/LICENSE)
[![Pure Kotlin](https://img.shields.io/badge/100%25-kotlin-blue.svg)](https://kotlinlang.org/)

## Getting started
 
```Kotlin
class FooWithLogging {
    companion object: KLogging()
    fun bar() {
        logger.info { "twinkle twinkle $little star" }
    }
}
```

## Download

Download jar from [github](https://github.com/MicroUtils/kotlin-logging/releases/latest) or [bintray](https://dl.bintray.com/microutils/kotlin.logging/microutils/kotlin.logging/).

### Maven
```
<dependency>
  <groupId>microutils</groupId>
  <artifactId>kotlin.logging</artifactId>
  <version>1.2</version>
</dependency>
```
### Gradle
```
compile 'microutils:kotlin.logging:1.2'
```

**Important note:** kotlin-logging depends on slf4j-api, but it is also required to depend on a logging implementation in runtime. More details [here](http://saltnlight5.blogspot.co.il/2013/08/how-to-configure-slf4j-with-different.html).

## Overview

After seeing many questions like [Idiomatic way of logging in Kotlin](http://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin), It seems like there should be a standard for logging and obtaining a logger in kotlin. kotlin-logging provide a wrapper for slf4j api to be used by kotlin classes with the following advantages:
  - No need to write the logger and class name or logger name boileplates.
  - A straight forward way to log messages with lazy-evaluated string using lambda expression `{}`.
  - All previous slf4j implementation still can be used.

## FAQ

- Why not use slf4j like before? That is possible but you get more power and less boilerplates with kotlin-logging.

## Usage

- See [wiki](https://github.com/MicroUtils/kotlin-logging/wiki).

## Support

- Issues: https://github.com/MicroUtils/kotlin-logging/issues
- Ask question in StackOverflow with kotlin-logging tag: http://stackoverflow.com/tags/kotlin-logging/info
- Slack channel: https://kotlinlang.slack.com/messages/kotlin-logging

## More links

- kotlin-logging is hosted on [bintray](https://bintray.com/microutils/kotlin.logging/kotlin-logging/view).
- https://medium.com/@OhadShai/logging-in-kotlin-95a4e76388f2

## Contributing

- Pull requests are welcome!
- Show your :heart: with a :star: ^^^

