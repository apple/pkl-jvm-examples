plugins {
  // apply the Pkl plugin
  id("org.pkl-lang") version("0.25.1")
  base
}

// Register a documentation generator named "pkldoc".
// This adds a task with the same name.
// By default, generated documentation is written to `outputDir` "${layout.buildDirectory}/pkldoc/$generatorName".
pkl {
  pkldocGenerators {
    register("pkldoc") {
      sourceModules.set(files("Bird.pkl", "doc-package-info.pkl"))
    }
  }
}

tasks.check {
  dependsOn("pkldoc")
}
