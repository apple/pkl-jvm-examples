/**
 * Copyright © 2025-2026 Apple Inc. and the Pkl project authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  // apply the Pkl plugin
  id("org.pkl-lang") version ("0.31.0")
  // if the `idea` plugin is applied, the Pkl plugin makes generated code visible to IntelliJ IDEA
  idea
  `java-library`
  kotlin("jvm")
}

repositories { mavenCentral() }

tasks.withType<JavaCompile>().configureEach {
  sourceCompatibility = "17"
  targetCompatibility = "17"
}

kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_17 } }

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.3.21")
  implementation("org.pkl-lang:pkl-config-kotlin:0.31.0")
  implementation("org.pkl-lang:pkl-config-java-all:0.31.0")
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
val runExample by
  tasks.registering(JavaExec::class) {
    mainClass.set("example.KotlinCodeGeneratorExampleKt")
    classpath = sourceSets.main.get().runtimeClasspath
  }

tasks.check { dependsOn(runExample) }
