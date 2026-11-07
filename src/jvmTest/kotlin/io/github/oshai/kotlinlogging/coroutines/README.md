## Example

Add a wrapper to a code block so that the SLF4J [MDC](https://logback.qos.ch/manual/mdc.html) uses a specific context
for that block of suspending code. In coroutines, MDC propagation is strictly scoped to the coroutine context block.

```kotlin
withLoggingContextAsync("userId" to "A_USER_ID") {
    // The MDC context will contain the mapping of "userId"=>"A_USER_ID"
    // during this log statement.
    logger.info { "..." }
}
// Suspending or exiting the block automatically restores the previous context.
```
