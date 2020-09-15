plugins {
    kotlin("multiplatform") version "1.4.10"
//    id("com.jfrog.bintray") version "1.8.4"
    id("org.jetbrains.dokka") version "1.4.0"
    kotlin("multiplatform") version "1.4.10"
    id("com.jfrog.bintray") version "1.8.4"
    id("com.jfrog.artifactory") version "4.17.2"
    id("org.jetbrains.dokka") version "0.10.0"
    `maven-publish`
    id("ru.mipt.npm.publish")
}


apply("versions.gradle.kts")


group = "io.github.microutils"
version = "1.11.5" + (if (System.getProperty("snapshot")?.toBoolean() == true) "-SNAPSHOT" else "")

repositories {
    jcenter()
    mavenCentral()
}

kotlin {
    jvm {
        compilations.named("main") {
            // kotlin compiler compatibility options
            kotlinOptions {
                apiVersion = "1.4"
                languageVersion = "1.4"
            }
        }
    }
    js(BOTH) {
        browser()
        nodejs()
    js {
        nodejs()
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")

    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> {
            mingwX64("native")
            linuxX64()
        }
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
    linuxX64("linuxX64")
    macosX64("macosX64")
    mingwX64("mingwX64")
    sourceSets {
        val commonMain by getting {}
        val commonTest by getting  {
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
        val nativeMain by getting
        findByName("linuxX64Main")?.dependsOn(nativeMain)
    }
}

tasks {
    register<Jar>("dokkaJar") {
        from(dokkaHtml)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
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
        artifact(tasks["dokkaJar"])
    }
}

//bintray {
//    user = "oshai"//project.hasProperty("bintrayUser") ? project.property("bintrayUser") : System.getenv("BINTRAY_USER")
//    key = "mykey" //https://bintray.com/profile/edit
//    // project.hasProperty("bintrayApiKey") ? project.property("bintrayApiKey") : System.getenv("BINTRAY_API_KEY")
//    setPublications("metadata", "jvm", "js")
//    publish = true //[Default: false] Whether version should be auto published after an upload
//    pkg.apply {
//        repo = "kotlin-logging"
//        name = "kotlin-logging"
//        userOrg = "microutils"
//        setLicenses("Apache-2.0")
//        vcsUrl = "https://github.com/MicroUtils/kotlin-logging"
//        websiteUrl = "https://github.com/MicroUtils/kotlin-logging"
//        issueTrackerUrl = "https://github.com/MicroUtils/kotlin-logging/issues"
//
//        githubRepo = "MicroUtils/kotlin-logging"
//        githubReleaseNotesFile = "ChangeLog.md"
//        version.apply {
//            name = "${project.version}"
//            desc = "kotlin-logging - Lightweight logging framework for Kotlin"
//            val date = Date()
//            released = "$date"
//            gpg.sign = true //Determines whether to GPG sign the files. The default is false
//            mavenCentralSync.apply {
//                sync = true //[Default: true] Determines whether to sync the version to Maven Central.
//                user = "token" //OSS user token: mandatory
//                password = "pass" //OSS user password: mandatory
//                close = "1" //Optional property. By default the staging repository is closed and artifacts are released to Maven Central. You can optionally turn this behaviour off (by puting 0 as value) and release the version manually.
//            }
//        }
//    }
//}
bintray {
    user = System.getProperty("bintray.user")
    key = System.getProperty("bintray.key") //https://bintray.com/profile/edit
    setPublications(*publishing.publications.names.toTypedArray())
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

/* Kscience publications */
kscience{
    publish {
        bintrayRepo = "dev"
        githubOrg = "altavir"
        githubProject = "kotlin-logging"
    }
    //workaround bintray bug
    project.afterEvaluate {
        setPublications(*project.extensions.findByType<PublishingExtension>()!!.publications.names.toTypedArray())
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
            invokeMethod("publications", publishing.publications.names.toTypedArray())
            setProperty("publishArtifacts", true)
            setProperty("publishPom", true)
        })
    })
    resolve(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.ResolverConfig>{
        setProperty("repoKey", "jcenter")
    })
    clientConfig.info.setBuildNumber(System.getProperty("build.number"))
}

//val githubUser: String? by project
//val githubToken: String? by project
//
//val bintrayRepo by extra("dev")
//
//val githubProject by extra("dataforge-core")
//
//afterEvaluate {
//    project.configure<PublishingExtension> {
//        if (githubUser != null && githubToken != null) {
//            repositories {
//                maven {
//                    name = "github"
//                    url = uri("https://maven.pkg.github.com/altavir/kotlin-logging/")
//                    credentials {
//                        username = githubUser
//                        password = githubToken
//                    }
//
//                }
//            }
//        }
//    }
//}
