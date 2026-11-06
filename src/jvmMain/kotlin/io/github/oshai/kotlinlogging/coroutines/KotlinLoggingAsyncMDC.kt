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
 */
public suspend fun <T> withLoggingContextAsync(
  pair: Pair<String, String?>,
  body: suspend () -> T,
): T = withMdcContext(listOf(pair), body)

/** Use a pair in an asynchronous MDC context. */
@Deprecated(
  message =
    "restorePrevious is deprecated and ignored in coroutines; MDC propagation is scoped to the coroutine context.",
  replaceWith = ReplaceWith("withLoggingContextAsync(pair, body = body)"),
)
public suspend fun <T> withLoggingContextAsync(
  pair: Pair<String, String?>,
  @Suppress("UNUSED_PARAMETER") restorePrevious: Boolean,
  body: suspend () -> T,
): T = withLoggingContextAsync(pair, body)

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
  body: suspend () -> T,
): T = withMdcContext(pair.asList(), body)

/** Use a varying number of pairs in an asynchronous MDC context. */
@Deprecated(
  message =
    "restorePrevious is deprecated and ignored in coroutines; MDC propagation is scoped to the coroutine context.",
  replaceWith = ReplaceWith("withLoggingContextAsync(*pair, body = body)"),
)
public suspend fun <T> withLoggingContextAsync(
  vararg pair: Pair<String, String?>,
  @Suppress("UNUSED_PARAMETER") restorePrevious: Boolean,
  body: suspend () -> T,
): T = withLoggingContextAsync(*pair, body = body)

/**
 * Use a map in an asynchronous MDC context. Example:
 * ```
 * withLoggingContextAsync(mapOf("userId" to userId)) {
 *   doSomething()
 * }
 * ```
 */
public suspend fun <T> withLoggingContextAsync(
  map: Map<String, String?>,
  body: suspend () -> T,
): T = withMdcContext(map.toList(), body)

/** Use a map in an asynchronous MDC context. */
@Deprecated(
  message =
    "restorePrevious is deprecated and ignored in coroutines; MDC propagation is scoped to the coroutine context.",
  replaceWith = ReplaceWith("withLoggingContextAsync(map, body = body)"),
)
public suspend fun <T> withLoggingContextAsync(
  map: Map<String, String?>,
  @Suppress("UNUSED_PARAMETER") restorePrevious: Boolean,
  body: suspend () -> T,
): T = withLoggingContextAsync(map, body)

/**
 * Puts the [entries] with a value into an [MDCContext] only. Writing them to the MDC of the current
 * thread first would leave them there when [body] suspends and resumes on another thread.
 */
private suspend fun <T> withMdcContext(
  entries: List<Pair<String, String?>>,
  body: suspend () -> T,
): T {
  val added = entries.mapNotNull { (key, value) -> value?.let { key to it } }.toMap()
  return withContext(MDCContext(MDC.getCopyOfContextMap().orEmpty() + added)) { body() }
}
