package bo.com.bmsc.app.theme.color

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import bo.com.bmsc.app.theme.AppColors

object HighContrastColors : BarikThemeColor {
  private val SurfaceActionLight = Color(0xFF8A1E1B)

  private val SuccessSubtle = Color(0xFF3A5E29)
  private val DangerSubtle = Color(0xFFE85450)
  private val WarningSubtle = Color(0xFF846600)
  private val DisabledSubtle = Color(0xFF000000)

  override val textSuccess = Color(0xFF5C9442)
  override val textLucked = Color(0xFFAB8400)
  override val textInfo = Color(0xFF646872)
  override val textDanger = Color(0xFFE3312C)

  override val background = Color(0xFF000000)
  override val textDefault = Color.White
  override val textSubtle = Color(0xFFEDEEEF)
  override val textSubtlest = Color(0xFFFFFFFF)
  override val textNegative = Color(0xFF152C44)

  override val trafficSuccess = Color(0xFF3A5E29)
  override val trafficWarning = Color(0xFF846600)

  override val clearer = Color(0xFF7C8088)

  /** State Colors */
  override val successMid = Color(0xFFCCEDBD)

  override val danger = Color(0xFFE3312C)
  override val warning = Color(0xFFF4BD00)
  override val success = Color(0xFF83D35E)

  override val disabled = Color(0xFF4B515C)

  override val secondaryBackground = Color(0xFF646872)

  override val skeletonDark = Color(0xFF4B515C)
  override val skeletonLight = Color(0xFF646872)
  override val skeletonLightest = Color(0xFF2F3642)

  override val borderSuccessContrast = Color(0xFF83D35E)
  override val borderWarningContrast = Color(0xFFF9D96B)
  override val borderDangerContrast = Color(0xFFFCEAEA)
  override val borderDisabledContrast = Color(0xFF898D94)

  override val successContrast = Color(0xFF83D35E)
  override val warningContrast = Color(0xFFF9D96B)
  override val dangerContrast = Color(0xFFFCEAEA)
  override val disabledContrast = Color(0xFF898D94)

  override val textDangerContrast = Color(0xFFD4A8AB)
  override val textSuccessContrast = Color(0xFFF3FBEF)
  override val textWarningContrast = Color(0xFFF3FBEF)

  override val toastSuccessContrast = SuccessSubtle
  override val toastWarningContrast = WarningSubtle
  override val toastDangerContrast = DangerSubtle
  override val toastInfoContrast = DisabledSubtle

  override val borderCard = Color.White
  override val iconSeparator = Color(0xFF7C8088)

  override val iconBrandLight = Color(0xFFF4ABA8)
  override val iconBrandDark = Color(0xFFE85450)

  override val surfaceBrandSubtlest = Color(0xFF8A1E1B)

  private val AlmostNegative = Color(0xFF3C424E)

  private val Negative = Color(0xFF242B38)
  private val Separator = Color(0xFF6E737C)
  private val BorderLight = Color(0xFF565C66)

  private val PageSubtle = Color(0xFF4B515C)
  private val TextBrand: Color = Color(0xFFEF8885)
  private val IconsClearer = Color(0xFF7C8088)

  override val scheme = darkColorScheme(
    primary = TextBrand,
    onPrimary = Color.White,
    secondary = SurfaceActionLight,
    onSecondary = Color.White,

    primaryContainer = AppColors.Primary,
    onPrimaryContainer = Color.White,

    outlineVariant = Color.White,
    surfaceContainerLow = background,
    surfaceContainerHigh = IconsClearer,

    background = background,
    onBackground = textDefault,

    surface = background,
    onSurface = textDefault,

    tertiaryContainer = Separator,
    onTertiaryContainer = Negative,

    secondaryContainer = PageSubtle,
    onSecondaryContainer = textDefault,

    surfaceVariant = AlmostNegative,
    onSurfaceVariant = textDefault,

    error = TextBrand,
  )
}
