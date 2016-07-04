# kotlin.logging
logging framework for kotlin

# Install

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

# Usage
```Kotlin
class ClassWithLogging {
    companion object: WithLogging()
    fun test() {
        logger.info{"test ClassWithLogging"}
    }
}
class ClassHasLogging: HasLogging {
    override val logger = logger()
    fun test() {
        logger.info{"test ClassHasLogging"}
    }
}
class ClassWithNamedLogging {
    companion object: Any(), HasLogging by WithNamedLogging("mu.ClassWithNamedLogging")
    fun test() {
        logger.info{"test ClassWithNamedLogging"}
    }
}
class ChildClassWithLogging {
    companion object: WithLogging()
    fun test() {
        logger.info{"test ChildClassWithLogging"}
    }
}
```
