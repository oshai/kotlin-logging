import org.gradle.api.internal.FeaturePreviews.Feature.VERSION_CATALOGS

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "kotlin-logging"

enableFeaturePreview(VERSION_CATALOGS.name)
