import org.gradle.api.internal.FeaturePreviews.Feature.VERSION_CATALOGS

pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
}

rootProject.name = "kotlin-logging"

enableFeaturePreview(VERSION_CATALOGS.name)
