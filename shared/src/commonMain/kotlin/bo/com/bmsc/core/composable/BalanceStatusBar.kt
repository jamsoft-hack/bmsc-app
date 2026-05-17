package bo.com.bmsc.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.core.helper.pickStatusColor
import kotlin.math.max

@Composable
fun BalanceStatusBar(
  current: Float,
  total: Float,
  limits: List<Float>,
  enabled: Boolean = true,
  hideBar: Boolean = false,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(8.dp)
      .background(AppColors.reactive.iconSeparator, CircleShape)
      .padding(horizontal = 2.5.dp, vertical = 2.dp)
  ) {
    if (!hideBar) {
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .fillMaxWidth(max(a = 0.02f, if(current == 0f && total == 0f) 0f else current / total))
          .background(
            if (!enabled) AppColors.SecondaryGrey else pickStatusColor(current, total, limits),
            CircleShape,
          )
      )
    }
  }
}

@Composable
fun BalanceStatusBar(
  current: Int,
  total: Int,
  limits: List<Float>,
  enabled: Boolean = true,
) {
  BalanceStatusBar(current.toFloat(), total.toFloat(), limits, enabled)
}
