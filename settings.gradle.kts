pluginManagement {
    plugins {
      id("com.diffplug.spotless") version "7.0.1" apply false
    }
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "kotlin-logging"
include(":kotlin-ir-plugin-gradle")
include(":kotlin-ir-plugin")
