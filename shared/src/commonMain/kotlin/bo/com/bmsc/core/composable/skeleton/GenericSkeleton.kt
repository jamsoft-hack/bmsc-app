package bo.com.bmsc.core.composable.skeleton

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import bo.com.bmsc.app.theme.AppColors

@Composable
fun GenericSkeleton(
  modifier: Modifier = Modifier,
  lightest: Boolean = false,
  shape: Shape = MaterialTheme.shapes.medium,
  durationMillis: Int = 600,
) {
  val transition = rememberInfiniteTransition()

  val animatedColor by transition.animateColor(
    initialValue = if (lightest) AppColors.reactive.skeletonLightest else AppColors.reactive.skeletonLight,
    targetValue = if (lightest) AppColors.reactive.skeletonLight else AppColors.reactive.skeletonDark,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = durationMillis, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  Box(modifier = modifier.clip(shape).background(animatedColor))
}
