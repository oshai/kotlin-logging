package mu.internal

import mu.LoggingCallback
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.*
import org.slf4j.Logger
import org.slf4j.Marker
import java.util.stream.Stream
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame

internal class JvmDelegatingLoggerTest {
    /**
     * Test verifies the proper case of the syntax - receive callback and use it.
     */
    @ParameterizedTest
    @MethodSource("createTestCases")
    fun delegatingCallbackShouldCallUnderlyingLoggerMethods(testCase: TestCase) {
        // given
        val underlyingLogger = mock<Logger> {
            on { testCase.isEnabledGetter(it) } doReturn true
            on { testCase.expectedUnderlyingMethodCall(it) } doAnswer { null }
        }
        val delegatingLogger = JvmDelegatingLogger(underlyingLogger)
        val callback = testCase.callbackGetter(delegatingLogger)

        // when
        assertNotNull(callback)

        testCase.callbackCallUnderTest(callback)

        // then
        verify(underlyingLogger) {
            1.times { testCase.isEnabledGetter(this) }
            1.times { testCase.expectedUnderlyingMethodCall(this) }
        }
    }

    @ParameterizedTest
    @MethodSource("createTestCases")
    fun delegatingCallbackShouldBeTheSameForTheSameCalls(testCase: TestCase) {
        // given
        val underlyingLogger = mock<Logger> {
            on { testCase.isEnabledGetter(it) } doReturn true
        }
        val delegatingLogger = JvmDelegatingLogger(underlyingLogger)

        // when
        val callback1 = testCase.callbackGetter(delegatingLogger)
        val callback2 = testCase.callbackGetter(delegatingLogger)
        assertSame(callback1, callback2)

        // then
        verify(underlyingLogger) {
            2.times { testCase.isEnabledGetter(this) }
            0.times { testCase.expectedUnderlyingMethodCall(this) }
        }
    }

    /**
     * Second important case of the callbacks - return null if logger is disabled.
     *
     * In theory, we must have test which ensures we don't create any objects in memory, however it is hard to do this.
     */
    @ParameterizedTest
    @MethodSource("createTestCases")
    fun delegatingCallbackShouldReturnNullIfLoggerIsDisabled(testCase: TestCase) {
        // given
        val underlyingLogger = mock<Logger> {
            on { testCase.isEnabledGetter(it) } doReturn false
        }
        val delegatingLogger = JvmDelegatingLogger(underlyingLogger)
        val callback = testCase.callbackGetter(delegatingLogger)

        // when
        assertNull(callback)

        // then
        verify(underlyingLogger) {
            1.times { testCase.isEnabledGetter(this) }
            0.times { testCase.expectedUnderlyingMethodCall(this) }
        }
    }

    /**
     * This is tricky scenario: we enable logger and then disable it.
     *
     * So we verify that we stop returning callback in that case.
     */
    @ParameterizedTest
    @MethodSource("createTestCases")
    fun delegatingCallbackShouldReturnNullIfLoggerWasDisabledInConfiguration(testCase: TestCase) {
        // given
        var isEnabled = true
        val underlyingLogger = mock<Logger> {
            this.on { testCase.isEnabledGetter(it) } doAnswer { isEnabled }
            on { testCase.expectedUnderlyingMethodCall(it) } doAnswer { null }
        }
        val delegatingLogger = JvmDelegatingLogger(underlyingLogger)

        // when
        // simulate logger was enabled initially ...
        val callback1 = testCase.callbackGetter(delegatingLogger)
        assertNotNull(callback1)
        testCase.callbackCallUnderTest(callback1)

        // ... and then it was disabled
        isEnabled = false
        val callback2 = testCase.callbackGetter(delegatingLogger)
        assertNull(callback2)

        // then
        verify(underlyingLogger) {
            2.times { testCase.isEnabledGetter(this) }
            1.times { testCase.expectedUnderlyingMethodCall(this) }
        }
    }

