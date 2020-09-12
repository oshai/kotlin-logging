import org.jetbrains.dokka.gradle.DokkaTask
import java.util.*

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("com.jfrog.bintray") version "1.8.4"
    id("com.jfrog.artifactory") version "4.17.2"
    id("org.jetbrains.dokka") version "0.10.0"
    `maven-publish`
    `java-library`
}

buildscript {
    apply("versions.gradle.kts")
}

group = "io.github.microutils"
version = "1.10.11" + (if (System.getProperty("snapshot")?.toBoolean() == true) "-SNAPSHOT" else "")

repositories {
    mavenCentral()
    jcenter()
}

tasks {
    register<Jar>("javadocJar") {
        val dokkaTask = getByName<DokkaTask>("dokka")
        from(dokkaTask.outputDirectory)
        dependsOn(dokkaTask)
        archiveClassifier.set("javadoc")
    }
    dokka {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/dokka"
    }
}

kotlin {
    metadata {
        mavenPublication {
            // make a name of an artifact backward-compatible, default "-metadata"
            artifactId = "${rootProject.name}-common"
        }
    }

    jvm {
        compilations.named("main") {
            // kotlin compiler compatibility options
            kotlinOptions {
                apiVersion = "1.1"
                languageVersion = "1.1"
            }
        }
        mavenPublication {
            // make a name of jvm artifact backward-compatible, default "-jvm"
            artifactId = rootProject.name
        }
    }
    js {
        compilations.named("main") {
            kotlinOptions {
                metaInfo = true
                sourceMap = true
                verbose = true
                moduleKind = "umd"
            }
        }
    }
    linuxX64("linuxX64")
    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        named("jvmMain") {
            dependencies {
                implementation(kotlin("stdlib"))
                api("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
            }
        }
        named("jvmTest") {
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
        named("jsMain") {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        named("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
            }
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
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    name.set("Ohad Shai")
                    email.set("ohadshai@gmail.com")
                    organization.set("github")
                    organizationUrl.set("http://www.github.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/MicroUtils/kotlin-logging.git")
                developerConnection.set("scm:git:ssh://github.com:MicroUtils/kotlin-logging.git")
                url.set("http://github.com/MicroUtils/kotlin-logging/tree/master")
            }
        }
        artifact(tasks["javadocJar"])
    }
}

bintray {
    user = System.getProperty("bintray.user")
    key = System.getProperty("bintray.key") //https://bintray.com/profile/edit
    setPublications("metadata", "jvm", "js")
    publish = true //[Default: false] Whether version should be auto published after an upload
    pkg.apply {
        repo = "kotlin-logging"
        name = "kotlin-logging"
        userOrg = "microutils"
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/MicroUtils/kotlin-logging"
        websiteUrl = "https://github.com/MicroUtils/kotlin-logging"
        issueTrackerUrl = "https://github.com/MicroUtils/kotlin-logging/issues"

        githubRepo = "MicroUtils/kotlin-logging"
        githubReleaseNotesFile = "ChangeLog.md"
        version.apply {
            name = "${project.version}"
            desc = "kotlin-logging - Lightweight logging framework for Kotlin"
            released = "${Date()}"
            gpg.sign = true //Determines whether to GPG sign the files. The default is false
            mavenCentralSync.apply {
                sync = true //[Default: true] Determines whether to sync the version to Maven Central.
                user = System.getProperty("maven.user") //OSS user token: mandatory
                password = System.getProperty("maven.password") //OSS user password: mandatory
                close = "1" //Optional property. By default the staging repository is closed and artifacts are released to Maven Central. You can optionally turn this behaviour off (by puting 0 as value) and release the version manually.
            }
        }
    }
}

artifactory {
    setContextUrl("http://oss.jfrog.org")
    publish(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig> {
        repository(delegateClosureOf<groovy.lang.GroovyObject> {
            setProperty("repoKey", "oss-snapshot-local")
            setProperty("username", System.getProperty("bintray.user"))
            setProperty("password", System.getProperty("bintray.key"))
            setProperty("maven", true)
        })
        defaults(delegateClosureOf<groovy.lang.GroovyObject> {
            invokeMethod("publications", arrayOf("mavenPublication"))
            setProperty("publishArtifacts", true)
            setProperty("publishPom", true)
        })
    })
    resolve(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.ResolverConfig>{
        setProperty("repoKey", "jcenter")
    })
    clientConfig.info.setBuildNumber(System.getProperty("build.number"))
}
