package mu

import org.slf4j.MDC

/**
 * Use a pair in MDC context. Example:
 * ```
 * withLoggingContext("userId" to userId) {
 *   doSomething()
 * }
 * ```
 */
public inline fun <T> withLoggingContext(pair: Pair<String, Any?>, body: () -> T): T =
    if (pair.second != null) {
        MDC.putCloseable(pair.first, pair.second.toString()).use { body() }
    } else {
        body()
    }

/**
 * Use a vary number of pairs in MDC context. Example:
 * ```
 * withLoggingContext("userId" to userId) {
 *   doSomething()
 * }
 * ```
 */
public inline fun <T> withLoggingContext(vararg pair: Pair<String, Any?>, body: () -> T): T {
    try {
        pair.filter { it.second != null }.forEach { MDC.put(it.first, it.second.toString()) }
        return body()
    } finally {
        pair.filter { it.second != null }.forEach { MDC.remove(it.first) }
    }
}

/**
 * Use a map in MDC context. Example:
 * ```
 * withLoggingContext(mapOf("userId" to userId)) {
 *   doSomething()
 * }
 * ```
 */
public inline fun <T> withLoggingContext(map: Map<String, String>, body: () -> T): T {
    try {
        map.forEach { MDC.put(it.key, it.value) }
        return body()
    } finally {
        map.forEach { MDC.remove(it.key) }
    }
}
