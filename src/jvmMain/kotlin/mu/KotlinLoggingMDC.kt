package mu

import org.slf4j.MDC

/**
 * Use a pair in MDC context. Example:
 * ```
 * withLoggingContext("userId" to userId) {
 *   doSomething()
 * }
 * ```
 * ```
 * withLoggingContext("userId" to userId, restorePrevious = false) {
 *   doSomething()
 * }
 * ```
 */
public inline fun <T> withLoggingContext(
    pair: Pair<String, String?>,
    restorePrevious: Boolean = true,
    body: () -> T
): T =
    if (pair.second == null) {
        body()
    } else if (!restorePrevious) {
        MDC.putCloseable(pair.first, pair.second).use { body() }
    } else {
        val previousValue = MDC.get(pair.first)
        try {
            MDC.putCloseable(pair.first, pair.second).use { body() }
        } finally {
            if (previousValue != null) MDC.put(pair.first, previousValue)
        }
    }

/**
 * Use a vary number of pairs in MDC context. Example:
 * ```
 * withLoggingContext("userId" to userId) {
 *   doSomething()
 * }
 * ```
 */
public inline fun <T> withLoggingContext(
    vararg pair: Pair<String, String?>,
    restorePrevious: Boolean = true,
    body: () -> T
): T {
    val pairForMDC = pair.filter { it.second != null }
    val cleanupCallbacks = pairForMDC.map { (mdcKey, _) ->
        val mdcValue = MDC.get(mdcKey)
        if (restorePrevious && mdcValue != null) {
            { MDC.put(mdcKey, mdcValue) }
        } else {
            { MDC.remove(mdcKey) }
        }
    }

    try {
        pairForMDC.forEach { MDC.put(it.first, it.second) }
        return body()
    } finally {
        cleanupCallbacks.forEach { it.invoke() }
    }
}

/**
 * Use a map in MDC context. Example:
 * ```
 * withLoggingContext(mapOf("userId" to userId)) {
 *   doSomething()
 * }
 * ```
 * ```
 * withLoggingContext(mapOf("userId" to userId), restorePrevious = true) {
 *   doSomething()
 * }
 * ```
 */
public inline fun <T> withLoggingContext(
    map: Map<String, String?>,
    restorePrevious: Boolean = true,
    body: () -> T
): T {
    val cleanupCallbacks = map.map {
        val mdcValue = MDC.get(it.key)
        if (restorePrevious && mdcValue != null) {
            { MDC.put(it.key, mdcValue) }
        } else {
            { MDC.remove(it.key) }
        }
    }

    try {
        map.forEach {
            if (it.value != null) {
                MDC.put(it.key, it.value)
            }
        }
        return body()
    } finally {
        cleanupCallbacks.forEach { it.invoke() }
    }
}
