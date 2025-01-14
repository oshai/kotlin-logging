/*
 * Copyright (C) 2020 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.oshai.kotlinlogging.irplugin

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

// Based on https://github.com/bnorm/kotlin-ir-plugin-template
open class KotlinLoggingGradleExtension(objects: ObjectFactory) {
  val disableAll: Property<String> = objects.property(String::class.java)
  val disableTransformingDeprecatedApi: Property<String> = objects.property(String::class.java)
  val disableTransformingNotImplementedApi: Property<String> = objects.property(String::class.java)
  val disableTransformingEntryExitApi: Property<String> = objects.property(String::class.java)
  val disableTransformingThrowingCatchingApi: Property<String> =
    objects.property(String::class.java)
  val disableCollectingCallSiteInformation: Property<String> = objects.property(String::class.java)
}