    companion object {
        @JvmStatic
        fun createTestCases(): Stream<Arguments> {
            val message = "Test"
            val marker = mock<Marker>()
            val throwable = mock<Throwable>()

            @Suppress("KotlinPlaceholderCountMatchesArgumentCount")
            val testCases = listOf(
                TestCase(
                    { it.error },
                    { it.isErrorEnabled },
                    { it.error(null as Marker?, message, null) },
                    { it.invoke(message) }
                ),
                TestCase(
                    { it.error },
                    { it.isErrorEnabled },
                    { it.error(marker, message, null) },
                    { it.invoke(marker, message) }
                ),
                TestCase(
                    { it.error },
                    { it.isErrorEnabled },
                    { it.error(null as Marker?, message, throwable) },
                    { it.invoke(throwable, message) }
                ),
                TestCase(
                    { it.error },
                    { it.isErrorEnabled },
                    { it.error(marker, message, throwable) },
                    { it.invoke(marker, throwable, message) }
                ),

                TestCase(
                    { it.warn },
                    { it.isWarnEnabled },
                    { it.warn(null as Marker?, message, null) },
                    { it.invoke(message) }
                ),
                TestCase(
                    { it.warn },
                    { it.isWarnEnabled },
                    { it.warn(marker, message, null) },
                    { it.invoke(marker, message) }
                ),
                TestCase(
                    { it.warn },
                    { it.isWarnEnabled },
                    { it.warn(null as Marker?, message, throwable) },
                    { it.invoke(throwable, message) }
                ),
                TestCase(
                    { it.warn },
                    { it.isWarnEnabled },
                    { it.warn(marker, message, throwable) },
                    { it.invoke(marker, throwable, message) }
                ),

                TestCase(
                    { it.info },
                    { it.isInfoEnabled },
                    { it.info(null as Marker?, message, null) },
                    { it.invoke(message) }
                ),
                TestCase(
                    { it.info },
                    { it.isInfoEnabled },
                    { it.info(marker, message, null) },
                    { it.invoke(marker, message) }
                ),
                TestCase(
                    { it.info },
                    { it.isInfoEnabled },
                    { it.info(null as Marker?, message, throwable) },
                    { it.invoke(throwable, message) }
                ),
                TestCase(
                    { it.info },
                    { it.isInfoEnabled },
                    { it.info(marker, message, throwable) },
                    { it.invoke(marker, throwable, message) }
                ),

                TestCase(
                    { it.debug },
                    { it.isDebugEnabled },
                    { it.debug(null as Marker?, message, null) },
                    { it.invoke(message) }
                ),
                TestCase(
                    { it.debug },
                    { it.isDebugEnabled },
                    { it.debug(marker, message, null) },
                    { it.invoke(marker, message) }
                ),
                TestCase(
                    { it.debug },
                    { it.isDebugEnabled },
                    { it.debug(null as Marker?, message, throwable) },
                    { it.invoke(throwable, message) }
                ),
                TestCase(
                    { it.debug },
                    { it.isDebugEnabled },
                    { it.debug(marker, message, throwable) },
                    { it.invoke(marker, throwable, message) }
                ),

                TestCase(
                    { it.trace },
                    { it.isTraceEnabled },
                    { it.trace(null as Marker?, message, null) },
                    { it.invoke(message) }
                ),
                TestCase(
                    { it.trace },
                    { it.isTraceEnabled },
                    { it.trace(marker, message, null) },
                    { it.invoke(marker, message) }
                ),
                TestCase(
                    { it.trace },
                    { it.isTraceEnabled },
                    { it.trace(null as Marker?, message, throwable) },
                    { it.invoke(throwable, message) }
                ),
                TestCase(
                    { it.trace },
                    { it.isTraceEnabled },
                    { it.trace(marker, message, throwable) },
                    { it.invoke(marker, throwable, message) }
                )
            )

            return testCases
                .map { Arguments.of(it) }
                .stream()
        }
    }

    data class TestCase(
        val callbackGetter: (JvmDelegatingLogger) -> LoggingCallback?,
        val isEnabledGetter: (Logger) -> Boolean,
        val expectedUnderlyingMethodCall: (Logger) -> Unit,
        val callbackCallUnderTest: (LoggingCallback) -> Unit
    )
}
