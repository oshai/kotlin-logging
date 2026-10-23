package io.github.oshai.kotlinlogging.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.*
import org.slf4j.MDC

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
public suspend fun <T> withLoggingContextAsync(
  pair: Pair<String, String?>,
  restorePrevious: Boolean = true,
  body: suspend () -> T,
): T =
  withMdcContext(
    listOf(pair),
    restorePrevious = restorePrevious,
    removeNullValuedKeys = false,
    body = body,
  )

/**
 * Use a varying number of pairs in an asynchronous MDC context. Example:
 * ```
 * withLoggingContextAsync("userId" to userId) {
 *   doSomething()
 * }
 * ```
 */
public suspend fun <T> withLoggingContextAsync(
  vararg pair: Pair<String, String?>,
  restorePrevious: Boolean = true,
  body: suspend () -> T,
): T =
  withMdcContext(
    pair.asList(),
    restorePrevious = restorePrevious,
    removeNullValuedKeys = false,
    body = body,
  )

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
public suspend fun <T> withLoggingContextAsync(
  map: Map<String, String?>,
  restorePrevious: Boolean = true,
  body: suspend () -> T,
): T =
  withMdcContext(
    map.toList(),
    restorePrevious = restorePrevious,
    // withLoggingContext(map, restorePrevious = false) removes keys whose value is null as well
    removeNullValuedKeys = true,
    body = body,
  )

/**
 * Puts the [entries] with a value into an [MDCContext] only. Writing them to the MDC of the current
 * thread first would leave them there when [body] suspends and resumes on another thread.
 */
private suspend fun <T> withMdcContext(
  entries: List<Pair<String, String?>>,
  restorePrevious: Boolean,
  removeNullValuedKeys: Boolean,
  body: suspend () -> T,
): T {
  val added = entries.mapNotNull { (key, value) -> value?.let { key to it } }.toMap()
  try {
    return withContext(MDCContext(MDC.getCopyOfContextMap().orEmpty() + added)) { body() }
  } finally {
    if (!restorePrevious) {
      val removed = if (removeNullValuedKeys) entries.map { it.first } else added.keys
      removed.forEach(MDC::remove)
    }
  }
}
