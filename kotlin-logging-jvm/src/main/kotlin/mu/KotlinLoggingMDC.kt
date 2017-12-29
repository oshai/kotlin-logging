package mu

import org.slf4j.MDC

inline fun <T> withLoggingContext(pair: Pair<String, String>, body: () -> T): T =
    MDC.putCloseable(pair.first, pair.second).use { body() }

inline fun <T> withLoggingContext(vararg pair: Pair<String, String>, body: () -> T): T {
  try {
    pair.forEach { MDC.put(it.first, it.second) }
    return body()
  } finally {
    pair.forEach { MDC.remove(it.first) }
  }
}

inline fun <T> withLoggingContext(map: Map<String, String>, body: () -> T): T {
  try {
    map.forEach { MDC.put(it.key, it.value) }
    return body()
  } finally {
    map.forEach { MDC.remove(it.key) }
  }
}
