package io.github.oshai.kotlinlogging.coroutines

import io.github.oshai.kotlinlogging.*
import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.*

/**
 * Use a pair in an asynchronous MDC context. Example:
 * ```
 * withLoggingContextAsync("userId" to userId) {
 *   doSomething()
 * }
 * ```
 * ```
 * withLoggingContextAsync("userId" to userId, restorePrevious = false) {
 *   doSomething()
 * }
 * ```
 */
public suspend inline fun <T> withLoggingContextAsync(
  pair: Pair<String, String?>,
  restorePrevious: Boolean = true,
  crossinline body: suspend () -> T,
): T =
  withLoggingContext(pair, restorePrevious = restorePrevious) {
    withContext(MDCContext()) { body() }
  }

/**
 * Use a pair inheriting coroutine context but not current MDC Context. Example:
 * ```
 * withLoggingContextAsync("userId" to userId) {
 *   doSomething()
 * }
 * ```
 */
public suspend inline fun <T> withCoroutineLoggingContext(
  pair: Pair<String, String?>,
  crossinline body: suspend () -> T,
): T = withCoroutineLoggingContext(mapOf(pair), body)

/**
 * Use a varying number of pairs in an asynchronous MDC context. Example:
 * ```
 * withLoggingContextAsync("userId" to userId) {
 *   doSomething()
 * }
 * ```
 */
public suspend inline fun <T> withLoggingContextAsync(
  vararg pair: Pair<String, String?>,
  restorePrevious: Boolean = true,
  crossinline body: suspend () -> T,
): T =
  withLoggingContext(*pair, restorePrevious = restorePrevious) {
    withContext(MDCContext()) { body() }
  }

/**
 * Use a varying number of pairs inheriting coroutine context but not current MDC Context. Example:
 * ```
 * withCoroutineLoggingContext("userId" to userId) {
 *   doSomething()
 * }
 * ```
 */
public suspend inline fun <T> withCoroutineLoggingContext(
  vararg pair: Pair<String, String?>,
  crossinline body: suspend () -> T,
): T = withCoroutineLoggingContext(pair.toMap(), body)

/**
 * Use a map in an asynchronous MDC context. Example:
 * ```
 * withLoggingContextAsync(mapOf("userId" to userId)) {
 *   doSomething()
 * }
 * ```
 * ```
 * withLoggingContextAsync(mapOf("userId" to userId), restorePrevious = true) {
 *   doSomething()
 * }
 * ```
 */
public suspend inline fun <T> withLoggingContextAsync(
  map: Map<String, String?>,
  restorePrevious: Boolean = true,
  crossinline body: suspend () -> T,
): T =
  withLoggingContext(map, restorePrevious = restorePrevious) {
    withContext(MDCContext()) { body() }
  }

/**
 * Use a map inheriting coroutine context but not current MDC Context. Example:
 * ```
 * withCoroutineLoggingContext(mapOf("userId" to userId)) {
 *   doSomething()
 * }
 * ```
 */
public suspend inline fun <T> withCoroutineLoggingContext(
  map: Map<String, String?>,
  crossinline body: suspend () -> T,
): T {
  val parent = currentCoroutineContext()[MDCContext]?.contextMap ?: emptyMap()
  val merged = (parent + map.filterValues { it != null }).mapValues { it.value!! }

  return withContext(MDCContext(merged)) { body() }
}
