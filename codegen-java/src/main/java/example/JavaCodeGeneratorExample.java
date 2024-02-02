/**
 * Copyright © 2024 Apple Inc. and the Pkl project authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import org.pkl.config.java.Config;
import org.pkl.config.java.ConfigEvaluator;
import org.pkl.config.java.JavaType;

import example.Birds.Bird;
import org.pkl.core.ModuleSource;

public class JavaCodeGeneratorExample {
  public static void main(String[] args) {
    Config config;
    // To learn more about the `ConfigEvaluator` API, see the `config-java` example.
    try(var evaluator = ConfigEvaluator.preconfigured()) {
      // "Module path" here represents Java's classpath. Since we put `config.pkl` inside the resources directory, it
      // is available in the classpath.
      config = evaluator.evaluate(ModuleSource.modulePath("/config.pkl"));
    }

    // Convert entire config to an instance of the generated config class.
    // This is the most convenient and most type-safe approach.
    var birds = config.as(Birds.class);
    System.out.println(birds.birds.get("Parrot"));

    // only convert the `birds` mapping
    var birdsMap = config.get("birds").as(JavaType.mapOf(String.class, Bird.class));
    System.out.println(birdsMap.get("Parrot"));

    // only convert the bird named "Parrot"
    var parrot = config.get("birds").get("Parrot").as(Bird.class);
    System.out.println(parrot);
  }
}
