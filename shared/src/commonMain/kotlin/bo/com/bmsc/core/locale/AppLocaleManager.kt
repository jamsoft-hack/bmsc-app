package bo.com.bmsc.core.locale

import androidx.compose.runtime.Composable

interface AppLocaleManager {
  fun getLocale(): AppLang
  fun changeLanguage(languageCode: String)
}

fun String?.toAppLang(): AppLang = when (this) {
  "es" -> AppLang.Spanish
  "eu" -> AppLang.Euskera
  else -> AppLang.English
}

expect fun currentLanguage(): AppLang

expect fun deviceCurrentLanguage(): AppLang

@Composable
expect fun rememberAppLocale(): AppLocaleManager
