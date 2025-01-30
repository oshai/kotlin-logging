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

import java.io.File
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocationWithRange
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.SourceRangeInfo
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.declarations.path
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly

// Copied from
// https://github.com/bnorm/kotlin-power-assert/blob/master/kotlin-power-assert-plugin/src/main/kotlin/com/bnorm/power/diagram/SourceFile.kt
data class SourceFile(private val irFile: IrFile) {
  private val source: String =
    File(irFile.path)
      .readText()
      .replace("\r\n", "\n") // https://youtrack.jetbrains.com/issue/KT-41888

  @OptIn(UnsafeDuringIrConstructionAPI::class)
  fun getSourceRangeInfo(element: IrElement): SourceRangeInfo {
    var range = element.startOffset..element.endOffset
    when (element) {
      is IrCall -> {
        val receiver = element.extensionReceiver ?: element.dispatchReceiver
        if (element.symbol.owner.isInfix && receiver != null) {
          // When an infix function is called *not* with infix notation, the startOffset will not
          // include the receiver.
          // Force the range to include the receiver, so it is always present
          range = receiver.startOffset..element.endOffset

          // The offsets of the receiver will *not* include surrounding parentheses so these need to
          // be checked for
          // manually.
          val substring = safeSubstring(receiver.startOffset - 1, receiver.endOffset + 1)
          if (substring.startsWith('(') && substring.endsWith(')')) {
            range = receiver.startOffset - 1..element.endOffset
          }
        }
      }
    }
    return irFile.fileEntry.getSourceRangeInfo(range.first, range.last)
  }

  fun getText(element: IrElement) = getText(getSourceRangeInfo(element))

  private fun getText(info: SourceRangeInfo): String {
    return safeSubstring(info.startOffset, info.endOffset)
  }

  private fun safeSubstring(start: Int, end: Int): String =
    source.substring(maxOf(start, 0), minOf(end, source.length))

  fun fileName() = irFile.name

  fun defaultClassName() =
    irFile.packageFqName.toString() +
      "." +
      irFile.name.capitalizeAsciiOnly().substringBefore(".kt") +
      "Kt"

  fun getCompilerMessageLocation(element: IrElement): CompilerMessageSourceLocation {
    val info = getSourceRangeInfo(element)
    val lineContent = getText(info)
    return CompilerMessageLocationWithRange.create(
      irFile.path,
      info.startLineNumber,
      info.startColumnNumber,
      info.endLineNumber,
      info.endColumnNumber,
      lineContent,
    )!!
  }
}
