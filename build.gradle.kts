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
  idea
  kotlin("jvm").version("2.2.20").apply(false)
  id("com.diffplug.spotless")
}

val originalRemoteName = System.getenv("PKL_ORIGINAL_REMOTE_NAME") ?: "origin"

spotless {
  ratchetFrom = "$originalRemoteName/main"

  val starBlockLicenseHeader =
    """
    /**
     * Copyright © ${'$'}YEAR Apple Inc. and the Pkl project authors. All rights reserved.
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
    """
      .trimIndent()
  val lineCommentLicenseHeader =
    """
    //===----------------------------------------------------------------------===//
    // Copyright © ${'$'}YEAR Apple Inc. and the Pkl project authors. All rights reserved.
    //
    // Licensed under the Apache License, Version 2.0 (the "License");
    // you may not use this file except in compliance with the License.
    // You may obtain a copy of the License at
    //
    //     https://www.apache.org/licenses/LICENSE-2.0
    //
    // Unless required by applicable law or agreed to in writing, software
    // distributed under the License is distributed on an "AS IS" BASIS,
    // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    // See the License for the specific language governing permissions and
    // limitations under the License.
    //===----------------------------------------------------------------------===//
  """
      .trimIndent()
  java {
    target("**/*.java")
    licenseHeader(starBlockLicenseHeader)
  }
  kotlin {
    target("**/*.kt")
    licenseHeader(starBlockLicenseHeader)
    ktfmt().googleStyle()
  }
  kotlinGradle {
    target("**/*.kts")
    licenseHeader(starBlockLicenseHeader, "([a-zA-Z]|@file|//)")
    ktfmt().googleStyle()
  }
  format("pkl") {
    target("**/*.pkl")
    licenseHeader(lineCommentLicenseHeader, "(/// |module |import |amends |(\\w+))")
    addStep(PklFormatterStep().create())
  }
}

repositories { mavenCentral() }
