package io.github.oshai.kotlinlogging.irplugin

import com.javiersc.kotlin.compiler.extensions.ir.createIrBlockBody
import com.javiersc.kotlin.compiler.extensions.ir.createIrFunctionExpression
import com.javiersc.kotlin.compiler.extensions.ir.createLambdaIrSimpleFunction
import com.javiersc.kotlin.compiler.extensions.ir.toIrConstructorCall
import org.jetbrains.kotlin.backend.common.FileLoweringPass
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.backend.jvm.codegen.isAnnotatedWithDeprecated
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.UNDEFINED_OFFSET
import org.jetbrains.kotlin.ir.backend.js.utils.valueArguments
import org.jetbrains.kotlin.ir.builders.declarations.UNDEFINED_PARAMETER_INDEX
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irInt
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.classOrFail
import org.jetbrains.kotlin.ir.types.classifierOrFail
import org.jetbrains.kotlin.ir.types.createType
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.ir.types.typeWith
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.dumpKotlinLike
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.ir.util.getPropertySetter
import org.jetbrains.kotlin.ir.util.isEnumClass
import org.jetbrains.kotlin.ir.util.kotlinFqName
import org.jetbrains.kotlin.ir.util.primaryConstructor
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.*
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

private const val PACKAGE_NAME = "io.github.oshai.kotlinlogging"
private const val PACKAGE_NAME_INTERNAL = "io.github.oshai.kotlinlogging.internal"

