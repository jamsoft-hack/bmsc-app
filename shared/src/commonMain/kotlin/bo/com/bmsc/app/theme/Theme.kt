package bo.com.bmsc.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import bo.com.bmsc.app.enumerated.ThemeModeEnum
import bo.com.bmsc.app.theme.color.BarikThemeColor
import bo.com.bmsc.app.theme.color.DarkColors
import bo.com.bmsc.app.theme.color.HighContrastColors
import bo.com.bmsc.app.theme.color.LightColors
import bo.com.bmsc.core.locale.AppLang
import bo.com.bmsc.core.locale.rememberAppLocale

val LocalBarikColors = staticCompositionLocalOf<BarikThemeColor> {
  LightColors
}

val LocalAppLocalization = compositionLocalOf { AppLang.Spanish }

@Composable
fun BarikTheme(
  content: @Composable () -> Unit,
) {
  val currentLanguage = rememberAppLocale()
  val state = ThemeViewModel.shared.themeMode.collectAsState()
  val locale = ThemeViewModel.shared.appLocale.collectAsState(initial = currentLanguage.getLocale())

  val colors: BarikThemeColor = when (state.value) {
    ThemeModeEnum.LIGHT -> LightColors
    ThemeModeEnum.DARK -> DarkColors
    ThemeModeEnum.HIGH_CONTRAST -> HighContrastColors
    ThemeModeEnum.SYSTEM -> if (isSystemInDarkTheme()) DarkColors else LightColors
  }

  CompositionLocalProvider(
    LocalBarikColors provides colors,
    LocalAppLocalization provides locale.value,
  ) {
    MaterialTheme(
      typography = appTypography(),
      colorScheme = colors.scheme,
      content = content,
    )
  }
}
