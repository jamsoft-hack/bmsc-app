package bo.com.bmsc.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import bo.com.bmsc.app.theme.color.BarikThemeColor
import bo.com.bmsc.app.theme.color.LightColors
import bo.com.bmsc.core.extension.blendColor

object AppColors {
  val Primary = Color( 0xFFE3312C)
  val DarkPrimary = Primary.blendColor(Color.Black.copy(0.5f))

  val Danger = Color(0xFFE3312C)
  val SecondaryGrey = Color(0xFF939B9C)
  val WarningDark = Color(0xFFAB8400)

  val Neutral = Color(0xFF242B38)

  val reactive: BarikThemeColor
    @Composable
    get() = LightColors
}
