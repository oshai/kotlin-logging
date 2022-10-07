import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.jvm.tasks.Jar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL

plugins {
    kotlin("multiplatform") version "1.7.20"
    id("org.jetbrains.dokka") version "1.6.0"
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    signing
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

group = "io.github.microutils"
version = "3.0.1"

repositories {
    mavenCentral()
}

nexusPublishing {
    repositories {
        sonatype()
    }
}

apply(plugin = "io.github.gradle-nexus.publish-plugin")

kotlin {
    explicitApi()
    jvm {
        compilations.all {
            // kotlin compiler compatibility options
            kotlinOptions {
                apiVersion = "1.5"
                languageVersion = "1.5"
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
                api(libs.slf4j.api)
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

    withType<Detekt> {
        reports.xml.required.set(true)
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
            url.set("https://github.com/MicroUtils/kotlin-logging")
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
                connection.set("scm:git:git://github.com/MicroUtils/kotlin-logging.git")
                developerConnection.set("scm:git:ssh://github.com:MicroUtils/kotlin-logging.git")
                url.set("https://github.com/MicroUtils/kotlin-logging/tree/master")
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
}
