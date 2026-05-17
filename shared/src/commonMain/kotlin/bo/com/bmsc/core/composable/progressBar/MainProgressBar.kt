package bo.com.bmsc.core.composable.progressBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors

@Composable
fun MainProgressBar(value: Int, maxValue: Int, enabled: Boolean = true) {
  Box(
    modifier = Modifier.fillMaxWidth()
      .height(8.dp)
      .clip(RoundedCornerShape(50.dp))
      .background(MaterialTheme.colorScheme.outlineVariant),
    contentAlignment = Alignment.CenterStart
  ) {
    BoxWithConstraints {
      val percentage = if (maxValue > 0) value.toFloat() / maxValue.toFloat() else 0f
      val progressBarWidthPx = constraints.maxWidth * percentage

      val progressBarWidthDp = with(LocalDensity.current) { progressBarWidthPx.toDp() }

      Box(
        modifier = Modifier
          .width(if (value > 0) progressBarWidthDp else 8.dp)
          .height(4.dp)
          .padding(horizontal = 2.dp)
          .clip(RoundedCornerShape(50.dp))
          .background(colorByValue(value, enabled))
      )
    }
  }
}

@Composable
private fun colorByValue(value: Int, enabled: Boolean): Color = if (enabled) {
  when (value) {
    in 0..5 -> AppColors.Danger
    in 6..10 -> AppColors.reactive.trafficWarning
    else -> AppColors.reactive.trafficSuccess
  }
} else {
  AppColors.SecondaryGrey
}