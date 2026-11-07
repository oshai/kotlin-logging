package io.github.oshai.kotlinlogging.internal

import kotlin.js.Date

public actual fun getCurrentTime(): Long = Date.now().toLong()
