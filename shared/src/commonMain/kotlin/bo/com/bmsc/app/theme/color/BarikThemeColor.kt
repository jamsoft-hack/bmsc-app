package bo.com.bmsc.app.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

interface BarikThemeColor {
  val background: Color
  val textDefault: Color
  val textSubtle: Color
  val textSubtlest: Color

  val textNegative: Color

  val clearer: Color
  val secondaryBackground: Color

  val danger: Color
  val warning: Color
  val success: Color
  val successMid: Color
  val disabled: Color

  val successContrast: Color
  val warningContrast: Color
  val dangerContrast: Color
  val disabledContrast: Color

  val borderSuccessContrast: Color
  val borderWarningContrast: Color
  val borderDangerContrast: Color
  val borderDisabledContrast: Color

  val textDangerContrast: Color
  val textSuccessContrast: Color
  val textWarningContrast: Color

  val skeletonDark: Color
  val skeletonLight: Color
  val skeletonLightest: Color

  val scheme: ColorScheme

  val toastSuccessContrast: Color
  val toastWarningContrast: Color
  val toastDangerContrast: Color
  val toastInfoContrast: Color

  val textSuccess: Color
  val textLucked: Color
  val textInfo: Color
  val textDanger: Color

  val borderCard: Color
  val iconSeparator: Color

  val iconBrandLight: Color
  val iconBrandDark: Color

  val surfaceBrandSubtlest: Color

  val trafficSuccess: Color
  val trafficWarning: Color
}
