package io.github.oshai.kotlinlogging.irplugin

data class LogStatement(
  val funName: String,
  val arguments: List<String> = emptyList(),
  val lastArgumentLambda: String? = null,
)
