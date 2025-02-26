import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  idea
  kotlin("jvm")
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions {
    jvmTarget = JvmTarget.JVM_17
  }
}

tasks.withType<JavaCompile>().configureEach {
  sourceCompatibility = "17"
  targetCompatibility = "17"
}


repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.10")
  implementation("org.pkl-lang:pkl-config-kotlin:0.28.0")
}

// Runs this example.
// This task is specific to this project and not generally required.
val runExample by tasks.registering(JavaExec::class) {
  mainClass.set("example.KotlinConfigExampleKt")
  classpath = sourceSets.main.get().runtimeClasspath
}

tasks.check {
  dependsOn(runExample)
}
