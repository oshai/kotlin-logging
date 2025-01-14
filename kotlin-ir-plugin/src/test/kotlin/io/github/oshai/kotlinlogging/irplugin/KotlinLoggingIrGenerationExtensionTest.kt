package io.github.oshai.kotlinlogging.irplugin

import io.github.oshai.kotlinlogging.KLogger
import java.io.File
import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

const val MARKER_PLACEHOLDER = "{marker}"
const val MARKER_VARIABLE_NAME = "marker"
const val THROWABLE_PLACEHOLDER = "{throwable}"
const val THROWABLE_VARIABLE_NAME = "throwable"
const val EXCEPTION_MESSAGE = "expected!"

class KotlinLoggingIrGenerationExtensionTest {

  private val testDefinitions = rootCollection {
    name = "root"
    sequenceOf(true, false).forEach { withClass ->
      collection {
        name = "with class=$withClass"
        collection {
          name = "entry/exit API"
          featureFlagExpectationAdjuster {
            featureFlags(FeatureFlag.DISABLE_TRANSFORMING_ENTRY_EXIT_API)
            adjuster {
              loggedEvent {
                message = formattedMessage
                callerDataFirstElement = null
              }
            }
          }
          test {
            code {
              useClass = withClass
              useThrowable = false
              useMarker = false
              initCode = "val argument1 = 42; val argument2 = true"
              logStatement =
                LogStatement(funName = "entry", arguments = listOf("argument1", "argument2"))
            }
            expect {
              loggedEvent {
                level = TestLoggingLevel.TRACE
                message = "entry(argument1, argument2)"
                formattedMessage = "entry(42, true)"
                hasMarker = false
                hasThrowable = false
              }
            }
          }
          test {
            code {
              useClass = withClass
              useThrowable = false
              useMarker = false
              initCode = "val resultValue = 42"
              funReturnType = Int::class
              logStatement = LogStatement(funName = "exit", arguments = listOf("resultValue"))
            }
            expect {
              returnedValue = 42
              loggedEvent {
                level = TestLoggingLevel.TRACE
                message = "exit(resultValue)"
                formattedMessage = "exit(42)"
                hasMarker = false
                hasThrowable = false
              }
            }
          }
          test {
            code {
              useClass = withClass
              useThrowable = false
              useMarker = false
              logStatement = LogStatement(funName = "exit")
            }
            expect {
              loggedEvent {
                level = TestLoggingLevel.TRACE
                message = "exit"
                formattedMessage = "exit"
                hasMarker = false
                hasThrowable = false
              }
            }
          }
        }
        collection {
          name = "throwing/catching API"
          featureFlagExpectationAdjuster {
            featureFlags(FeatureFlag.DISABLE_TRANSFORMING_THROWING_CATCHING_API)
            adjuster {
              loggedEvent {
                message = formattedMessage
                callerDataFirstElement = null
              }
            }
          }
          test {
            code {
              useClass = withClass
              useThrowable = true
              useMarker = false
              logStatement =
                LogStatement(funName = "throwing", arguments = listOf(THROWABLE_PLACEHOLDER))
              throwReturnValueFromLogStatement = true
            }
            expect {
              thrownExceptionToString = "java.lang.Exception: expected!"
              loggedEvent {
                level = TestLoggingLevel.ERROR
                message = "throwing(throwable)"
                formattedMessage = "throwing(java.lang.Exception: expected!)"
                hasMarker = false
                hasThrowable = true
              }
            }
          }
          test {
            code {
              useClass = withClass
              useThrowable = true
              useMarker = false
              logStatement =
                LogStatement(funName = "catching", arguments = listOf(THROWABLE_PLACEHOLDER))
            }
            expect {
              loggedEvent {
                level = TestLoggingLevel.ERROR
                message = "catching(throwable)"
                formattedMessage = "catching(java.lang.Exception: expected!)"
                hasMarker = false
                hasThrowable = true
              }
            }
          }
        }
        TestLoggingLevel.entries.forEach { withLogLevel ->
          collection {
            name = "with log level=${withLogLevel.levelEnum}"
            sequenceOf(true, false).forEach { withThrowable ->
              collection {
                name = "with throwable=$withThrowable"
                sequenceOf(true, false).forEach { withMarker ->
                  collection {
                    name = "with marker=$withMarker"
                    test {
                      skip =
                        (withMarker &&
                          !withThrowable) // skip variant with only marker and message builder --
                      // that is handled in deprecated API container
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        logStatement =
                          LogStatement(
                            funName = withLogLevel.levelName,
                            arguments = listOf(THROWABLE_PLACEHOLDER, MARKER_PLACEHOLDER),
                            lastArgumentLambda = """ "${withLogLevel.levelName} messageBuilder" """,
                          )
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message = "\"${withLogLevel.levelName} messageBuilder\""
                          formattedMessage = "${withLogLevel.levelName} messageBuilder"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    test {
                      skip =
                        (withMarker &&
                          !withThrowable) // skip variant with only marker and message builder --
                      // that is handled in deprecated API container
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        initCode = "val i = 42"
                        logStatement =
                          LogStatement(
                            funName = withLogLevel.levelName,
                            arguments = listOf(THROWABLE_PLACEHOLDER, MARKER_PLACEHOLDER),
                            lastArgumentLambda =
                              """ "${withLogLevel.levelName} messageBuilder ${expression("i")}" """,
                          )
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message =
                            "\"${withLogLevel.levelName} messageBuilder ${expression("i")}\""
                          formattedMessage = "${withLogLevel.levelName} messageBuilder 42"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    test {
                      skip =
                        (withMarker &&
                          !withThrowable) // skip variant with only marker and message builder --
                      // that is handled in deprecated API container
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        initCode = "val i = 42"
                        logStatement =
                          LogStatement(
                            funName = withLogLevel.levelName,
                            arguments = listOf(THROWABLE_PLACEHOLDER, MARKER_PLACEHOLDER),
                            lastArgumentLambda =
                              """ "${withLogLevel.levelName} messageBuilder ${expression("i")} ${expression("helper()")}" """,
                          )
                        extraMethodCode = """fun helper() = "Hello!""""
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message =
                            "\"${withLogLevel.levelName} messageBuilder ${expression("i")} ${expression("helper()")}\""
                          formattedMessage = "${withLogLevel.levelName} messageBuilder 42 Hello!"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    collection {
                      name = "deprecated ${KLogger::class.simpleName} API"
                      featureFlagExpectationAdjuster {
                        featureFlags(FeatureFlag.DISABLE_TRANSFORMING_DEPRECATED_API)
                        adjuster {
                          loggedEvent {
                            message = formattedMessage
                            callerDataFirstElement = null
                          }
                        }
                      }
                      test {
                        skip = !withMarker // skip variants without marker -- those are handled in
                        // non-deprecated API tests
                        code {
                          useClass = withClass
                          useThrowable = withThrowable
                          useMarker = withMarker
                          logStatement =
                            LogStatement(
                              funName = withLogLevel.levelName,
                              arguments = listOf(MARKER_PLACEHOLDER, THROWABLE_PLACEHOLDER),
                              lastArgumentLambda =
                                """ "${withLogLevel.levelName} messageBuilder" """,
                            )
                        }
                        expect {
                          loggedEvent {
                            level = withLogLevel
                            message = "\"${withLogLevel.levelName} messageBuilder\""
                            formattedMessage = "${withLogLevel.levelName} messageBuilder"
                            hasMarker = withMarker
                            hasThrowable = withThrowable
                          }
                        }
                      }
                      test {
                        skip = !withMarker // skip variants without marker -- those are handled in
                        // non-deprecated API tests
                        code {
                          useClass = withClass
                          useThrowable = withThrowable
                          useMarker = withMarker
                          initCode = "val i = 42"
                          logStatement =
                            LogStatement(
                              funName = withLogLevel.levelName,
                              arguments = listOf(MARKER_PLACEHOLDER, THROWABLE_PLACEHOLDER),
                              lastArgumentLambda =
                                """ "${withLogLevel.levelName} messageBuilder ${expression("i")}" """,
                            )
                        }
                        expect {
                          loggedEvent {
                            level = withLogLevel
                            message =
                              "\"${withLogLevel.levelName} messageBuilder ${expression("i")}\""
                            formattedMessage = "${withLogLevel.levelName} messageBuilder 42"
                            hasMarker = withMarker
                            hasThrowable = withThrowable
                          }
                        }
                      }
                      test {
                        skip = !withMarker // skip variants without marker -- those are handled in
                        // non-deprecated API tests
                        code {
                          useClass = withClass
                          useThrowable = withThrowable
                          useMarker = withMarker
                          initCode = "val i = 42"
                          logStatement =
                            LogStatement(
                              funName = withLogLevel.levelName,
                              arguments = listOf(MARKER_PLACEHOLDER, THROWABLE_PLACEHOLDER),
                              lastArgumentLambda =
                                """ "${withLogLevel.levelName} messageBuilder ${expression("i")} ${expression("helper()")}" """,
                            )
                          extraMethodCode = """fun helper() = "Hello!""""
                        }
                        expect {
                          loggedEvent {
                            level = withLogLevel
                            message =
                              "\"${withLogLevel.levelName} messageBuilder ${expression("i")} ${expression("helper()")}\""
                            formattedMessage = "${withLogLevel.levelName} messageBuilder 42 Hello!"
                            hasMarker = withMarker
                            hasThrowable = withThrowable
                          }
                        }
                      }
                      test {
                        code {
                          useClass = withClass
                          useThrowable = withThrowable
                          useMarker = withMarker
                          logStatement =
                            LogStatement(
                              funName = withLogLevel.levelName,
                              arguments =
                                listOf(
                                  MARKER_PLACEHOLDER,
                                  """ "${withLogLevel.levelName} message {}" """.trim(),
                                  THROWABLE_PLACEHOLDER,
                                ),
                            )
                        }
                        expect {
                          loggedEvent {
                            level = withLogLevel
                            message = "\"${withLogLevel.levelName} message {}\""
                            formattedMessage = "${withLogLevel.levelName} message {}"
                            hasMarker = withMarker
                            hasThrowable = withThrowable
                          }
                        }
                      }
                      collection {
                        name = "deprecated API not implemented by ${KLogger::class.simpleName}"
                        featureFlagExpectationAdjuster {
                          featureFlags(
                            FeatureFlag.DISABLE_ALL,
                            FeatureFlag.DISABLE_TRANSFORMING_NOT_IMPLEMENTED_API,
                            FeatureFlag.DISABLE_TRANSFORMING_DEPRECATED_API,
                          )
                          adjuster {
                            loggedEvent(null)
                            thrownExceptionToString =
                              "kotlin.NotImplementedError: An operation is not implemented."
                          }
                        }
                        test {
                          code {
                            useClass = withClass
                            useThrowable = withThrowable
                            useMarker = withMarker
                            initCode = "val arg = 42"
                            logStatement =
                              LogStatement(
                                funName = withLogLevel.levelName,
                                arguments =
                                  listOf(
                                    MARKER_PLACEHOLDER,
                                    """ "${withLogLevel.levelName} message {}" """.trim(),
                                    "arg",
                                    THROWABLE_PLACEHOLDER,
                                  ),
                              )
                          }
                          expect {
                            loggedEvent {
                              level = withLogLevel
                              message = "\"${withLogLevel.levelName} message {}\""
                              formattedMessage = "${withLogLevel.levelName} message 42"
                              hasMarker = withMarker
                              hasThrowable = withThrowable
                            }
                          }
                        }
                        test {
                          code {
                            useClass = withClass
                            useThrowable = withThrowable
                            useMarker = withMarker
                            initCode = "val arg = 42"
                            logStatement =
                              LogStatement(
                                funName = withLogLevel.levelName,
                                arguments =
                                  listOf(
                                    MARKER_PLACEHOLDER,
                                    """ "${withLogLevel.levelName} message {} " + "" + "{}" + "{}" + " abc" + " {}" """
                                      .trim(),
                                    "arg",
                                    "helper()",
                                    THROWABLE_PLACEHOLDER,
                                  ),
                              )
                            extraMethodCode = """fun helper() = "Hello!""""
                          }
                          expect {
                            loggedEvent {
                              level = withLogLevel
                              message =
                                """"${withLogLevel.levelName} message {} " + "" + "{}" + "{}" + " abc" + " {}""""
                              formattedMessage =
                                if (withThrowable)
                                  "${withLogLevel.levelName} message 42 Hello!java.lang.Exception: expected! abc {}"
                                else "${withLogLevel.levelName} message 42 Hello!{} abc {}"
                              hasMarker = withMarker
                              hasThrowable = false
                            }
                          }
                        }
                        test {
                          code {
                            useClass = withClass
                            useThrowable = withThrowable
                            useMarker = withMarker
                            initCode = "val a = 1; val b = 2"
                            logStatement =
                              LogStatement(
                                funName = withLogLevel.levelName,
                                arguments =
                                  listOf(
                                    MARKER_PLACEHOLDER,
                                    """ "${withLogLevel.levelName} message {}a" + " {}b" + " {}ab" + " ab" """
                                      .trim(),
                                    "a",
                                    "b",
                                    "ab()",
                                    THROWABLE_PLACEHOLDER,
                                  ),
                              )
                            extraMethodCode = """fun ab() = 12"""
                          }
                          expect {
                            loggedEvent {
                              level = withLogLevel
                              message =
                                """"${withLogLevel.levelName} message {}a" + " {}b" + " {}ab" + " ab""""
                              formattedMessage = "${withLogLevel.levelName} message 1a 2b 12ab ab"
                              hasMarker = withMarker
                              hasThrowable = withThrowable
                            }
                          }
                        }
                      }
                    }
                    test {
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        logStatement =
                          LogStatement(
                            funName = "at${withLogLevel.levelName.capitalizeAsciiOnly()}",
                            arguments = listOf(MARKER_PLACEHOLDER),
                            lastArgumentLambda =
                              """ message="${withLogLevel.levelName} eventBuilder"; cause=$THROWABLE_PLACEHOLDER """,
                          )
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message = "\"${withLogLevel.levelName} eventBuilder\""
                          formattedMessage = "${withLogLevel.levelName} eventBuilder"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    test {
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        initCode = "val i = 42"
                        logStatement =
                          LogStatement(
                            funName = "at${withLogLevel.levelName.capitalizeAsciiOnly()}",
                            arguments = listOf(MARKER_PLACEHOLDER),
                            lastArgumentLambda =
                              """ message="${withLogLevel.levelName} eventBuilder ${expression("i")}"; cause=$THROWABLE_PLACEHOLDER """,
                          )
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message = "\"${withLogLevel.levelName} eventBuilder ${expression("i")}\""
                          formattedMessage = "${withLogLevel.levelName} eventBuilder 42"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    test {
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        initCode = "val i = 42"
                        logStatement =
                          LogStatement(
                            funName = "at${withLogLevel.levelName.capitalizeAsciiOnly()}",
                            arguments = listOf(MARKER_PLACEHOLDER),
                            lastArgumentLambda =
                              """ message="${withLogLevel.levelName} eventBuilder ${expression("i")} ${
                            expression(
                              "helper()"
                            )
                          }"; cause=$THROWABLE_PLACEHOLDER """,
                          )
                        extraMethodCode = """fun helper() = "Hello!""""
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message =
                            "\"${withLogLevel.levelName} eventBuilder ${expression("i")} ${expression("helper()")}\""
                          formattedMessage = "${withLogLevel.levelName} eventBuilder 42 Hello!"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    test {
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        logStatement =
                          LogStatement(
                            funName = "at",
                            arguments =
                              listOf(
                                "Level.${withLogLevel.levelName.toUpperCaseAsciiOnly()}",
                                MARKER_PLACEHOLDER,
                              ),
                            lastArgumentLambda =
                              """ message="${withLogLevel.levelName} eventBuilder"; cause=$THROWABLE_PLACEHOLDER """,
                          )
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message = "\"${withLogLevel.levelName} eventBuilder\""
                          formattedMessage = "${withLogLevel.levelName} eventBuilder"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    test {
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        initCode = "val i = 42"
                        logStatement =
                          LogStatement(
                            funName = "at",
                            arguments =
                              listOf(
                                "Level.${withLogLevel.levelName.toUpperCaseAsciiOnly()}",
                                MARKER_PLACEHOLDER,
                              ),
                            lastArgumentLambda =
                              """ message="${withLogLevel.levelName} eventBuilder ${
                            expression(
                              "i"
                            )
                          }"; cause=$THROWABLE_PLACEHOLDER """,
                          )
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message = "\"${withLogLevel.levelName} eventBuilder ${expression("i")}\""
                          formattedMessage = "${withLogLevel.levelName} eventBuilder 42"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                    test {
                      code {
                        useClass = withClass
                        useThrowable = withThrowable
                        useMarker = withMarker
                        initCode = "val i = 42"
                        logStatement =
                          LogStatement(
                            funName = "at",
                            arguments =
                              listOf(
                                "Level.${withLogLevel.levelName.toUpperCaseAsciiOnly()}",
                                MARKER_PLACEHOLDER,
                              ),
                            lastArgumentLambda =
                              """ message="${withLogLevel.levelName} eventBuilder ${
                            expression(
                              "i"
                            )
                          } ${expression("helper()")}"; cause=$THROWABLE_PLACEHOLDER """,
                          )
                        extraMethodCode = """fun helper() = "Hello!""""
                      }
                      expect {
                        loggedEvent {
                          level = withLogLevel
                          message =
                            "\"${withLogLevel.levelName} eventBuilder ${expression("i")} ${expression("helper()")}\""
                          formattedMessage = "${withLogLevel.levelName} eventBuilder 42 Hello!"
                          hasMarker = withMarker
                          hasThrowable = withThrowable
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private fun expression(variableName: String) =
    if (variableName.matches(Regex("[A-Za-z0-9_]*"))) "${'$'}$variableName"
    else "${'$'}{$variableName}"

  @TestFactory
  fun `generate code, compile it, run it and assert resulting log event(s)`(): List<DynamicNode> {
    var testCounter = 0
    val preparedTests = testDefinitions.map { it.prepare(++testCounter) }
    val featureFlagCompilationResults = compileTests(FeatureFlag.entries, preparedTests)

    File("code-samples-from-tests.md").bufferedWriter().use { writer ->
      writer.write("# Code samples from tests\n\n")
      writer.write(
        """
        |All the test cases that the plugin is tested against with before+after code snippets and how different feature
        |flags affect the transformation. Organized on the top level by feature flags and then by groups of test cases.
        |"""
          .trimMargin()
      )
      writer.write("\n")
      featureFlagCompilationResults.forEach { featureFlagCompilationResult ->
        featureFlagCompilationResult.compiledTests
          .toMarkDownDocument("featureFlag=${featureFlagCompilationResult.featureFlag.name}")
          .let { writer.write(it) }
      }
    }

    return featureFlagCompilationResults.map {
      DynamicContainer.dynamicContainer(
        "featureFlag=${it.featureFlag.name}",
        it.compiledTests
          .toDynamicTests {
            DynamicTest.dynamicTest(preparedTest.testCode.testName) {
              Assertions.assertAll(execute().assertResults())
            }
          }
          .children,
      )
    }
  }
}
