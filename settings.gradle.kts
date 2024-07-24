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
require(javaVersion.isCompatibleWith(JavaVersion.VERSION_17)) {
  "Project requires Java 17 or higher, but found ${javaVersion.majorVersion}."
}
