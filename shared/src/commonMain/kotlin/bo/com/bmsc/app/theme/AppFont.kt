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
   val Nunito = FontFamily(
    Font(Res.font.roboto_thin, weight = FontWeight.Thin),
    Font(Res.font.roboto_light, weight = FontWeight.Light),
    Font(Res.font.roboto_regular, weight = FontWeight.Normal),
    Font(Res.font.roboto_medium, weight = FontWeight.Medium),
    Font(Res.font.roboto_bold, weight = FontWeight.Bold),
    Font(Res.font.roboto_black, weight = FontWeight.Black),
  )
  val Inter = Nunito

  val MentaTypography = Typography(
    displayLarge = TextStyle(
      fontFamily = Nunito, fontWeight = FontWeight.ExtraBold,
      fontSize = 57.sp, lineHeight = 60.sp, letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
      fontFamily = Nunito, fontWeight = FontWeight.ExtraBold,
      fontSize = 45.sp, lineHeight = 50.sp, letterSpacing = (-0.20).sp
    ),
    displaySmall = TextStyle(
      fontFamily = Nunito, fontWeight = FontWeight.ExtraBold,
      fontSize = 36.sp, lineHeight = 42.sp, letterSpacing = (-0.15).sp
    ),
    headlineLarge = TextStyle(
      fontFamily = Nunito, fontWeight = FontWeight.Bold,
      fontSize = 32.sp, lineHeight = 38.sp, letterSpacing = (-0.12).sp
    ),
    headlineMedium = TextStyle(
      fontFamily = Nunito, fontWeight = FontWeight.Bold,
      fontSize = 28.sp, lineHeight = 34.sp, letterSpacing = (-0.10).sp
    ),
    headlineSmall = TextStyle(
      fontFamily = Nunito, fontWeight = FontWeight.Bold,
      fontSize = 24.sp, lineHeight = 30.sp, letterSpacing = (-0.05).sp
    ),
    titleLarge = TextStyle(
      fontFamily = Nunito, fontWeight = FontWeight.Bold,
      fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.SemiBold,
      fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.SemiBold,
      fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.10.sp
    ),
    bodyLarge = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.Normal,
      fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.50.sp
    ),
    bodyMedium = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.Normal,
      fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.Normal,
      fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.40.sp
    ),
    labelLarge = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.SemiBold,
      fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.10.sp
    ),
    labelMedium = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.SemiBold,
      fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.50.sp
    ),
    labelSmall = TextStyle(
      fontFamily = Inter, fontWeight = FontWeight.SemiBold,
      fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.50.sp
    ),
  )

  return MentaTypography
}
