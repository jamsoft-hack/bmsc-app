package bo.com.bmsc.core.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

class IosAppLocaleManager : AppLocaleManager {
  private val userDefaults = NSUserDefaults.standardUserDefaults

  override fun getLocale(): AppLang {
    val languageCode = Locale.current.language

    return languageCode.toAppLang()
  }

  override fun changeLanguage(languageCode: String) {
    userDefaults.setObject(listOf(languageCode), forKey = "AppleLanguages")
    userDefaults.setObject(languageCode, forKey = "currentLanguage")

    userDefaults.synchronize()
  }
}

actual fun currentLanguage(): AppLang {
  val manager = bo.com.bmsc.core.locale.IosAppLocaleManager()

  return manager.getLocale()
}

actual fun deviceCurrentLanguage(): AppLang {
  val locale = NSLocale.currentLocale

  return locale.languageCode.toAppLang()
}

@Composable
actual fun rememberAppLocale(): AppLocaleManager {
  val manager = bo.com.bmsc.core.locale.IosAppLocaleManager()

  return remember(manager) {
    manager
  }
}
