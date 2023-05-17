package io.github.oshai.internal

import io.github.oshai.KLogger
import io.github.oshai.KLoggerDecorator

internal class KLoggerDecorator(
  private val delegate: KLogger,
  private val decorator: KLoggerDecorator
) : KLogger by delegate {

  override fun trace(msg: () -> Any?) {
    decorator.decorate {
      delegate.trace(msg)
    }
  }

  override fun debug(msg: () -> Any?) {
    decorator.decorate {
      delegate.trace(msg)
    }
  }

  override fun info(msg: () -> Any?) {
    decorator.decorate {
      delegate.trace(msg)
    }
  }
}

internal fun KLogger.decorated(decorator: KLoggerDecorator) = KLoggerDecorator(this, decorator)
