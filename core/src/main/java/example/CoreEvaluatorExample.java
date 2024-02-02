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

import java.util.List;

import org.pkl.core.Evaluator;
import org.pkl.core.EvaluatorBuilder;
import org.pkl.core.ModuleSource;
import org.pkl.core.PModule;
import org.pkl.core.PObject;

public class CoreEvaluatorExample {
  public static void main(String[] args) {
    // A Pkl module is represented by an instance of `PModule`, a subclass of `PObject`.
    PModule module;
    var moduleSource = ModuleSource.text("parrot { name = \"Polly\"; age = 31; favoriteFoods = new Listing { \"apples\"; \"crackers\" } }");

    // To evaluate a Pkl module to a `PModule` object,
    // create a module `Evaluator` and call its `evaluate()` method.
    //
    // There are two ways to create an `Evaluator`:
    // * `Evaluator.preconfigured()`
    // * `EvaluatorBuilder`
    //
    // `Evaluator.preconfigured()` behaves much the same as the CLI.
    // For example, it allows evaluating modules over HTTPS, which isn't always desirable.
    // `EvaluatorBuilder` gives more control over a `Evaluator`'s behavior.
    //
    // It's important to close an evaluator after the last `evaluate()` call.
    // Previously returned `PModule` objects remain valid.
    try (var evaluator =
             Evaluator.preconfigured()) {
      module = evaluator.evaluate(moduleSource);
    }

    // To get a property value, use `PObject.get()`.
    // Pkl values are mapped to Java values as follows (in alphabetical order):
    // `Boolean`  -> `java.lang.Boolean`
    // `DataSize` -> `org.pkl.core.DataSize`
    // `Duration` -> `org.pkl.core.Duration`
    // `Dynamic`  -> `org.pkl.core.PObject`
    // `Float`    -> `java.lang.Double`
    // `Int`      -> `java.lang.Long`
    // `IntSeq`   -> `org.pkl.core.IntSeq`
    // `List`     -> `java.util.List`
    // `Listing`  -> `java.util.List`
    // `Map`      -> `java.util.Map`
    // `Mapping`  -> `java.util.Map`
    // `null`     -> `null` when using `PObject.get()`, `PNull` when using `PObject.getProperty()`
    // `Module`   -> `org.pkl.core.PModule`
    // `Pair`     -> `org.pkl.core.Pair`
    // `Set`      -> `java.util.Set`
    // `String`   -> `java.lang.String`
    // `Typed`    -> `org.pkl.core.PObject`
    var parrot = (PObject) module.get("parrot");
    System.out.println(parrot);

    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    var favoriteFoods = (List<String>) parrot.get("favoriteFoods");
    System.out.println(favoriteFoods);

    // `PObject.getClassInfo()` provides some information on an object's Pkl class.
    var className = parrot.getClassInfo().getQualifiedName();
    System.out.println(className);

    // There are other evaluation modes available on an evaluator.
    try (var evaluator = Evaluator.preconfigured()) {
      // `evaluateOutputText` evaluates a module's textual output, similar to the output of the CLI's `eval` command.
      var textualOutput = evaluator.evaluateOutputText(moduleSource);
      System.out.println(textualOutput);

      // `evaluateSchema` evaluates a module's schema, providing information on its classes and properties
      var schema = evaluator.evaluateSchema(moduleSource);
      System.out.println(schema.getModuleClass().getAllProperties().keySet());
    }
    // For the direct API equivalent of the CLI, use `org.pkl.cli.CliEvaluator` in the `pkl-cli` library.
  }
}
