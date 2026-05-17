package bo.com.bmsc.core.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import bo.com.bmsc.app.theme.AppColors

@Composable
fun pickStatusColor(
  value: Float,
  maxValue: Float,
  limits: List<Float>,
): Color  {
  val amount = if(value == 0f && maxValue == 0f) 0f else value / maxValue

  return if (amount <= limits.first()) {
    AppColors.Danger
  } else if (amount <= limits.last()) {
    AppColors.reactive.trafficWarning
  } else {
    AppColors.reactive.trafficSuccess
  }
}
