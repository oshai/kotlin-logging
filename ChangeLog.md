# 1.11.1

* Upgrade to Kotlin 1.4.10 [#137](https://github.com/MicroUtils/kotlin-logging/pull/137).

# 1.11.0

* Fix message formatted twice [issue #132](https://github.com/MicroUtils/kotlin-logging/issues/132) and [PR #134](https://github.com/MicroUtils/kotlin-logging/pull/134).
* Add automation for snapshot and release builds [#135](https://github.com/MicroUtils/kotlin-logging/issues/135).
* Tried to upgrade to Kotlin 1.4.0, but reveted [#130](https://github.com/MicroUtils/kotlin-logging/issues/130) (Still WIP).

# Versions 1.9.x and 1.10.x are obselete

* Had deployment issues.

# 1.8.3

* Add Linux X64 Support [#119](https://github.com/MicroUtils/kotlin-logging/pull/119).

# Versions 1.8.0.x are obselete

* Used for testing of [#119](https://github.com/MicroUtils/kotlin-logging/pull/119).

# 1.7.10

* Remove extra space from log message in entry - [#114](https://github.com/MicroUtils/kotlin-logging/issues/114).

# 1.7.9

* update Kotlin to 1.3.70.

# 1.7.8

* use dokka for kotlin javadocs - [#82](https://github.com/MicroUtils/kotlin-logging/issues/82)
* upgrade gradle wrapper - [#100](https://github.com/MicroUtils/kotlin-logging/issues/100)

# 1.7.7

* upgrade versions - [#97](https://github.com/MicroUtils/kotlin-logging/issues/97)

# 1.7.6

* wrap slf4j logger with kotlin logger - [#88](https://github.com/MicroUtils/kotlin-logging/issues/88)

# 1.7.5

* Provide for nullable in entry and rename exit argument - [#89](https://github.com/MicroUtils/kotlin-logging/issues/89)

# 1.7.4

* Fixed nullability in exit signature: [#84](https://github.com/MicroUtils/kotlin-logging/issues/84) [#86](https://github.com/MicroUtils/kotlin-logging/issues/86) [#87](https://github.com/MicroUtils/kotlin-logging/issues/87)

# 1.7.2

* Fixed missing slf4j dependency - https://github.com/MicroUtils/kotlin-logging/issues/83

# 1.7.1

* Convert project to new MPP format: https://github.com/MicroUtils/kotlin-logging/issues/64

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
