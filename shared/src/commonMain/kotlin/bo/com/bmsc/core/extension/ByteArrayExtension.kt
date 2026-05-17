package bo.com.bmsc.core.extension

fun ByteArray.toHexString(): String {
  val hexChars = "0123456789abcdef"
  val result = StringBuilder(this.size * 2)
  for (byte in this) {
    val i = byte.toInt()
    result.append(hexChars[(i shr 4) and 0x0F])
    result.append(hexChars[i and 0x0F])
  }
  return result.toString()
}