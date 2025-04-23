import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  // apply the Pkl plugin
  id("org.pkl-lang") version("0.28.2")
  // if the `idea` plugin is applied, the Pkl plugin makes generated code visible to IntelliJ IDEA
  idea
  `java-library`
  kotlin("jvm")
}

repositories {
  mavenCentral()
}

tasks.withType<JavaCompile>().configureEach {
  sourceCompatibility = "17"
  targetCompatibility = "17"
}

kotlin {
  compilerOptions {
    jvmTarget = JvmTarget.JVM_17
  }
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.10")
  implementation("org.pkl-lang:pkl-config-kotlin:0.28.2")
}

// Register a code generator named "configClasses".
// This adds a task with the same name.
//
// The generated classes are automatically added to the `main` source set,
// which means they are accessible from production and test code.
// Alternatively, a different `sourceSet` can be configured.
//
// By default, generated classes are written to `outputDir` "$projectDir/generated/$generatorName".
// This tends to work better for IDEs than writing generated classes
// to the build output directory, which is typically excluded by IDEs.
pkl {
  kotlinCodeGenerators {
    register("configClasses") {
      sourceModules.set(files("src/main/resources/Birds.pkl"))
      generateKdoc.set(true)
    }
  }
}

// Runs this example.
// This task is specific to this project and not generally required.
val runExample by tasks.registering(JavaExec::class) {
  mainClass.set("example.KotlinCodeGeneratorExampleKt")
  classpath = sourceSets.main.get().runtimeClasspath
}

tasks.check {
  dependsOn(runExample)
}
