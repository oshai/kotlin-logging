import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.9.23"
    // This version is dependent on the maximum tested version
    // of this plugin within the Kotlin multiplatform library
    id("com.android.library") version "8.2.2"

    id("org.jetbrains.dokka") version "1.9.20"

    id("com.diffplug.spotless") version "6.25.0"

    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    `maven-publish`
    signing
}

apply("versions.gradle.kts")

group = "io.github.oshai"
version = "6.0.7"

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JvmTarget.JVM_1_8.target
}

kotlin {
    explicitApi()

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        // kotlin compiler compatibility options
        apiVersion.set(KotlinVersion.KOTLIN_1_9)
        languageVersion.set(KotlinVersion.KOTLIN_1_9)

        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    jvm {
        compilations.all {
            // kotlin compiler compatibility options
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
    androidTarget {
        publishLibraryVariants("release", "debug")
    }
    val linuxTargets = listOf(
        linuxArm64(),
        linuxX64(),
        mingwX64(),
        androidNativeX64(),
        androidNativeX86(),
        androidNativeArm64(),
        androidNativeArm32(),
    )
    val darwinTargets = listOf(
        macosArm64(),
        macosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        iosX64(),
        watchosArm64(),
        watchosSimulatorArm64(),
        watchosX64(),
        tvosArm64(),
        tvosSimulatorArm64(),
        tvosX64()
    )

    sourceSets {
        val commonMain by getting {}
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        // common to jvm and android
        val javaMain by creating {
            dependsOn(commonMain)
            dependencies {
                compileOnly("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${extra["coroutines_version"]}")
            }
        }
        val jvmMain by getting {
            dependsOn(javaMain)
            dependencies {
                compileOnly("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${extra["coroutines_version"]}")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.junit.jupiter:junit-jupiter-engine:${extra["junit_version"]}")
                implementation("org.junit.jupiter:junit-jupiter-params:${extra["junit_version"]}")
                implementation("org.mockito:mockito-core:${extra["mockito_version"]}")
                implementation("org.apache.logging.log4j:log4j-api:${extra["log4j_version"]}")
                implementation("org.apache.logging.log4j:log4j-core:${extra["log4j_version"]}")
                implementation("org.apache.logging.log4j:log4j-slf4j2-impl:${extra["log4j_version"]}")
                implementation("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
                // our jul test just forward the logs jul -> slf4j -> log4j
                implementation("org.slf4j:jul-to-slf4j:${extra["slf4j_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${extra["coroutines_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${extra["coroutines_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${extra["coroutines_version"]}")
            }
        }
        val androidMain by getting {
            dependsOn(javaMain)
            dependencies {
                compileOnly("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${extra["coroutines_version"]}")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.junit.jupiter:junit-jupiter-engine:${extra["junit_version"]}")
                implementation("org.junit.jupiter:junit-jupiter-params:${extra["junit_version"]}")
                implementation("org.mockito:mockito-core:${extra["mockito_version"]}")
                implementation("org.apache.logging.log4j:log4j-api:${extra["log4j_version"]}")
                implementation("org.apache.logging.log4j:log4j-core:${extra["log4j_version"]}")
                implementation("org.apache.logging.log4j:log4j-slf4j2-impl:${extra["log4j_version"]}")
                implementation("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
                // our jul test just forward the logs jul -> slf4j -> log4j
                implementation("org.slf4j:jul-to-slf4j:${extra["slf4j_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${extra["coroutines_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${extra["coroutines_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${extra["coroutines_version"]}")
            }
        }
        val directMain by creating {
            dependsOn(commonMain)
        }
        val jsMain by getting {
            dependsOn(directMain)
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val wasmJsMain by getting {
            dependsOn(directMain)
        }
        val wasmJsTest by getting {
            dependencies {
                implementation(kotlin("test-wasm-js"))
            }
        }
        val nativeMain by creating {
            dependsOn(directMain)
        }
        val nativeTest by creating {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val linuxMain by creating {
            dependsOn(nativeMain)
        }
        val darwinMain by creating {
            dependsOn(commonMain)
        }
        linuxTargets.forEach {
            getByName("${it.targetName}Main") {
                dependsOn(linuxMain)
            }
        }
        darwinTargets.forEach {
            getByName("${it.targetName}Main") {
                dependsOn(darwinMain)
            }
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    namespace = "io.github.oshai"
}

tasks {
    withType<Jar> {
        metaInf.with(
            copySpec {
                from("${project.rootDir}/LICENSE")
            }
        )
    }

    val jvmJar by getting(Jar::class) {
        manifest {
            attributes("Automatic-Module-Name" to "io.github.oshai.kotlinlogging")
        }
    }
}


// Docs

tasks {
    register<Jar>("dokkaJar") {
        from(dokkaHtml)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
    }
}


// Tests

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            showExceptions = true
            exceptionFormat = FULL
        }
    }
}


// Static code analysis tools

spotless {
    kotlin {
        target("src/**/*.kt")
        ktfmt("0.47").googleStyle()
    }
}


// Publishing

nexusPublishing {
    repositories {
        sonatype {  // only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(System.getenv("SONATYPE_USERNAME_2")) // defaults to project.properties["myNexusUsername"]
            password.set(System.getenv("SONATYPE_PASSWORD_2")) // defaults to project.properties["myNexusPassword"]
        }
    }
}

apply(plugin = "io.github.gradle-nexus.publish-plugin")

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("kotlin-logging")
            description.set("kotlin-logging $version - Lightweight logging framework for Kotlin")
            url.set("https://github.com/oshai/kotlin-logging")
            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    name.set("Ohad Shai")
                    email.set("ohadshai@gmail.com")
                    organization.set("github")
                    organizationUrl.set("https://www.github.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/oshai/kotlin-logging.git")
                developerConnection.set("scm:git:ssh://github.com:oshai/kotlin-logging.git")
                url.set("https://github.com/oshai/kotlin-logging/tree/master")
            }
        }
        artifact(tasks["dokkaJar"])
    }
}

signing {
    useInMemoryPgpKeys(
        System.getProperty("GPG_PRIVATE_KEY"),
        System.getProperty("GPG_PRIVATE_PASSWORD")
    )
    sign(publishing.publications)
}


// Gradle wrapper

tasks {
    // see https://docs.gradle.org/current/userguide/gradle_wrapper.html#customizing_wrapper
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
    }
}

//region Fix Gradle warning about signing tasks using publishing task outputs without explicit dependencies
// https://github.com/gradle/gradle/issues/26091
tasks.withType<AbstractPublishToMaven>().configureEach {
    val signingTasks = tasks.withType<Sign>()
    mustRunAfter(signingTasks)
}
//endregion
