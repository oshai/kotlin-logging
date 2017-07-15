# 1.4.6
* Fixed issue [#20](https://github.com/MicroUtils/kotlin-logging/issues/20) - add `KLogger.underlyingLogger` that provides the Logger actual implementation. Can help in operations such as setting log level.

# 1.4.5
* Fixed issue [#22](https://github.com/MicroUtils/kotlin-logging/pull/22) - Suppress exceptions from toString methods.

# 1.4.4
* Upgrade to Kotlin 1.1.

# 1.4.3
* Fixed issue [#17](https://github.com/MicroUtils/kotlin-logging/issues/17) - bad location in some logging methods.

# 1.4.2
* Supported `null` values in parameters that allows it in slf4j.
* Update to kotlin 1.0.6.

# 1.4.1
* Added KotlinLogging object allow getting a logger in the following way:
```kotlin
private val logger = KotlinLogging.logger {}
```
see issue [#12](https://github.com/MicroUtils/kotlin-logging/issues/12) and PR [#13](https://github.com/MicroUtils/kotlin-logging/issues/13)

# 1.4

* Changed KLogger to be an interface
* Fix issue [#11](https://github.com/MicroUtils/kotlin-logging/issues/11) - Location Awerness of logger

# 1.3.3

* Remove dependency on kotlin reflection to make it even more lightweight.
* Update to kotlin 1.0.4.


# 1.3.2

* Published artifacts to maven-central.
* Added lazy evaluated message methods for throwables: https://github.com/MicroUtils/kotlin-logging/pull/7.

# 1.3

* Changed artifactId and jar name from kotlin.logging to kotlin-logging and changed groupId to io.github.microutils
* Moved the bintray path

# 1.2.1

* Added tests for class name in logger in KLoggerNameResolverTest
* Added KLoggerNameResolver and KLoggerFactory
* Added documentation

# 1.2

* Added KLogger method inline - better for Android methods count

# 1.1.1

* Added documentation and sources jars to release
* Removed KLogger method inline - should not affect performance and line numbers will be better

# 1.1

* Changed names of classes:
  * WithLogging -> KLogging
  * HasLogging -> KLoggable
  * WithNamedLogging -> NamedKLogging
