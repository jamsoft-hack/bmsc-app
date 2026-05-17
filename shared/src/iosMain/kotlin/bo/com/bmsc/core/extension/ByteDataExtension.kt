package bo.com.bmsc.core.extension

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
  val mBytes: NSData = this
  val dataLength = mBytes.length.toInt()
  try {
    if (dataLength == 0) {
      return ByteArray(0)
    }

    val result = ByteArray(dataLength).apply {
      usePinned { pinned ->
        mBytes.bytes?.let { dataBytes ->
          memcpy(pinned.addressOf(0), dataBytes, dataLength.convert())
        } ?: throw IllegalArgumentException("Null bytes exception")
      }
    }
    return result
  } catch (e: Exception) {
    println("ERROR on bytes: $e")
    return ByteArray(0)
  }
}
