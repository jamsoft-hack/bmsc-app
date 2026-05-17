package bo.com.bmsc.app.theme.color

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import bo.com.bmsc.app.theme.AppColors

object LightColors: BarikThemeColor {
  private val SurfaceActionLight = Color(0xFFFCEAEA)

  override val background = Color(0xFFF5F6F6)

  private val BorderLight = Color(0xFFE8E8E8)
  private val DangerSubtle = Color(0xFFF4EAEA)
  private val WarningSubtle = Color(0xFFFEF8E6)
  private val SuccessSubtle = Color(0xFFF3FBEF)
  private val DisabledSubtle = background

  override val textSubtlest = Color(0xFF6E737C)

  override val clearer = Color(0xFFA3A6AB)

  override val textDefault = Color(0xFF242B38)
  override val textSubtle = Color(0xFF4B515C)

  override val textNegative = Color(0xFFFFFFFF)

  /** State Colors */
  val Error = Color(0xFFE3312C)
  override val danger = Color(0xFFE3312C)
  override val warning = Color(0xFFF4BD00)
  override val success = Color(0xFF83D35E)

  override val successMid = Color(0xFF5C9442)
  override val disabled = Color(0xFFBBBDC1)

  /** Skeleton Colors */
  override val skeletonDark = Color(0xFFBBBDC1)
  override val skeletonLight = Color(0xFFE3E3E5)
  override val skeletonLightest = Color(0xFFBBBDC1)

  override val borderSuccessContrast = Color(0xFF83D35E)
  override val borderWarningContrast = Color(0xFF846600)
  override val borderDangerContrast = Color(0xFF8A1E1B)
  override val borderDisabledContrast = Color(0xFF898D94)

  override val successContrast = Color(0xFF83D35E)
  override val warningContrast = Color(0xFF846600)
  override val dangerContrast = Color(0xFF8A1E1B)
  override val disabledContrast = Color(0xFF898D94)

  override val textDangerContrast = Color(0xFF5B1A1E)
  override val textSuccessContrast = Color(0xFF3A5E29)
  override val textWarningContrast = Color(0xFF846600)

  override val toastSuccessContrast = SuccessSubtle
  override val toastWarningContrast = WarningSubtle
  override val toastDangerContrast = DangerSubtle
  override val toastInfoContrast = DisabledSubtle

  override val textSuccess = Color(0xFF5C9442)
  override val textLucked = Color(0xFFAB8400)
  override val textInfo = Color(0xFF646872)
  override val textDanger = Color(0xFFE3312C)

  override val borderCard = Color.Transparent
  override val iconSeparator = Color(0xFFE3E3E5)

  override val iconBrandLight = Color(0xFFEF8885)
  override val iconBrandDark = Color(0xFF8A1E1B)
  override val secondaryBackground = Color(0xFFD9D9D9)

  override val surfaceBrandSubtlest = Color(0xFFFCEAEA)

  override val trafficSuccess = Color(0xFF83D35E)
  override val trafficWarning = Color(0xFFF4BD00)

  private val AlmostNegative: Color = Color(0xFFF6F7F7)
  private val IconsClearer = Color(0xFFA3A6AB)

  private val Negative = Color.White
  private val Separator = Color(0xFFE3E3E5)
  private val PageSubtle = Color(0xFFE3E3E5)

  override val scheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = Color.White,
    secondary = SurfaceActionLight,
    onSecondary = Color.White,
    background = background,
    onBackground = textDefault,

    primaryContainer = AppColors.Primary,
    onPrimaryContainer = Color.White,

    surface = Color.White,
    surfaceVariant = AlmostNegative,

    outlineVariant = BorderLight,

    surfaceContainerLow = Color.White,
    surfaceContainerHigh = IconsClearer,

    secondaryContainer = PageSubtle,
    onSecondaryContainer = textDefault,

    tertiaryContainer = Separator,
    onTertiaryContainer = Negative,

    onSurface = textDefault,
    onSurfaceVariant = Color.Black,

    error = Color(0xFFE3312C),
  )
}
