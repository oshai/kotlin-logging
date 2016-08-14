# 1.2.2 (broken) / 1.3

* Changed artifactId and jar name from kotlin.logging to kotlin-logging

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
