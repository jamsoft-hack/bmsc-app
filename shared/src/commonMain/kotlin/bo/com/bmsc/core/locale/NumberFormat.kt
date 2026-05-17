package bo.com.bmsc.core.locale

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.localeFormat(locale: AppLang, decimals: Int = 2): String {
  var result = ""

  val extendNumber = (10.0).pow(decimals)
  var rounded = (abs(x= this) * extendNumber).roundToInt().toString()

  if (rounded.length < decimals + 1) {
    rounded = "0".repeat(n = decimals + 1 - rounded.length) + rounded
  }

  val limit: Int = rounded.length - 1
  val decimalLimit: Int = rounded.length - decimals

  for (i: Int in limit downTo 0) {
    val num: Char = rounded[i]

    result = if (decimalLimit == i) {
      "${locale.decimalSeparator}$num$result"
    } else if ((rounded.length - decimals - i) % 3 == 0 && i > decimals) {
      "${locale.thousandSeparator}$num$result"
    } else {
      num + result
    }
  }

  return if (this < 0) "-$result" else result
}

fun Float.localeFormat(): String {
  val locale = currentLanguage()
  val decimalStr = toString().split(AppLang.English.decimalSeparator).last()

  val decimals = if (decimalStr == "0") 0 else decimalStr.length

  return this.localeFormat(locale, decimals)
}
