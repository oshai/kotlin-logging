package io.github.oshai.kotlinlogging.irplugin

data class KotlinLoggingIrPluginConfig(
  val disableAll: Boolean = false,
  val disableTransformingDeprecatedApi: Boolean = false,
  val disableTransformingNotImplementedApi: Boolean = false,
  val disableTransformingEntryExitApi: Boolean = false,
  val disableTransformingThrowingCatchingApi: Boolean = false,
  val disableCollectingCallSiteInformation: Boolean = false,
)
