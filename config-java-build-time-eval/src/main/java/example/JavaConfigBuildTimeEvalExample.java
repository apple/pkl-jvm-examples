/**
 * Copyright © 2024-2025 Apple Inc. and the Pkl project authors. All rights reserved.
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
import org.pkl.config.java.JavaType;

public class JavaConfigBuildTimeEvalExample {
    public static void main(String[] args) {
        // Configuration is represented as a tree of `Config` objects.
        // To load pkl-binary data as a `Config` tree, use the `Config.fromPklBinary` method.
        //
        // For the best Kotlin experience, provide a `ValueMapper` for configured Kotlin using
        // `ValueMapperBuilder.preconfigured().forKotlin().build()`.
        var data = JavaConfigBuildTimeEvalExample.class.getResourceAsStream("data.msgpack");
        assert data != null;
        var config = Config.fromPklBinary(data);
        // To descend the `Config` tree, use the `get()` method,
        // which returns another `Config` object.
        var parrot = config.get("birds").get("Parrot");

        // To access a `Config` object's value,
        // call `as()` and pass the Java type to convert the value to.
        // The conversion is performed by `ConfigEvaluator.getValueMapper()`,
        // which can be customized via `ConfigEvaluatorBuilder`.
        var age = parrot.get("age").as(int.class);
        System.out.println(age);

        // The `as()` method also accepts parameterized types such as `List<String>`.
        var favoriteFoods = parrot.get("favoriteFoods").as(JavaType.listOf(String.class));
        System.out.println(favoriteFoods);
    }
}
