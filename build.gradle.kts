import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.jvm.tasks.Jar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL

plugins {
    kotlin("multiplatform") version "1.5.21"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    signing
    id("io.gitlab.arturbosch.detekt") version "1.18.0"
}

apply("versions.gradle.kts")

group = "io.github.microutils"
version = "2.0.12"

repositories {
    mavenCentral()
}

nexusPublishing {
    repositories {
        sonatype()
    }
}

kotlin {
    explicitApi()
    jvm {
        compilations.all {
            // kotlin compiler compatibility options
            kotlinOptions {
                apiVersion = "1.4"
                languageVersion = "1.4"
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

    linuxX64("linuxX64")
    macosX64("macosX64")
    mingwX64("mingwX64")

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
                api("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("junit:junit:${extra["junit_version"]}")
                implementation("org.mockito:mockito-all:${extra["mockito_version"]}")
                implementation("org.apache.logging.log4j:log4j-api:${extra["log4j_version"]}")
                implementation("org.apache.logging.log4j:log4j-core:${extra["log4j_version"]}")
                implementation("org.apache.logging.log4j:log4j-slf4j-impl:${extra["log4j_version"]}")
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
        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }
        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }
        val macosX64Main by getting {
            dependsOn(nativeMain)
        }
    }
}

tasks {
    register<Jar>("dokkaJar") {
        from(dokkaHtml)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
    }

	withType<Jar> {
		metaInf.with(
			copySpec {
				from("${project.rootDir}/LICENSE")
			}
		)
	}

    withType<Test> {
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
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
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
