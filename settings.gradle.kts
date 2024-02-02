rootProject.name = "pkl-jvm-examples"

include("build-eval")
include("codegen-java")
include("codegen-kotlin")
include("config-java")
include("config-kotlin")
include("core")
include("pkldoc")

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

val javaVersion = JavaVersion.current()
require(javaVersion.isJava11Compatible) {
  "Project requires Java 11 or higher, but found ${javaVersion.majorVersion}."
}
