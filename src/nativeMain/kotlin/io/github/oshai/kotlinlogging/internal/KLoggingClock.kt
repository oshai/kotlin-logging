package io.github.oshai.kotlinlogging.internal

import kotlin.system.getTimeMillis

@Suppress("DEPRECATION") public actual fun getCurrentTime(): Long = getTimeMillis()
