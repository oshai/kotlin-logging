import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL

plugins {
  kotlin("jvm")
  kotlin("kapt")
  id("com.github.gmazzo.buildconfig")
  id("com.diffplug.spotless")

}
repositories {
  mavenCentral()
}

dependencies {
  compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")

  kapt("com.google.auto.service:auto-service:1.1.1")
  compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
  implementation("com.javiersc.kotlin:kotlin-compiler-extensions:0.4.4+2.0.21")

  testImplementation(kotlin("test-junit"))
  testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
  testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
  testImplementation("dev.zacsweers.kctfork:core:0.5.1")
  testImplementation(rootProject)
  testImplementation("ch.qos.logback:logback-classic:${rootProject.extra["logback_version"]}")
}

buildConfig {
  packageName("${rootProject.extra["kotlin_plugin_package_name"]}")
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
}

tasks {
  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showStandardStreams = true
      showExceptions = true
      exceptionFormat = FULL
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
