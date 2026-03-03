## Example

Add a wrapper to a code block so that the SLF4J [MDC](https://logback.qos.ch/manual/mdc.html) uses a specific context
for that block of suspending code with an option to avoid restoring the context after the code block has executed.

```kotlin
withLoggingContextAsync("userId" to "A_USER_ID") {
    // The MDC context will contain the mapping of "userId"=>"A_USER_ID"
    // during this log statement.
    logger.info { "..." }
}
// The block will restore The MDC context so that it no longer contains
// the mapping of "userId"=>"A_USER_ID"
withLoggingContextAsync("userId" to "ANOTHER_USER_ID", restorePrevious = false) {
    logger.info { "..." }
}
// The MDC context will retain the mapping of "userId"=>"ANOTHER_USER_ID",
// as the previous context restoration was disabled.
```

## Coroutine-Safe wrappers

If you prefer to leverage coroutine context instead of thread local storage, you can use the `withCoroutineLoggingContext` wrapper.
This will NOT inherit the current MDC set prior, but will carry existing coroutine context within nested declarations, restoring previous context
at the logical coroutine level rather than thread level.

```kotlin
MDC.put("foo", "bar")
withCoroutineLoggingContext("userId" to "A_USER_ID") {
    // The MDC context will contain the mapping of "userId"=>"A_USER_ID"
    // during this log statement, but NOT the mapping of "foo"=>"bar" as the coroutine context does not inherit the thread local MDC.
    logger.info { "..." }
}
// after the block, the MDC will now only contain "foo"=>"bar"
```
