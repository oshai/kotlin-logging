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

@file:OptIn(ExperimentalCompilerApi::class)

package io.github.oshai.kotlinlogging.irplugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

// Based on https://github.com/bnorm/kotlin-ir-plugin-template
@AutoService(CommandLineProcessor::class)
class KotlinLoggingCommandLineProcessor : CommandLineProcessor {
  companion object {
    private const val OPTION_DISABLE_ALL = "disableAll"
    private const val OPTION_DISABLE_TRANSFORMING_DEPRECATED_API =
      "disableTransformingDeprecatedApi"
    private const val OPTION_DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API =
      "disableTransformingNotImplementedApi"
    private const val OPTION_DISABLE_TRANSFORMING_ENTRY_EXIT_API = "disableTransformingEntryExitApi"
    private const val OPTION_DISABLE_TRANSFORMING_THROWING_CATCHING_API =
      "disableTransformingThrowingCatchingApi"
    private const val OPTION_DISABLE_COLLECTING_CALL_SITE_INFORMATION =
      "disableCollectingCallSiteInformation"

    val ARG_DISABLE_ALL = CompilerConfigurationKey<Boolean>(OPTION_DISABLE_ALL)
    val ARG_DISABLE_TRANSFORMING_DEPRECATED_API =
      CompilerConfigurationKey<Boolean>(OPTION_DISABLE_TRANSFORMING_DEPRECATED_API)
    val ARG_DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API =
      CompilerConfigurationKey<Boolean>(OPTION_DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API)
    val ARG_DISABLE_TRANSFORMING_ENTRY_EXIT_API =
      CompilerConfigurationKey<Boolean>(OPTION_DISABLE_TRANSFORMING_ENTRY_EXIT_API)
    val ARG_DISABLE_TRANSFORMING_THROWING_CATCHING_API =
      CompilerConfigurationKey<Boolean>(OPTION_DISABLE_TRANSFORMING_THROWING_CATCHING_API)
    val ARG_DISABLE_COLLECTING_CALL_SITE_INFORMATION =
      CompilerConfigurationKey<Boolean>(OPTION_DISABLE_COLLECTING_CALL_SITE_INFORMATION)
  }

  override val pluginId: String = BuildConfig.KOTLIN_PLUGIN_ID

  override val pluginOptions: Collection<CliOption> =
    listOf(
      CliOption(
        optionName = OPTION_DISABLE_ALL,
        valueDescription = "boolean",
        description = "Disable all transformations",
        required = false,
      ),
      CliOption(
        optionName = OPTION_DISABLE_TRANSFORMING_DEPRECATED_API,
        valueDescription = "boolean",
        description = "Disable transforming KLogger deprecated API to non-deprecated API",
        required = false,
      ),
      CliOption(
        optionName = OPTION_DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API,
        valueDescription = "boolean",
        description =
          "Disable transforming KLogger deprecated (and not implemented) API to non-deprecated API",
        required = false,
      ),
      CliOption(
        optionName = OPTION_DISABLE_TRANSFORMING_ENTRY_EXIT_API,
        valueDescription = "boolean",
        description = "Disable transforming KLogger entry/exit API",
        required = false,
      ),
      CliOption(
        optionName = OPTION_DISABLE_TRANSFORMING_THROWING_CATCHING_API,
        valueDescription = "boolean",
        description = "Disable transforming KLogger throwing/catching API",
        required = false,
      ),
      CliOption(
        optionName = OPTION_DISABLE_COLLECTING_CALL_SITE_INFORMATION,
        valueDescription = "boolean",
        description = "Disable collecting call site information",
        required = false,
      ),
    )

  override fun processOption(
    option: AbstractCliOption,
    value: String,
    configuration: CompilerConfiguration,
  ) {
    return when (option.optionName) {
      OPTION_DISABLE_ALL -> configuration.put(ARG_DISABLE_ALL, value.toBoolean())
      OPTION_DISABLE_TRANSFORMING_DEPRECATED_API ->
        configuration.put(ARG_DISABLE_TRANSFORMING_DEPRECATED_API, value.toBoolean())
      OPTION_DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API ->
        configuration.put(ARG_DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API, value.toBoolean())
      OPTION_DISABLE_TRANSFORMING_ENTRY_EXIT_API ->
        configuration.put(ARG_DISABLE_TRANSFORMING_ENTRY_EXIT_API, value.toBoolean())
      OPTION_DISABLE_TRANSFORMING_THROWING_CATCHING_API ->
        configuration.put(ARG_DISABLE_TRANSFORMING_THROWING_CATCHING_API, value.toBoolean())
      OPTION_DISABLE_COLLECTING_CALL_SITE_INFORMATION ->
        configuration.put(ARG_DISABLE_COLLECTING_CALL_SITE_INFORMATION, value.toBoolean())
      else -> throw IllegalArgumentException("Unexpected config option: ${option.optionName}")
    }
  }
}
