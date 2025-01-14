plugins {
  id("java-gradle-plugin")
  id("com.gradle.plugin-publish") version "1.2.1"
  kotlin("jvm")
  id("com.github.gmazzo.buildconfig")
  id("com.diffplug.spotless")

}
repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
  val project = project(":kotlin-ir-plugin")
  packageName("${rootProject.extra["kotlin_plugin_package_name"]}")
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${project.group}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${project.name}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${project.version}\"")
}

gradlePlugin {
  plugins {
    create("kotlinLoggingIrPlugin") {
      id = rootProject.extra["kotlin_plugin_id"] as String
      displayName = "kotlin-logging IR plugin"
      description = "Collects metadata about logging calls from the source code and adds it to the respective calls"
      implementationClass = "io.github.oshai.kotlinlogging.irplugin.KotlinLoggingGradlePlugin"
    }
  }
}

publishing {
  repositories {
    maven {
      name = "localPluginRepository"
      url = uri(rootProject.layout.buildDirectory.dir("local-plugin-repository"))
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
