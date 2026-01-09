// File: build.gradle.kts
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
    alias(libs.plugins.nexus.publish.plugin)
    `maven-publish`
    signing
}

group = "io.github.oshai"
version = "8.0.00-beta-04"

repositories {
    google()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_1_8
    }
}

kotlin {
    explicitApi()

    compilerOptions {
        // kotlin compiler compatibility options
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        languageVersion.set(KotlinVersion.KOTLIN_2_0)

        // Required to silence compiler warnings about the beta status of
        // expected and actual classes. See https://kotlinlang.org/docs/multiplatform-expect-actual.html#expected-and-actual-classes
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    jvm {
        compilations {
            val main by getting
            tasks.named<JavaCompile>("compileJvmMainJava") {
                options.compilerArgs.add("--patch-module")
                options.compilerArgs.add("io.github.oshai.kotlinlogging=${main.output.classesDirs.asPath}")
            }
            // logback tests are testing only direct logback dependency (not slf4j/log4j)
            val logbackTest by compilations.creating {
                defaultSourceSet {
                    dependencies {
                        // Compile against the main compilation's compile classpath and outputs:
                        implementation(main.compileDependencyFiles + main.output.classesDirs)
                    }
                }
                val logbackTest = tasks.register<Test>("logbackTest") {
                    description = "Runs tests with Logback"
                    group = "verification"
                    // Run the tests with the classpath containing the compile dependencies (including 'main'),
                    // runtime dependencies, and the outputs of this compilation:
                    classpath = compileDependencyFiles + runtimeDependencyFiles + output.allOutputs

                    // Run only the tests from this compilation's outputs:
                    testClassesDirs = output.classesDirs
                }
                tasks["allTests"].dependsOn(logbackTest)
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
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmWasi { // console based WASI target
        // Use nodejs as runtime env for tests & distribution (WASI support via node --experimental-wasi-unstable-preview1)
        nodejs()
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
        watchosArm32(),
        watchosArm64(),
        watchosSimulatorArm64(),
        watchosDeviceArm64(),
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
                compileOnly(libs.slf4j.api)
                compileOnly(libs.kotlinx.coroutines.slf4j)
            }
        }
        val jvmMain by getting {
            dependsOn(javaMain)
            dependencies {
                compileOnly(libs.slf4j.api)
                compileOnly(libs.logback.classic)
                compileOnly(libs.kotlinx.coroutines.slf4j)
                compileOnly(libs.nativeimage)
            }
        }
        val jvmLogbackTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(project.dependencies.enforcedPlatform(libs.junit.bom))
                implementation(libs.junit.jupiter.engine)
                implementation(libs.junit.jupiter.params)
                implementation(libs.junit.platform.launcher)
                implementation(libs.mockito.core)
                implementation(libs.slf4j.api)
                implementation(libs.logback.classic)
                implementation(libs.logstash.logback.encoder)
                implementation(libs.jackson.core)
                implementation(libs.jackson.module.kotlin)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(project.dependencies.enforcedPlatform(libs.junit.bom))
                implementation(libs.junit.jupiter.engine)
                implementation(libs.junit.jupiter.params)
                implementation(libs.mockito.core)
                implementation(libs.log4j.api)
                implementation(libs.log4j.core)
                implementation(libs.log4j.slf4j2.impl)
                implementation(libs.slf4j.api)

                // our jul test just forward the logs jul -> slf4j -> log4j
                implementation(libs.jul.to.slf4j)
                implementation(libs.kotlinx.coroutines.slf4j)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val androidMain by getting {
            dependsOn(javaMain)
            dependencies {
                compileOnly(libs.slf4j.api)
                compileOnly(libs.kotlinx.coroutines.slf4j)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(project.dependencies.enforcedPlatform(libs.junit.bom))
                implementation(libs.junit.jupiter.engine)
                implementation(libs.junit.jupiter.params)
                implementation(libs.mockito.core)
                implementation(libs.log4j.api)
                implementation(libs.log4j.core)
                implementation(libs.log4j.slf4j2.impl)
                implementation(libs.slf4j.api)
                // our jul test just forward the logs jul -> slf4j -> log4j
                implementation(libs.jul.to.slf4j)
                implementation(libs.kotlinx.coroutines.slf4j)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.coroutines.test)
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
        val wasmWasiMain by getting {
            dependsOn(directMain)
        }
        val wasmWasiTest by getting {
            dependencies {
                implementation(kotlin("test-wasm-wasi"))
            }
        }
        val nativeMain by creating {
            dependsOn(directMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
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
        val darwinTest by creating {
            dependsOn(commonTest)
            dependencies {
                implementation(kotlin("test"))
            }
        }
        linuxTargets.forEach {
            getByName("${it.targetName}Main") {
                dependsOn(linuxMain)
            }
            getByName("${it.targetName}Test") {
                dependsOn(nativeTest)
            }
        }
        darwinTargets.forEach {
            getByName("${it.targetName}Main") {
                dependsOn(darwinMain)
            }
            getByName("${it.targetName}Test") {
                dependsOn(darwinTest)
            }
            val main by it.compilations.getting
            val oslog by main.cinterops.creating {
                defFile(project.file("src/darwinMain/cinterop/oslog.def"))
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
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/content/repositories/snapshots/"))
            username.set(System.getenv("SONATYPE_USERNAME_2")) // defaults to project.properties["myNexusUsername"]
            password.set(System.getenv("SONATYPE_PASSWORD_2")) // defaults to project.properties["myNexusPassword"]
        }
    }
}

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

// Fix implicit dependency issue for native MetadataElements tasks
// "Declare an explicit dependency on ':commonizeCInterop' from ':*MetadataElements' using Task#dependsOn"
tasks.matching {
    it.name.matches("^(linux|mingw|androidNative|macos|ios|watchos|tvos).*MetadataElements$".toRegex())
}.configureEach {
    dependsOn("commonizeCInterop")
}
