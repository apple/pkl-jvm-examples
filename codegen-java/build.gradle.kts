plugins {
  // apply the Pkl plugin
  id("org.pkl-lang") version("0.25.1")
  // if the `idea` plugin is applied, the Pkl plugin makes generated code visible to IntelliJ IDEA
  idea
  `java-library`
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.pkl-lang:pkl-config-java:0.25.1")
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
  javaCodeGenerators {
    register("genJava") {
      sourceModules.addAll(files("src/main/resources/Birds.pkl", "src/main/resources/upsell.pkl"))
      generateJavadoc.set(true)
    }
  }
  evaluators {
    // Create an evaluator that evaluates the application's runtime configuration.
    // This creates an equally named task called "testPklConfig".
    //
    // This task is registered as a dependency to `check`, so that Pkl modules are checked for
    // correctness as part of a project's tests.
    register("mosaicTemplate") {
      sourceModules.addAll(files("src/main/resources/config.pkl", "src/main/resources/post_checkout_upsell_template.pkl"))
      modulePath.from(sourceSets.main.get().runtimeClasspath)
      outputFile.set(file("${layout.buildDirectory.get()}/tesetPklConfig/config"))
    }
  }
}

tasks.check {
  dependsOn(tasks.named("mosaicTemplate"))
}

// Runs this example.
// This task is specific to this project and not generally required.
val runExample by tasks.registering(JavaExec::class) {
  mainClass.set("example.JavaCodeGeneratorExample")
  classpath = sourceSets.main.get().runtimeClasspath
}

tasks.check {
  dependsOn(runExample)
}
