package io.github.oshai.kotlinlogging.logback.internal

import io.github.oshai.kotlinlogging.KLogger
import org.junit.jupiter.api.DynamicNode

class KLoggerTestScenario(val name: String) {
  var given: GivenLogStatement? = null
  var whenCall: KLogger.(GivenLogStatement) -> Unit = {}
  var expectedCall: ExpectedLogEvent.(GivenLogStatement) -> Unit = {}

  fun givenStatement(block: GivenLogStatement.() -> Unit) {
    this.given = GivenLogStatement().apply(block)
  }

  fun whenCalling(block: KLogger.(GivenLogStatement) -> Unit) {
    this.whenCall = block
  }

  fun thenExpect(block: ExpectedLogEvent.(GivenLogStatement) -> Unit) {
    this.expectedCall = block
  }

  fun callWithLogger(logger: KLogger) {
    whenCall(logger, given!!)
  }

  fun buildExpected(): ExpectedLogEvent {
    return ExpectedLogEvent().apply { expectedCall(given!!) }
  }
}

class KLoggerTestScenarioList(val name: String) {
  val branches = mutableListOf<KLoggerTestScenarioList>()
  val leaves = mutableListOf<KLoggerTestScenario>()

  fun test(name: String, block: KLoggerTestScenario.() -> Unit) {
    leaves.add(KLoggerTestScenario(name).apply(block))
  }

  fun tests(name: String, block: KLoggerTestScenarioList.() -> Unit) {
    branches.add(KLoggerTestScenarioList(name).apply(block))
  }

  fun toDynamicTests(converter: (KLoggerTestScenario) -> DynamicNode): List<DynamicNode> {
    return branches.flatMap { it.toDynamicTests(converter) } + leaves.map(converter)
  }
}

fun tests(name: String, block: KLoggerTestScenarioList.() -> Unit): KLoggerTestScenarioList {
  return KLoggerTestScenarioList(name).apply(block)
}

fun expectedEvent(block: ExpectedLogEvent.() -> Unit): ExpectedLogEvent {
  return ExpectedLogEvent().apply(block)
}

class GivenLogStatement {
  var level: String = ""
  var marker: String = ""
  var messagePrefix: String = ""
  var message: String = ""
  var arg1: String = ""
  var arg2: String = ""
  var arguments: Array<Any> = emptyArray()
}

class ExpectedLogEvent {
  var level: String = ""
  var name: String = ""
  var message: String = ""
  var arg1: String = ""
  var arg2: String = ""
  var arguments: List<Any> = emptyList()
  var marker: String = ""
}
