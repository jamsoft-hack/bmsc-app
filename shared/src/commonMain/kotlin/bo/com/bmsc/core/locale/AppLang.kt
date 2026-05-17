package bo.com.bmsc.core.locale

import bmsc.shared.generated.resources.Res
import org.jetbrains.compose.resources.StringResource

enum class AppLang(
  val code: String,
  val decimalSeparator: Char,
  val thousandSeparator: Char,
  val monthDateFormat: String,
  val humanDateFormat: String,
) {
  Euskera(
    code = "eu",
    decimalSeparator = ',',
    thousandSeparator = '.',
    monthDateFormat = "MMM dd",
    humanDateFormat = "MMM dd'ra', yyyy",
  ),
  Spanish(
    code = "es",
    decimalSeparator = ',',
    thousandSeparator = '.',
    monthDateFormat = "dd MMM.",
    humanDateFormat = "dd MMM. yyyy",
  ),
  English(
    code = "en",
    decimalSeparator = '.',
    thousandSeparator = ',',
    monthDateFormat = "MMM dd",
    humanDateFormat = "MMM dd, yyyy",
  );

  companion object {
    fun fromCode(code: String): AppLang = entries.find { it.code == code } ?: Spanish
  }

  val countryCode get(): String {
    var response = code + "_ES";

    if (code == "en") {
      response = code + "_US"
    }

    return response;
  }

  val resourceCode get(): String {
    return when(this) { // Comes from API: "es_ES" -> es, "es" -> eu, "en" -> en
      Spanish -> "es_ES"
      Euskera -> "es"
      else -> this.code
    }
  }
}
