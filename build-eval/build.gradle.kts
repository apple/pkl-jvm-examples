plugins {
  // apply the Pkl plugin
  id("org.pkl-lang") version("0.29.0")
  base
}

// Register an evaluator named "runExample".
// This adds a task with the same name.
// Configuration options largely mirror Pkl CLI options.
// For UP-TO-DATE checks to work correctly,
pkl {
  evaluators {
    register("runExample") {
      sourceModules.set(files("bird.pkl"))
      outputFile.set(file("${layout.buildDirectory.get()}/bird.yaml"))
      outputFormat.set("yaml")
    }
  }
}

tasks.check {
  dependsOn("runExample")
}
