package bo.com.bmsc.app.enumerated

import androidx.compose.ui.graphics.vector.ImageVector
//import barik.composeapp.generated.resources.Res
//import barik.composeapp.generated.resources.theme_dark
//import barik.composeapp.generated.resources.theme_high_contrast
//import barik.composeapp.generated.resources.theme_light
//import barik.composeapp.generated.resources.theme_system

enum class ThemeModeEnum(
) {
  SYSTEM(),
  LIGHT(),
  DARK(),
  HIGH_CONTRAST();
  companion object {
    fun fromName(name: String?) = entries.firstOrNull { it.name == name }
  }
}