class KotlinLoggingIrGenerationExtension(
  private val messageCollector: MessageCollector,
  private val config: KotlinLoggingIrPluginConfig,
) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    messageCollector.report(CompilerMessageSeverity.INFO, "Plugin config: $config")
    if (!config.disableAll) {
      for (file in moduleFragment.files) {
        AccessorCallTransformer(config, SourceFile(file), pluginContext, messageCollector)
          .runOnFileInOrder(file)
      }
    }
  }

  class TypesHelper(val context: IrPluginContext) {

    val atMethodName = Name.identifier("at")
    val extensionReceiverParameterName = Name.identifier("\$this\$at")

    val levelClassSymbol =
      context.referenceClass(ClassId(FqName(PACKAGE_NAME), Name.identifier("Level")))!!
    val markerClassSymbol =
      context.referenceClass(ClassId(FqName(PACKAGE_NAME), Name.identifier("Marker")))!!
    val markerType = markerClassSymbol.defaultType
    val throwableType = context.irBuiltIns.throwableType

    val function1Class = context.irBuiltIns.functionN(1)
    val unitType = context.irBuiltIns.unitType
    val eventBuilderClassSymbol =
      context.referenceClass(
        ClassId(FqName(PACKAGE_NAME), Name.identifier("KLoggingEventBuilder"))
      )!!
    val eventBuilderLambdaType =
      function1Class.typeWith(eventBuilderClassSymbol.defaultType, unitType)
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    val setMessageFunction = eventBuilderClassSymbol.owner.getPropertySetter("message")!!
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    val setCauseFunction = eventBuilderClassSymbol.owner.getPropertySetter("cause")!!
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    val setInternalCompilerDataFunction =
      eventBuilderClassSymbol.owner.getPropertySetter("internalCompilerData")!!
    val internalCompilerDataClassSymbol =
      context.referenceClass(
        ClassId(
          FqName(PACKAGE_NAME),
          FqName("KLoggingEventBuilder.InternalCompilerData"),
          isLocal = false,
        )
      )!!
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    val internalCompilerDataConstructor = internalCompilerDataClassSymbol.owner.primaryConstructor!!

    val function0Class = context.irBuiltIns.functionN(0)
    val anyType = context.irBuiltIns.anyType
    val messageBuilderLambdaType = function0Class.typeWith(anyType)
    val stringType = context.irBuiltIns.stringType

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    val toStringFunctionSymbol =
      context
        .referenceFunctions(
          CallableId(packageName = FqName(PACKAGE_NAME_INTERNAL), Name.identifier("toStringSafe"))
        )
        .single {
          it.owner.extensionReceiverParameter?.type?.classifierOrFail ==
            messageBuilderLambdaType.classifierOrFail
        }
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    val castToThrowableFunctionSymbol =
      context
        .referenceFunctions(
          CallableId(
            packageName = FqName(PACKAGE_NAME_INTERNAL),
            Name.identifier("castToThrowable"),
          )
        )
        .single {
          it.owner.extensionReceiverParameter?.type?.classifierOrFail == anyType.classifierOrFail
        }

    val kLoggerClassSymbol =
      context.referenceClass(ClassId(FqName(PACKAGE_NAME), Name.identifier("KLogger")))!!
  }

  class AccessorCallTransformer(
    private val config: KotlinLoggingIrPluginConfig,
    private val sourceFile: SourceFile,
    private val context: IrPluginContext,
    private val messageCollector: MessageCollector,
  ) : IrElementTransformerVoidWithContext(), FileLoweringPass {

    private val typesHelper = TypesHelper(context)

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    fun findKLoggerExtensionFunction(
      originalFunctionName: String,
      extraFilter: (IrFunction) -> Boolean = { _ -> true },
    ): IrSimpleFunctionSymbol {
      val overloadedFunctionName = "${originalFunctionName}WithCompilerData"
      return context
        .referenceFunctions(
          CallableId(
            packageName = FqName(PACKAGE_NAME_INTERNAL),
            Name.identifier(overloadedFunctionName),
          )
        )
        .single {
          it.owner.extensionReceiverParameter?.type?.classifierOrFail ==
            typesHelper.kLoggerClassSymbol.defaultType.classifierOrFail &&
            extraFilter.invoke(it.owner)
        }
    }

    override fun lower(irFile: IrFile) {
      irFile.transformChildrenVoid()
    }

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    override fun visitCall(expression: IrCall): IrExpression {
      try {
        expression.symbol.owner.parent.let {
          if (it.kotlinFqName == FqName("io.github.oshai.kotlinlogging.KLogger")) {
            return handleKLoggerFunction(expression)
          }
        }
        return super.visitCall(expression)
      } catch (e: Exception) {
        messageCollector.report(
          severity = CompilerMessageSeverity.ERROR,
          location = sourceFile.getCompilerMessageLocation(expression),
          message =
            """Kotlin-logging IR plugin failed.
          Please report to https://github.com/oshai/kotlin-logging/issues and include the below information.
          Error: ${e.message}
          ${e.stackTraceToString()}
          Expression source code: ${sourceFile.getText(expression)}
          Expression raw dump: ${expression.dump()}
          Expression Kotlin-like dump: ${expression.dumpKotlinLike()}
          """
              .trimIndent(),
        )
        throw e
      }
    }

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    private fun handleKLoggerFunction(expression: IrCall): IrExpression {
      val newExpr =
        when (expression.symbol.owner.name.asString()) {
          "trace",
          "debug",
          "info",
          "warn",
          "error" -> {
            if (
              config.disableTransformingNotImplementedApi &&
                expression.isNotImplementedKLoggerApiCall(typesHelper)
            ) {
              return super.visitCall(expression)
            }
            if (
              config.disableTransformingDeprecatedApi &&
                expression.symbol.owner.isAnnotatedWithDeprecated
            ) {
              return super.visitCall(expression)
            }
            val (newStatement, messageTemplate) = createAtStatementFromSimpleLoggingCall(expression)
            injectCompilerOptionsIntoAtStatement(
              expression,
              newStatement,
              collectCompilerData(expression, messageTemplate),
            )
            newStatement
          }
          "at",
          "atTrace",
          "atDebug",
          "atInfo",
          "atWarn",
          "atError" -> {
            val messageTemplate = collectMessageTemplateFromAtStatement(expression)
            injectCompilerOptionsIntoAtStatement(
              expression,
              expression,
              collectCompilerData(expression, messageTemplate),
            )
            expression
          }
          "entry",
          "exit" -> {
            if (!config.disableTransformingEntryExitApi) {
              createOverloadedFunctionCall(expression)
            } else {
              return super.visitCall(expression)
            }
          }
          "throwing",
          "catching" -> {
            if (!config.disableTransformingThrowingCatchingApi) {
              createOverloadedFunctionCall(expression)
            } else {
              super.visitCall(expression)
            }
          }
          else -> {
            return super.visitCall(expression)
          }
        }
      return newExpr
    }

    private fun collectCompilerData(expression: IrCall, messageTemplate: String) =
      if (config.disableCollectingCallSiteInformation) {
        KLoggingCallCompilerData(messageTemplate = messageTemplate)
      } else {
        KLoggingCallCompilerData(
          messageTemplate = messageTemplate,
          className = getCurrentClassName() ?: sourceFile.defaultClassName(),
          methodName = currentDeclarationParent?.kotlinFqName?.shortName()?.asString(),
          lineNumber =
            sourceFile.getSourceRangeInfo(expression).startLineNumber +
              1, // somehow, the line numbers are off by one in tests
          fileName = sourceFile.fileName(),
        )
      }

    private fun getCurrentClassName(): String? {
      val currentClass = currentClass
      if (currentClass != null && currentClass.irElement is IrClass) {
        return (currentClass.irElement as IrClass).kotlinFqName.asString()
      }
      return null
    }

    data class KLoggingCallCompilerData(
      val messageTemplate: String? = null,
      val className: String? = null,
      val methodName: String? = null,
      val lineNumber: Int? = null,
      val fileName: String? = null,
    )

    private fun makeCompilerDataConstructorCall(
      compilerData: KLoggingCallCompilerData,
      builder: DeclarationIrBuilder,
    ): IrConstructorCall {
      return typesHelper.internalCompilerDataConstructor.toIrConstructorCall().apply {
        putValueArgument(0, compilerData.messageTemplate?.let { builder.irString(it) })
        putValueArgument(1, compilerData.className?.let { builder.irString(it) })
        putValueArgument(2, compilerData.methodName?.let { builder.irString(it) })
        putValueArgument(3, compilerData.lineNumber?.let { builder.irInt(it) })
        putValueArgument(4, compilerData.fileName?.let { builder.irString(it) })
      }
    }

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    private fun collectMessageTemplateFromAtStatement(atCall: IrCall): String {
      val function = atCall.symbol.owner
      var messageExpression: IrExpression? = null
      function.valueParameters.forEachIndexed { index, parameter ->
        if (
          parameter.type.classifierOrFail == typesHelper.eventBuilderLambdaType.classifierOrFail
        ) {
          val eventBuilderLambdaArgument = atCall.valueArguments[index] as IrFunctionExpression
          val eventBuilderLambdaBody = eventBuilderLambdaArgument.function.body!!
          eventBuilderLambdaBody.statements.forEach { statement ->
            if (statement is IrCall && statement.symbol == typesHelper.setMessageFunction) {
              messageExpression = statement.valueArguments[0]
            }
          }
        }
      }
      return sourceFile.getText(messageExpression!!)
    }

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    private fun injectCompilerOptionsIntoAtStatement(
      originalLogExpression: IrCall,
      atCall: IrCall,
      compilerData: KLoggingCallCompilerData,
    ) {
      val symbol = currentScope!!.scope.scopeOwnerSymbol
      val builder =
        DeclarationIrBuilder(
          context,
          symbol,
          originalLogExpression.startOffset,
          originalLogExpression.endOffset,
        )
      val eventBuilderLambdaArgument = atCall.valueArguments.last() as IrFunctionExpression
      val eventBuilderLambdaBody = eventBuilderLambdaArgument.function.body!! as IrBlockBody
      eventBuilderLambdaBody.statements.addFirst(
        builder.irCall(typesHelper.setInternalCompilerDataFunction.owner).apply {
          dispatchReceiver =
            IrGetValueImpl(
              startOffset = UNDEFINED_OFFSET,
              endOffset = UNDEFINED_OFFSET,
              symbol = eventBuilderLambdaArgument.function.extensionReceiverParameter!!.symbol,
            )
          putValueArgument(0, makeCompilerDataConstructorCall(compilerData, builder))
        }
      )
    }

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    private fun createOverloadedFunctionCall(originalLogExpression: IrCall): IrExpression {
      val symbol = currentScope!!.scope.scopeOwnerSymbol
      val builder =
        DeclarationIrBuilder(
          context,
          symbol,
          originalLogExpression.startOffset,
          originalLogExpression.endOffset,
        )
      val loggerExpression = originalLogExpression.dispatchReceiver!!
      val originalFunction = originalLogExpression.symbol.owner
      val functionName = originalFunction.name.toString()
      val overloadedFunction =
        findKLoggerExtensionFunction(functionName) {
          it.valueParameters.size == originalFunction.valueParameters.size + 1 &&
            it.valueParameters.first().type.classifierOrFail ==
              typesHelper.internalCompilerDataClassSymbol.defaultType.classifierOrFail
        }
      val firstArgument =
        if (originalLogExpression.valueArguments.isNotEmpty()) {
          originalLogExpression.valueArguments[0]
        } else {
          null
        }
      val compilerData =
        collectCompilerData(
          originalLogExpression,
          functionName +
            (if (firstArgument != null) "(${sourceFile.getText(firstArgument)})" else ""),
        )
      return builder.irCall(overloadedFunction).apply {
        extensionReceiver = loggerExpression
        putValueArgument(0, makeCompilerDataConstructorCall(compilerData, builder))
        originalLogExpression.valueArguments.forEachIndexed { index, exp ->
          putValueArgument(index + 1, exp)
        }
      }
    }

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    private fun createAtStatementFromSimpleLoggingCall(
      originalLogExpression: IrCall
    ): Pair<IrCall, String> {
      val symbol = currentScope!!.scope.scopeOwnerSymbol
      val builder =
        DeclarationIrBuilder(
          context,
          symbol,
          originalLogExpression.startOffset,
          originalLogExpression.endOffset,
        )
      val loggingCallExpressions =
        SimpleLoggingCallExpressionsBuilder(typesHelper, currentScope!!, sourceFile)
          .getExpressions(originalLogExpression)
      val loggerExpression = originalLogExpression.dispatchReceiver!!
      val atFunction =
        loggerExpression.type.classOrFail.owner.functions.single {
          it.name == typesHelper.atMethodName &&
            sameParameters(
              it.valueParameters,
              listOf(
                typesHelper.levelClassSymbol.defaultType,
                typesHelper.markerClassSymbol.createType(true, listOf()),
                typesHelper.eventBuilderLambdaType,
              ),
            )
        }
      return Pair(
        builder.irCall(atFunction).apply {
          dispatchReceiver = loggerExpression
          putValueArgument(0, loggingCallExpressions.levelExpression)
          putValueArgument(1, loggingCallExpressions.markerExpression)
          putValueArgument(
            2,
            createIrFunctionExpression(
              type = typesHelper.eventBuilderLambdaType,
              function =
                context.createLambdaIrSimpleFunction {
                  extensionReceiverParameter =
                    factory
                      .createValueParameter(
                        startOffset = UNDEFINED_OFFSET,
                        endOffset = UNDEFINED_OFFSET,
                        origin = IrDeclarationOrigin.DEFINED,
                        name = typesHelper.extensionReceiverParameterName,
                        type = typesHelper.eventBuilderClassSymbol.defaultType,
                        isAssignable = false,
                        symbol = IrValueParameterSymbolImpl(),
                        index = UNDEFINED_PARAMETER_INDEX,
                        varargElementType = null,
                        isCrossinline = false,
                        isNoinline = false,
                        isHidden = false,
                      )
                      .apply { parent = this@createLambdaIrSimpleFunction }
                  returnType = typesHelper.unitType
                  parent = currentDeclarationParent!!
                  body =
                    context.createIrBlockBody {
                      statements.add(
                        builder.irCall(typesHelper.setMessageFunction.owner).apply {
                          dispatchReceiver =
                            IrGetValueImpl(
                              startOffset = UNDEFINED_OFFSET,
                              endOffset = UNDEFINED_OFFSET,
                              symbol = extensionReceiverParameter!!.symbol,
                            )
                          parent = currentDeclarationParent!!
                          putValueArgument(0, loggingCallExpressions.messageExpression)
                        }
                      )
                      if (loggingCallExpressions.causeExpression != null) {
                        statements.add(
                          builder.irCall(typesHelper.setCauseFunction.owner).apply {
                            dispatchReceiver =
                              IrGetValueImpl(
                                startOffset = UNDEFINED_OFFSET,
                                endOffset = UNDEFINED_OFFSET,
                                symbol = extensionReceiverParameter!!.symbol,
                              )
                            parent = currentDeclarationParent!!
                            putValueArgument(0, loggingCallExpressions.causeExpression)
                          }
                        )
                      }
                    }
                },
              origin = IrStatementOrigin.LAMBDA,
            ),
          )
        } as IrCall,
        loggingCallExpressions.messageTemplate,
      )
    }

    private fun sameParameters(
      valueParameters: List<IrValueParameter>,
      valueArguments: List<IrType>,
    ): Boolean {
      if (valueParameters.size != valueArguments.size) return false
      for (i in valueParameters.indices) {
        if (valueParameters[i].type.classifierOrFail != valueArguments[i].classifierOrFail)
          return false
      }
      return true
    }
  }
}

