package io.github.oshai.kotlinlogging.irplugin

import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.backend.js.utils.valueArguments
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrExpression

class PlaceholderReplacer(
  private val typesHelper: KotlinLoggingIrGenerationExtension.TypesHelper,
  private val builder: DeclarationIrBuilder,
) {
  data class ReplaceResult(val newExpression: IrExpression, val newArgIndex: Int)

  fun replace(
    msgExp: IrExpression?,
    valueArguments: List<IrElement?>,
    currentArgIndex: Int,
    placeholder: String,
  ): ReplaceResult {
    var newArgIndex = currentArgIndex
    if (msgExp is IrConst<*>) {
      val value = msgExp.value
      if (value is String && value.contains(placeholder)) {
        val pair =
          replacePlaceholdersInStringConstant(value, valueArguments, newArgIndex, placeholder)
        val newExpression = pair.first
        newArgIndex = pair.second
        return ReplaceResult(newExpression!!, newArgIndex)
      }
    } else if (msgExp is IrCall) {
      val dispatchResult =
        replace(msgExp.dispatchReceiver, valueArguments, newArgIndex, placeholder)
      msgExp.dispatchReceiver = dispatchResult.newExpression
      newArgIndex = dispatchResult.newArgIndex
      msgExp.valueArguments.forEachIndexed { index, valueArgument ->
        val argResult = replace(valueArgument, valueArguments, newArgIndex, placeholder)
        msgExp.putValueArgument(index, argResult.newExpression)
        newArgIndex = argResult.newArgIndex
      }
    } else {
      error("Unknown message expression: $msgExp")
    }
    return ReplaceResult(msgExp, newArgIndex)
  }

  private fun replacePlaceholdersInStringConstant(
    msg: String,
    valueArguments: List<IrElement?>,
    currentArgIndex: Int,
    placeholder: String,
  ): Pair<IrExpression?, Int> {
    val plusFunctionSymbol = typesHelper.context.irBuiltIns.extensionStringPlus
    var newArgIndex = currentArgIndex
    if (msg == placeholder) {
      return Pair(
        if (valueArguments.size > newArgIndex) valueArguments[newArgIndex++] as IrExpression
        else builder.irString(placeholder),
        newArgIndex,
      )
    }
    val msgParts = msg.split(placeholder)
    val newExpression =
      msgParts.foldIndexed(null as IrExpression?) { index, accumulator, msgPart ->
        if (index < msgParts.size - 1) {
          val arg =
            if (valueArguments.size > newArgIndex) valueArguments[newArgIndex++]
            else builder.irString(placeholder)
          if (index == 0) {
            // first element
            builder.irCall(plusFunctionSymbol).apply {
              dispatchReceiver = builder.irString(msgPart)
              putValueArgument(0, arg!! as IrExpression)
            }
          } else {
            // in-between elements
            builder.irCall(plusFunctionSymbol).apply {
              dispatchReceiver = accumulator
              putValueArgument(
                0,
                builder.irCall(plusFunctionSymbol).apply {
                  dispatchReceiver = builder.irString(msgPart)
                  putValueArgument(0, arg!! as IrExpression)
                },
              )
            }
          }
        } else {
          // last element
          builder.irCall(plusFunctionSymbol).apply {
            dispatchReceiver = accumulator
            putValueArgument(0, builder.irString(msgPart))
          }
        }
      }
    return Pair(newExpression, newArgIndex)
  }
}
