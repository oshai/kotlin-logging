pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        jcenter()
        maven("https://dl.bintray.com/mipt-npm/kscience")
        maven("https://dl.bintray.com/mipt-npm/dev")
    }

    val toolsVersion = "0.6.0-dev-6"

    plugins {
        id("ru.mipt.npm.publish") version toolsVersion
    }
}

rootProject.name = "kotlin-logging"
