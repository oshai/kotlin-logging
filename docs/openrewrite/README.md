# Automated Migration to `kotlin-logging` `8.x` with OpenRewrite

[`rewrite.yml`](rewrite.yml) provides a declarative [OpenRewrite](https://docs.openrewrite.org/) recipe (`io.github.oshai.kotlinlogging.UpgradeToKotlinLogging8`) to automate migrating from legacy `io.github.microutils` (`1.x`–`3.x`) to `io.github.oshai` `8.x`.

## What This Recipe Automates

1. **Gradle & Maven dependencies** (`build.gradle.kts`, `build.gradle`, `gradle/libs.versions.toml`, and `pom.xml`):
   - `io.github.microutils:kotlin-logging-jvm` -> `io.github.oshai:kotlin-logging-jvm:8.0.7`
   - `io.github.microutils:kotlin-logging` -> `io.github.oshai:kotlin-logging:8.0.7`
   - `io.github.microutils:kotlin-logging-js` -> `io.github.oshai:kotlin-logging-js:8.0.7`
   - `io.github.microutils:kotlin-logging-linuxx64` -> `io.github.oshai:kotlin-logging-linuxx64:8.0.7`
2. **Kotlin & Java package imports and qualified references**:
   - Rewrites `import mu.*` (`KotlinLogging`, `KLogger`, `withLoggingContext`, `Marker`, `KMarkerFactory`, `KotlinLoggingConfiguration`, `KLogging`, `NamedKLogging`, `KLoggable`, etc.) and qualified `mu.*` usages to `io.github.oshai.kotlinlogging.*`.

---

## Option 1: Gradle via `--init-script` (No Build File Changes Required)

Download `rewrite.yml` into your project root, create a temporary `init.gradle`, and run `rewriteRun`:

```bash
curl -sSLO https://raw.githubusercontent.com/oshai/kotlin-logging/master/docs/openrewrite/rewrite.yml

cat << 'EOF' > init.gradle
initscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("org.openrewrite:plugin:7.39.0")
    }
}
rootProject {
    plugins.apply(org.openrewrite.gradle.RewritePlugin)
    dependencies {
        add("rewrite", platform("org.openrewrite.recipe:rewrite-recipe-bom:3.37.0"))
        add("rewrite", "org.openrewrite:rewrite-kotlin")
        add("rewrite", "org.openrewrite.recipe:rewrite-java-dependencies")
    }
    rewrite {
        activeRecipe("io.github.oshai.kotlinlogging.UpgradeToKotlinLogging8")
        configFile = project.getRootProject().file("rewrite.yml")
    }
    afterEvaluate {
        if (repositories.isEmpty()) {
            repositories {
                mavenCentral()
            }
        }
    }
}
EOF

./gradlew rewriteRun --init-script init.gradle
rm -f init.gradle rewrite.yml
```

> **Note on OpenRewrite versions**: `org.openrewrite.rewrite` `7.39.0` and `rewrite-recipe-bom` `3.37.0` (`rewrite-maven-plugin` `6.46.1` for Maven) are the latest self-consistent versions published directly to Maven Central and the Gradle Plugin Portal without requiring an external artifact repository.

---

## Option 2: Gradle (`build.gradle.kts`)

Download [`rewrite.yml`](rewrite.yml) to your root project directory and temporarily configure the OpenRewrite plugin in `build.gradle.kts`:

```kotlin
plugins {
    id("org.openrewrite.rewrite") version "7.39.0"
}

dependencies {
    rewrite(platform("org.openrewrite.recipe:rewrite-recipe-bom:3.37.0"))
    rewrite("org.openrewrite:rewrite-kotlin")
    rewrite("org.openrewrite.recipe:rewrite-java-dependencies")
}

rewrite {
    activeRecipe("io.github.oshai.kotlinlogging.UpgradeToKotlinLogging8")
    configFile = project.getRootProject().file("rewrite.yml")
}
```

Then execute:

```bash
./gradlew rewriteRun
```

Review the diff with `git diff`, remove the temporary `rewrite` configuration from `build.gradle.kts` and `rewrite.yml`, and verify compilation (`./gradlew check`).

---

## Option 3: Maven (`pom.xml`)

Download `rewrite.yml` into your project root and run `rewrite-maven-plugin`:

```bash
curl -sSLO https://raw.githubusercontent.com/oshai/kotlin-logging/master/docs/openrewrite/rewrite.yml

mvn -U org.openrewrite.maven:rewrite-maven-plugin:6.46.1:run \
  -Drewrite.recipeArtifactCoordinates=org.openrewrite:rewrite-kotlin:8.89.0,org.openrewrite.recipe:rewrite-java-dependencies:1.60.0 \
  -Drewrite.configLocation=rewrite.yml \
  -Drewrite.activeRecipes=io.github.oshai.kotlinlogging.UpgradeToKotlinLogging8

rm -f rewrite.yml
```

> **Maven `1.x` note**: In `kotlin-logging` `1.x`, the JVM artifact was named `kotlin-logging`. Since `2.0+`, `kotlin-logging` is the Kotlin Multiplatform root metadata module; pure Maven JVM projects must depend on `<artifactId>kotlin-logging-jvm</artifactId>`.

---

## Manual Follow-Ups After Running the Recipe

### 1. Explicit `slf4j-api` & Runtime Backend Dependency (JVM / Android)

In `3.x` (`io.github.microutils`), `org.slf4j:slf4j-api` was exposed as a transitive `api` dependency. Starting in `5.0.0`, `kotlin-logging-jvm` declares `slf4j-api` as `compileOnly` so applications can freely use either SLF4J `2.x` or SLF4J `1.7.x`.

If your project does not already bring in SLF4J via Logback, Log4j2, or Spring Boot, add `slf4j-api` and a runtime implementation explicitly:

```kotlin
dependencies {
    implementation("io.github.oshai:kotlin-logging-jvm:8.0.7")
    implementation("org.slf4j:slf4j-api:2.0.20")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.20") // or ch.qos.logback:logback-classic
}
```

### 2. Non-Lambda `String?` Overloads Removed in `8.0.0`

Version `8.0.0` removed all deprecated eager `String?` logging methods on `KLogger` (such as `logger.info("msg")`, `logger.error("msg", ex)`, and `logger.info("User {}", id)`). Every `KLogger` call in `8.x` uses lazy `{}` lambdas or fluent builders:

```kotlin
// Before (3.x - 7.x eager String overloads, removed in 8.0.0):
logger.info("Server started on port $port")
logger.error("Request failed", exception)

// After (8.x idiomatic lazy lambdas):
logger.info { "Server started on port $port" }
logger.error(exception) { "Request failed" } // Throwable first, message lambda last

// Or 8.x structured fluent API:
logger.atError {
    message = "Request failed"
    cause = exception
    payload = mapOf("port" to port)
}
```

### 3. Direct / JS / Wasm / Native Log Level Configuration (`8.0.0+`)

If configuring log levels on Kotlin/JS, WebAssembly, or Kotlin/Native, `KotlinLoggingConfiguration.logLevel` moved under `.direct` in `8.0.0`:

```kotlin
import io.github.oshai.kotlinlogging.KotlinLoggingConfiguration
import io.github.oshai.kotlinlogging.Level

KotlinLoggingConfiguration.direct.logLevel = Level.DEBUG
```
