plugins {
  idea
  `java-library`
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.pkl-lang:pkl-config-java-all:0.26.2")
}

// Runs this example.
// This task is specific to this project and not generally required.
val runExample by tasks.registering(JavaExec::class) {
  mainClass.set("example.JavaConfigExample")
  classpath = sourceSets.main.get().runtimeClasspath
}

tasks.check {
  dependsOn(runExample)
}
