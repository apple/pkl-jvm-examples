/**
 * Copyright © 2025 Apple Inc. and the Pkl project authors. All rights reserved.
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
plugins {
  // apply the Pkl plugin
  id("org.pkl-lang") version ("0.30.0")
  idea
  `java-library`
}

java { sourceCompatibility = JavaVersion.VERSION_17 }

repositories { mavenCentral() }

dependencies { implementation("org.pkl-lang:pkl-config-java-all:0.30.0") }

// Generate a resource named "data.msgpack" by evaluating data.pkl
pkl {
  evaluators {
    register("configData") {
      sourceModules.set(files("config.pkl"))
      outputFormat.set("pkl-binary")
      outputFile.set(file("build/generated/pkl-binary/example/data.msgpack"))
    }
  }
}

// Ensure the data.msgpack is built when compiling
tasks.processResources { dependsOn("configData") }

// Ensure data.msgpack is included as a resource
sourceSets { main { resources { srcDir("build/generated/pkl-binary") } } }

// Runs this example.
// This task is specific to this project and not generally required.
val runExample by
  tasks.registering(JavaExec::class) {
    mainClass.set("example.JavaConfigBuildTimeEvalExample")
    classpath = sourceSets.main.get().runtimeClasspath
  }

tasks.check { dependsOn(runExample) }
