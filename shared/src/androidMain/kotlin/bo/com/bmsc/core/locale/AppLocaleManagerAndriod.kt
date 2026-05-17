package bo.com.bmsc.core.locale

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.core.os.LocaleListCompat

class AndroidAppLocaleManager(
  private val context: Context,
) : AppLocaleManager {

  override fun changeLanguage(languageCode: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // >= version 13
      context.getSystemService(LocaleManager::class.java).applicationLocales =
        LocaleList.forLanguageTags(languageCode)
    } else {
      AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }
  }

  override fun getLocale(): AppLang {
    var locale: String? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      val localManager = context.getSystemService(LocaleManager::class.java)

      localManager?.applicationLocales?.let {
        if (!it.isEmpty) {
          locale = it[0]?.toLanguageTag()?.split("-")?.firstOrNull()
        }
      }
    } else {
      locale = AppCompatDelegate.getApplicationLocales()
        .toLanguageTags().split("-")
        .firstOrNull()
    }

    if (locale == null) {
      locale = Locale.current.language
    }

    return locale.toAppLang()
  }
}

actual fun currentLanguage(): AppLang {
//  val localManager = AndroidAppLocaleManager(MainActivity.instance)

//  return localManager.getLocale()
  return AppLang.Spanish
}

actual fun deviceCurrentLanguage(): AppLang {
  return java.util.Locale.getDefault().language.toAppLang()
}

@Composable
actual fun rememberAppLocale(): AppLocaleManager {
  val context = LocalContext.current
  val localManager = AndroidAppLocaleManager(context)

  return remember(localManager) {
    localManager
  }
}
