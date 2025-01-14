package io.github.oshai.kotlinlogging.irplugin

enum class FeatureFlag(
  val configurer: () -> KotlinLoggingIrPluginConfig,
  val expectationAdjuster: TestExecutionResultBuilder.() -> Unit,
) {
  DEFAULT({ KotlinLoggingIrPluginConfig() }, {}),
  DISABLE_ALL(
    { KotlinLoggingIrPluginConfig(disableAll = true) },
    {
      loggedEvent {
        message = formattedMessage
        callerDataFirstElement = null
      }
    },
  ),
  DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API(
    { KotlinLoggingIrPluginConfig(disableTransformingNotImplementedApi = true) },
    {},
  ),
  DISABLE_TRANSFORMING_DEPRECATED_API(
    { KotlinLoggingIrPluginConfig(disableTransformingDeprecatedApi = true) },
    {},
  ),
  DISABLE_TRANSFORMING_ENTRY_EXIT_API(
    { KotlinLoggingIrPluginConfig(disableTransformingEntryExitApi = true) },
    {},
  ),
  DISABLE_TRANSFORMING_THROWING_CATCHING_API(
    { KotlinLoggingIrPluginConfig(disableTransformingThrowingCatchingApi = true) },
    {},
  ),
  DISABLE_COLLECTING_CALL_SITE_INFORMATION(
    { KotlinLoggingIrPluginConfig(disableCollectingCallSiteInformation = true) },
    { loggedEvent { callerDataFirstElement = null } },
  ),
}

data class FeatureFlagExpectationAdjuster(
  val applicableFeatureFlags: Set<FeatureFlag>,
  val expectationAdjuster: TestExecutionResultBuilder.() -> Unit,
)

class FeatureFlagExpectationAdjusterBuilder {
  private val applicableFeatureFlags: MutableSet<FeatureFlag> = mutableSetOf()
  private var expectationAdjuster: (TestExecutionResultBuilder.() -> Unit)? = null

  fun featureFlags(vararg flags: FeatureFlag) {
    applicableFeatureFlags.addAll(flags)
  }

  fun adjuster(block: TestExecutionResultBuilder.() -> Unit) {
    expectationAdjuster = block
  }

  fun build(): FeatureFlagExpectationAdjuster {
    return FeatureFlagExpectationAdjuster(
      applicableFeatureFlags = applicableFeatureFlags,
      expectationAdjuster = expectationAdjuster!!,
    )
  }
}
