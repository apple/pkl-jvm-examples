/**
 * Copyright © 2024-2025 Apple Inc. and the Pkl project authors. All rights reserved.
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
package example

import org.pkl.config.java.Config
import org.pkl.config.java.mapper.ValueMapperBuilder
import org.pkl.config.kotlin.forKotlin
import org.pkl.config.kotlin.to

fun main() {
  // Configuration is represented as a tree of `Config` objects.
  // To load pkl-binary data as a `Config` tree, use the `Config.fromPklBinary` method.
  //
  // For the best Kotlin experience, provide a `ValueMapper` for configured Kotlin using
  // `ValueMapperBuilder.preconfigured().forKotlin().build()`.
  val data = object {}.javaClass.getResourceAsStream("data.msgpack")
  val config = Config.fromPklBinary(data!!, ValueMapperBuilder.preconfigured().forKotlin().build())

  // To descend the `Config` tree, use the subscript operator (`[]`),
  // which returns another `Config` object.
  val parrot = config["birds"]["Parrot"]

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
