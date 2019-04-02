# 1.6.26

* Add support for slf4j-ext: https://github.com/MicroUtils/kotlin-logging/pull/73

# 1.6.25

* Javascript - Extracting Message Formatter interface (more changes):https://github.com/MicroUtils/kotlin-logging/pull/66

# 1.6.24

* Javascript - Extracting Message Formatter interface: https://github.com/MicroUtils/kotlin-logging/pull/63

# 1.6.23

* Update kotlin to 1.3.20.

# 1.6.22

* Fix log levels in JS: https://github.com/MicroUtils/kotlin-logging/pull/57
* Update kotlin to 1.3.10.

# 1.6.20

* Compile with Kotlin 1.3.0: https://github.com/MicroUtils/kotlin-logging/pull/55 thanks @walokra.

# 1.6.0 + 1.6.10
* Add Markers support for multiplatform [#48](https://github.com/MicroUtils/kotlin-logging/pull/48)
* Improvement to version upload automation [#46](https://github.com/MicroUtils/kotlin-logging/issues/46)

# 1.5.9
* Back to Kotlin 1.2.60, created 3 artifacts on maven central.

# 1.5.8
* Refactor - move toStringSafe() to common lib.
* This version (+1.5.7 which has no changes) are mostly deployed for multiplatform checks.

# 1.5.6
* Add Partial support for multiplatform [#21](https://github.com/MicroUtils/kotlin-logging/issues/21)
  * common and js implementation.
* kotlin version is now 1.2.30, when tried to upgrade an additional (unneeded) dependency in pom.xml on common module was created.

# 1.5.4
* Accept nullable Throwables [#38](https://github.com/MicroUtils/kotlin-logging/issues/38) [#39](https://github.com/MicroUtils/kotlin-logging/issues/39)

# 1.5.3
* Add Partial support for multiplatform [#21](https://github.com/MicroUtils/kotlin-logging/issues/21)
  * Split directories.
  * Currently only jvm artifact is deployed.

# 1.4.9
* Add MDC Support [#31](https://github.com/MicroUtils/kotlin-logging/issues/31)
* Upgrade to Kotlin 1.2.10.

# 1.4.8
* 1.4.7 was broken see issue [#30](https://github.com/MicroUtils/kotlin-logging/issues/30)

# 1.4.7
* Upgrade to Kotlin 1.2.0.

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