@OptIn(UnsafeDuringIrConstructionAPI::class)
private fun IrCall.isNotImplementedKLoggerApiCall(
  typesHelper: KotlinLoggingIrGenerationExtension.TypesHelper
): Boolean {
  val function = symbol.owner
  val functionParameters = function.valueParameters
  if (
    functionParameters.any {
      it.type.classifierOrFail == typesHelper.throwableType.classifierOrFail
    }
  ) {
    // arguments: message, throwable
    // arguments: marker, message, throwable
    // arguments: marker, throwable, message builder
    return false
  }
  if (functionParameters.size > 2) {
    // arguments: marker, message, arg - not supported by KLogger API
    // arguments: marker, message, arg1, arg2 - not supported by KLogger API
    // arguments: marker, message, vararg - not supported by KLogger API
    // arguments: message, arg1, arg2 - not supported by KLogger API
    return true
  }
  if (
    functionParameters.size > 1 &&
      functionParameters.first().type.classifierOrFail == typesHelper.stringType.classifierOrFail
  ) {
    // arguments: message, arg - not supported by KLogger API
    // arguments: message, vararg - not supported by KLogger API
    return true
  }
  return false
}

@OptIn(UnsafeDuringIrConstructionAPI::class)
fun IrClass.enumEntry(enumName: String): IrEnumEntry? =
  if (isEnumClass) {
    declarations.filterIsInstance<IrEnumEntry>().firstOrNull { enumEntry ->
      "${enumEntry.name}" == enumName
    }
  } else null

fun FileLoweringPass.runOnFileInOrder(irFile: IrFile) {
  irFile.acceptVoid(
    object : IrElementVisitorVoid {
      override fun visitElement(element: IrElement) {
        element.acceptChildrenVoid(this)
      }

      override fun visitFile(declaration: IrFile) {
        lower(declaration)
        declaration.acceptChildrenVoid(this)
      }
    }
  )
}
