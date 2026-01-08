package io.github.oshai.kotlinlogging.internal

import kotlin.wasm.WasmImport
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

public actual fun getCurrentTime(): Long {
  @OptIn(UnsafeWasmMemoryApi::class)
  withScopedMemoryAllocator { allocator ->
    val timestampPtr = allocator.allocate(8) // 64-bit integer
    val ret =
      clock_time_get(
        1, // CLOCKID_REALTIME
        1000000, // precision: 1ms (in ns)
        timestampPtr.address.toInt(),
      )
    if (ret != 0) return 0L
    return (timestampPtr.loadLong() / 1_000_000) // ns to ms
  }
}

@WasmImport("wasi_snapshot_preview1", "clock_time_get")
private external fun clock_time_get(clockId: Int, precision: Long, resultPtr: Int): Int
