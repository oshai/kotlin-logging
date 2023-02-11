import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.jvm.tasks.Jar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL

plugins {
    kotlin("multiplatform") version "1.8.10"
    id("org.jetbrains.dokka") version "1.7.10"
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    signing
    id("io.gitlab.arturbosch.detekt") version "1.18.0"
    id("com.android.library") version "7.1.2"
    id("com.diffplug.spotless") version "5.12.4"
}


apply("versions.gradle.kts")

group = "io.github.oshai"
version = "4.0.0-beta-13"

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(System.getenv("SONATYPE_USERNAME_2")) // defaults to project.properties["myNexusUsername"]
            password.set(System.getenv("SONATYPE_PASSWORD_2")) // defaults to project.properties["myNexusPassword"]
        }
    }
}

apply(plugin = "io.github.gradle-nexus.publish-plugin")

kotlin {
    explicitApi()
    jvm {
        compilations.all {
            // kotlin compiler compatibility options
            kotlinOptions {
                apiVersion = "1.4"
                languageVersion = "1.4"
                jvmTarget = "1.8"
            }
        }
    }
    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs()
    }
    android()
    val linuxTargets = listOf(
        linuxArm64(),
        linuxX64(),
        mingwX64()
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
        val jvmMain by getting {
            dependencies {
                compileOnly("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
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
            }
        }
        val androidMain by getting {}
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.junit.jupiter:junit-jupiter-engine:${extra["junit_version"]}")
                implementation("org.junit.jupiter:junit-jupiter-params:${extra["junit_version"]}")
                implementation("org.mockito:mockito-core:${extra["mockito_version"]}")
            }
        }
        val jsMain by getting {}
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(nativeMain)
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val linuxMain by creating {
            dependsOn(nativeMain)
        }
        val darwinMain by creating {
            dependsOn(nativeMain)
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

tasks {
    register<Jar>("dokkaJar") {
        from(dokkaHtml)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
    }

    // see https://docs.gradle.org/current/userguide/gradle_wrapper.html#customizing_wrapper
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
    }

	withType<Jar> {
		metaInf.with(
			copySpec {
				from("${project.rootDir}/LICENSE")
			}
		)
	}

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            showExceptions = true
            exceptionFormat = FULL
        }
    }
    afterEvaluate {
        check {
            dependsOn(withType<Detekt>())
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

detekt {
    buildUponDefaultConfig = true
    config = files(rootDir.resolve("detekt.yml"))
    parallel = true

    reports {
        html.enabled = false
        txt.enabled = false
    }
}

val jvmJar by tasks.getting(Jar::class) {
    manifest {
        attributes("Automatic-Module-Name" to "io.github.oshai.kotlinlogging")
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}
spotless {
    kotlin {
        target("src/**/*.kt")
        ktfmt("0.24").googleStyle()
    }
}
