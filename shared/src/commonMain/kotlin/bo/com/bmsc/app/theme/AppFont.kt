package bo.com.bmsc.app.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.roboto_black
import bmsc.shared.generated.resources.roboto_bold
import bmsc.shared.generated.resources.roboto_light
import bmsc.shared.generated.resources.roboto_medium
import bmsc.shared.generated.resources.roboto_regular
import bmsc.shared.generated.resources.roboto_thin
import org.jetbrains.compose.resources.Font

@Composable
fun appTypography(): Typography {
  val family = FontFamily(
    Font(Res.font.roboto_thin, weight = FontWeight.Thin),
    Font(Res.font.roboto_light, weight = FontWeight.Light),
    Font(Res.font.roboto_regular, weight = FontWeight.Normal),
    Font(Res.font.roboto_medium, weight = FontWeight.Medium),
    Font(Res.font.roboto_bold, weight = FontWeight.Bold),
    Font(Res.font.roboto_black, weight = FontWeight.Black),
  )

  val typography = Typography(
    bodySmall = TextStyle(
      fontFamily = family,
      fontSize = 12.sp,
    ),
    bodyMedium = TextStyle(
      fontFamily = family,
      fontSize = 14.sp,
      lineHeight = 20.sp,
    ),
    bodyLarge = TextStyle(
      fontFamily = family,
      fontSize = 16.sp,
      lineHeight = 20.sp,
    ),
    headlineLarge = TextStyle(
      fontFamily = family,
    ),
    displaySmall = TextStyle(
      fontFamily = family,
      fontSize = 24.sp,
    ),
    titleMedium = TextStyle(
      fontFamily = family,
      fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
      fontFamily = family,
    ),
    titleLarge = TextStyle(
      fontFamily = family,
      fontSize = 20.sp,
      lineHeight = 32.sp,
    ),
  )

  return typography
}
