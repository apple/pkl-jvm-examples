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
package example

import org.pkl.config.java.ConfigEvaluator
import org.pkl.config.kotlin.forKotlin
import org.pkl.config.kotlin.to
import org.pkl.core.ModuleSource

fun main() {
  // Configuration is represented as a tree of `Config` objects.
  // To evaluate a Pkl module to a `Config` tree,
  // create a `ConfigEvaluator` and call its `evaluate()` method.
  //
  // There are two ways to create a `ConfigEvaluator`:
  // * `ConfigEvaluator.preconfigured()`
  // * `ConfigEvaluatorBuilder`
  //
  // `ConfigEvaluator.preconfigured()` behaves much the same as the CLI.
  // For example, it allows evaluating modules over HTTPS, which isn't always desirable.
  // `ConfigEvaluatorBuilder` gives more control over a `ConfigEvaluator`'s behavior.
  //
  // For the best Kotlin experience, use `ConfigEvaluator(Builder).forKotlin()`.
  //
  // It's important to close an evaluator after the last `evaluate()` call.
  // Previously returned `Config` objects remain valid.
  val config = ConfigEvaluator.preconfigured().forKotlin().use { evaluator ->
    evaluator.evaluate(ModuleSource.text("""
      parrot {
        name = "Polly"
        age = 31
        favoriteFoods = new Listing { "apples"; "crackers" }
      }
      """.trimIndent()))
  }

  // To descend the `Config` tree, use the subscript operator (`[]`),
  // which returns another `Config` object.
  val parrot = config["parrot"]

  // To access a `Config` object's value,
  // call `to()` and pass the Kotlin type to convert the value to.
  // (The `to()` method is the Kotlin equivalent of the Java API's `as()` method.)
  // The conversion is performed by `ConfigEvaluator.getValueMapper()`,
  // which can be customized via `ConfigEvaluatorBuilder.getValueMapperBuilder()`.
  val age = parrot["age"].to<Int>()
  println(age)

  // The `to()` method also accepts parameterized types such as `List<String>`.
  val favoriteFoods = parrot["favoriteFoods"].to<List<String>>()
  println(favoriteFoods)
}
