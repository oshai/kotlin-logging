
# For full changelog see github [releases page](https://github.com/oshai/kotlin-logging/releases)
# For the new changelog see github [ChangeLog.md](https://github.com/oshai/kotlin-logging/ChangeLog.md)

# 2.1.20

* Same as `2.1.17`.

# 2.1.17

* Upgrade log4j to 2.16.0 [#211](https://github.com/oshai/kotlin-logging/pull/211) [#207](https://github.com/oshai/kotlin-logging/pull/207).
* Upgrade dokka to 1.6 [#209](https://github.com/oshai/kotlin-logging/pull/209).
* Fix detekt warnings [#203](https://github.com/oshai/kotlin-logging/pull/203).
* Upgrade to junit 5 [#204](https://github.com/oshai/kotlin-logging/pull/204).

# 2.1.15

* Add support for mingw (windows) and osx.

# 2.1.14

* Move to github actions release.

# 2.0.12

* added the license to all jar files [#191](https://github.com/oshai/kotlin-logging/pull/191.

# 2.0.11

* Fixed losing previous values in MDC [#187](https://github.com/oshai/kotlin-logging/pull/187).
 
# 2.0.10

* upgrade kotlin to 1.5 [#175](https://github.com/oshai/kotlin-logging/pull/175)
* unwrapCompanionClass fails to unwrap private companions when on kotlin 1.4+ [#182](https://github.com/oshai/kotlin-logging/issues/182).

# 2.0.8

* Publish to maven central directly [#177](https://github.com/oshai/kotlin-logging/issues/177).

# 2.0.7

* Support optional/nullable values for MDC [#172](https://github.com/oshai/kotlin-logging/issues/172).

# 2.0.6

* Same as `2.0.5`.

# 2.0.5

* Upgrade Kotlin to 1.4.31.

# 2.0.4

* When an error occurs in Log message invocation then log it as an ERROR [#160](https://github.com/oshai/kotlin-logging/issues/160).

# 2.0.3

* Fix Bintray versions display [#152](https://github.com/oshai/kotlin-logging/issues/152).

# 2.0.2

* Build refactor and bintray plugin replacement [#145](https://github.com/oshai/kotlin-logging/pull/145).
* Support JS-IR backend [#139](https://github.com/oshai/kotlin-logging/pull/139).
* Use linux code for linux/macos/mingw targets [#131](https://github.com/oshai/kotlin-logging/pull/131).
* Upgrade to Kotlin 1.4.10 [#137](https://github.com/oshai/kotlin-logging/pull/137).
* Add explicit API in strict mode [#124](https://github.com/oshai/kotlin-logging/issues/124).

# 2.X changes from 1.X

The main incompatible change is in the artifact names. In version `1.x` the jvm artifact is called `kotlin-logging` while in version `2.x` it's called `kotlin-logging-jvm` to comply with the multiplatform schema. In addition, version 2.x supports only Kotlin >= 1.4.

Therefore, for jvm library owners it is still recommended to use 1.x, as for the rest of the use cases 2.x is recommended.


# 1.12.5

* Fix Kotlin-logging version 1.12.0 is missing .module file in Maven artifact [#163](https://github.com/oshai/kotlin-logging/pull/163).

# Versions 1.12.1 -  1.12.4 are obselete

* Break versions due to kotlin upgrade, see [#170](https://github.com/oshai/kotlin-logging/issues/170).

# 1.12.0

* Upgrade to Kotlin 1.4.10 [#148](https://github.com/oshai/kotlin-logging/pull/148).

# 1.11.5

* Fix [#144](https://github.com/oshai/kotlin-logging/pull/144) - pom.xml has no dependencies anymore.
* Fix message formatted twice [issue #132](https://github.com/oshai/kotlin-logging/issues/132) and [PR #134](https://github.com/oshai/kotlin-logging/pull/134).
* Add automation for snapshot and release builds [#135](https://github.com/oshai/kotlin-logging/issues/135).
* Tried to upgrade to Kotlin 1.4.0, but reveted [#130](https://github.com/oshai/kotlin-logging/issues/130).

# Versions 1.9.x,1.10.x,1.11.0-1.11.4 are obselete

* Had deployment issues.

# 1.8.3

* Add Linux X64 Support [#119](https://github.com/oshai/kotlin-logging/pull/119).

# Versions 1.8.0.x are obselete

* Used for testing of [#119](https://github.com/oshai/kotlin-logging/pull/119).

# 1.7.10

* Remove extra space from log message in entry - [#114](https://github.com/oshai/kotlin-logging/issues/114).

# 1.7.9

* update Kotlin to 1.3.70.

# 1.7.8

* use dokka for kotlin javadocs - [#82](https://github.com/oshai/kotlin-logging/issues/82)
* upgrade gradle wrapper - [#100](https://github.com/oshai/kotlin-logging/issues/100)

# 1.7.7

* upgrade versions - [#97](https://github.com/oshai/kotlin-logging/issues/97)

# 1.7.6

* wrap slf4j logger with kotlin logger - [#88](https://github.com/oshai/kotlin-logging/issues/88)

# 1.7.5

* Provide for nullable in entry and rename exit argument - [#89](https://github.com/oshai/kotlin-logging/issues/89)

# 1.7.4

* Fixed nullability in exit signature: [#84](https://github.com/oshai/kotlin-logging/issues/84) [#86](https://github.com/oshai/kotlin-logging/issues/86) [#87](https://github.com/oshai/kotlin-logging/issues/87)

# 1.7.2

* Fixed missing slf4j dependency - https://github.com/oshai/kotlin-logging/issues/83

# 1.7.1

* Convert project to new MPP format: https://github.com/oshai/kotlin-logging/issues/64

# 1.6.26

* Add support for slf4j-ext: https://github.com/oshai/kotlin-logging/pull/73

# 1.6.25

* Javascript - Extracting Message Formatter interface (more changes):https://github.com/oshai/kotlin-logging/pull/66

# 1.6.24

* Javascript - Extracting Message Formatter interface: https://github.com/oshai/kotlin-logging/pull/63

# 1.6.23

* Update kotlin to 1.3.20.

# 1.6.22

* Fix log levels in JS: https://github.com/oshai/kotlin-logging/pull/57
* Update kotlin to 1.3.10.

# 1.6.20

* Compile with Kotlin 1.3.0: https://github.com/oshai/kotlin-logging/pull/55 thanks @walokra.

# 1.6.0 + 1.6.10
* Add Markers support for multiplatform [#48](https://github.com/oshai/kotlin-logging/pull/48)
* Improvement to version upload automation [#46](https://github.com/oshai/kotlin-logging/issues/46)

# 1.5.9
* Back to Kotlin 1.2.60, created 3 artifacts on maven central.

# 1.5.8
* Refactor - move toStringSafe() to common lib.
* This version (+1.5.7 which has no changes) are mostly deployed for multiplatform checks.

# 1.5.6
* Add Partial support for multiplatform [#21](https://github.com/oshai/kotlin-logging/issues/21)
  * common and js implementation.
* kotlin version is now 1.2.30, when tried to upgrade an additional (unneeded) dependency in pom.xml on common module was created.

# 1.5.4
* Accept nullable Throwables [#38](https://github.com/oshai/kotlin-logging/issues/38) [#39](https://github.com/oshai/kotlin-logging/issues/39)

# 1.5.3
* Add Partial support for multiplatform [#21](https://github.com/oshai/kotlin-logging/issues/21)
  * Split directories.
  * Currently only jvm artifact is deployed.

# 1.4.9
* Add MDC Support [#31](https://github.com/oshai/kotlin-logging/issues/31)
* Upgrade to Kotlin 1.2.10.

# 1.4.8
* 1.4.7 was broken see issue [#30](https://github.com/oshai/kotlin-logging/issues/30)

# 1.4.7
* Upgrade to Kotlin 1.2.0.

# 1.4.6
* Fixed issue [#20](https://github.com/oshai/kotlin-logging/issues/20) - add `KLogger.underlyingLogger` that provides the Logger actual implementation. Can help in operations such as setting log level.

# 1.4.5
* Fixed issue [#22](https://github.com/oshai/kotlin-logging/pull/22) - Suppress exceptions from toString methods.

# 1.4.4
* Upgrade to Kotlin 1.1.

# 1.4.3
* Fixed issue [#17](https://github.com/oshai/kotlin-logging/issues/17) - bad location in some logging methods.

# 1.4.2
* Supported `null` values in parameters that allows it in slf4j.
* Update to kotlin 1.0.6.

# 1.4.1
* Added KotlinLogging object allow getting a logger in the following way:
```kotlin
private val logger = KotlinLogging.logger {}
```
see issue [#12](https://github.com/oshai/kotlin-logging/issues/12) and PR [#13](https://github.com/oshai/kotlin-logging/issues/13)

# 1.4

* Changed KLogger to be an interface
* Fix issue [#11](https://github.com/oshai/kotlin-logging/issues/11) - Location Awerness of logger

# 1.3.3

* Remove dependency on kotlin reflection to make it even more lightweight.
* Update to kotlin 1.0.4.


# 1.3.2

* Published artifacts to maven-central.
* Added lazy evaluated message methods for throwables: https://github.com/oshai/kotlin-logging/pull/7.

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
