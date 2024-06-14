
# `kotlin-logging` versions

This page is the TL;DR version of the changes in major versions.

For the full changelog see github [releases page](https://github.com/oshai/kotlin-logging/releases).  
The previous changelog is available [here](https://github.com/oshai/kotlin-logging/ChangeLog-old.md).

List of versions are available also in [mvnrepository](https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging)
and [maven central](https://repo1.maven.org/maven2/io/github/oshai/kotlin-logging/).

# Overview 

The latest kotlin-logging version is 7.  
It is the recommended version for new users and upgrades, as previous versions will require manual upgrade.

Version 4.x is **not recommended** to use (we jumped directly from v.3 to v.5).  
During the change from v.3 to v.5 the lib was "rebranded" from `MicroUtils` to `oshai`.  
The github organization changed from [MicroUtils](https://github.com/MicroUtils/kotlin-logging) to [oshai](https://github.com/oshai/kotlin-logging) user 
(github automatically redirects from the old to the new url).  
The package name changed from `mu` to `io.github.oshai.kotlinlogging`.

The rationale for the change is that after v.3 (v.4, v.5) a big breaking change was introduced around multiplatform class hierarchy
and changing artifact id and package name allow to avoid compatibility issues for users of the pre v.5 versions (v.1, v.2, v.3).  
In addition, what initially seems as a good idea of having an organization for the lib (micro utils), turns out as less visible as to who maintains it, and the package name was less conventional.

Version 5 is not backward compatible with previous versions (v.3, v.2, v.1). Group id (in maven) and packages names changed.
It is possible to use both version 5 and previous versions side-by-side so some of the code from the old version
and some new. It is also possible to have libs using old version and use the new version (and vice-versa).  
In that sense it's a completely new dependency.

Below is a list of all versions, some history and note-worthy changes.

# Version 7

Released on: Jun 14, 2024.  
Full change log:
[v.7](https://github.com/oshai/kotlin-logging/releases/tag/7.0.0).

Main changes are:

- Support kotlin version >= 2.
- Change to depend on KMP 2.

# Version 6

Released on: Dec 18, 2023.  
Full change log: 
[v.6](https://github.com/oshai/kotlin-logging/releases/tag/6.0.0).

Main changes are:

- Support kotlin version >= 1.9.
- Change to depend on KMP 1.9.

# Version 5

Released on: July 19, 2023.  
Full change log: 
[v.5](https://github.com/oshai/kotlin-logging/releases/tag/5.0.0)
and [v.4](https://github.com/oshai/kotlin-logging/releases/tag/4.0.0).


Main changes are (compared to v.3):

- Add fluent api and structured logging support (payload).
- Maven group id changed from `io.github.microutils` -> `io.github.oshai`.
- Root package change from `mu` -> `io.github.oshai.kotlinlogging`.
- Slf4j dependency is not provided anymore (users have to provide it). It means that >= 5.x can work with both slf4j 1 or 2.
- There are changes to multiplatform class hierarchy that break compatibility (the api structure around expected / actual was significantly changed).'


# Version 4

Not to be used. Immediately after releasing the changes it was decided some more breaking changes are required so skipping this version.

# Version 3

Released on: Sep 18, 2022.  
Full change log:
[v.3](https://github.com/oshai/kotlin-logging/releases/tag/3.0.0)

Main Changes:
- Upgrade slf4j 1.x->2.x by @yeikel in https://github.com/oshai/kotlin-logging/pull/234

# Version 2

Released on: Sep 20, 2020.  
Full change log in the [previous changelog](https://github.com/oshai/kotlin-logging/blob/master/ChangeLog-old.md).

The main incompatible change is in the artifact names. In version `1.x` the jvm artifact is called `kotlin-logging` while in version `2.x` it's called `kotlin-logging-jvm` to comply with the multiplatform schema. In addition, version 2.x supports only Kotlin >= 1.4.

# Version 1

First GA release.  
Released on: Jul 13, 2016.  
Full change log in the [previous changelog](https://github.com/oshai/kotlin-logging/blob/master/ChangeLog-old.md).


# Version 0.1

Initial Release.  
Released on: Jul 5, 2016.  
Full change log in the [previous changelog](https://github.com/oshai/kotlin-logging/blob/master/ChangeLog-old.md).

