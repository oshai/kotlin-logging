package io.github.oshai

public fun interface KLoggerDecorator {
    public fun decorate(actualCall: () -> Unit)
}


internal object DoNothingDecorator : KLoggerDecorator {
  override fun decorate(actualCall: () -> Unit) = actualCall()
}
