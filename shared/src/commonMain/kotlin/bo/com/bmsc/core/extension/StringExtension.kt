package bo.com.bmsc.core.extension

fun String.capitalize (): String {
  return this[0].uppercase() + this.slice(indices = 1 until this.length)
}

// Only use when we trying to convert from HexString
fun String.toByteArray(): ByteArray {
  val formatted = this.replace(oldValue = "-", newValue = "")

  val bytes = formatted.chunked(size = 2)
    .map { it.toInt(radix = 16).toByte() }
    .toByteArray()

  return bytes
}
