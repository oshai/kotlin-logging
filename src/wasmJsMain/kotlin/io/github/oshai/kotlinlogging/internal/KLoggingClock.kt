package io.github.oshai.kotlinlogging.internal

public actual fun getCurrentTime(): Long = Date.now().toLong()

private external class Date {
  companion object {
    fun now(): Double
  }
}
