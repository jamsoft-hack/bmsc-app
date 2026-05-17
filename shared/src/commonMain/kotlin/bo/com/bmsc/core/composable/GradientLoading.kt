package bo.com.bmsc.core.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GradientLoading(
  modifier: Modifier = Modifier,
  containerColor: Color = MaterialTheme.colorScheme.background,
) {
  val transition = rememberInfiniteTransition()
  val angle by transition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )

  Box(
    modifier = modifier
      .clip(CircleShape)
      .size(80.dp)
      .background(MaterialTheme.colorScheme.primaryContainer)
      .rotate(angle)
  ) {
    Box(
      modifier = Modifier
        .clip(CircleShape)
        .size(120.dp)
        .offset(22.dp, 22.dp)
        .background(
          Brush.radialGradient(
            listOf(Color(0xFFFFC858), Color(0x0000)),
          )
        )
    )

    Box(
      modifier = Modifier
        .clip(CircleShape)
        .size(52.dp)
        .align(alignment = Alignment.Center)
        .background(containerColor)
    )
  }
}

@Composable
fun GradientLoadingSmall(modifier: Modifier = Modifier) {
  val transition = rememberInfiniteTransition()
  val angle by transition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )

  Box(
    modifier = modifier
      .clip(CircleShape)
      .size(54.dp)
      .background(MaterialTheme.colorScheme.primaryContainer)
      .rotate(angle)
  ) {
    Box(
      modifier = Modifier
        .clip(CircleShape)
        .size(100.dp)
        .offset(17.dp, 17.dp)
        .background(
          Brush.radialGradient(
            listOf(Color(0xFFFFC858), Color(0x0000)),
          )
        )
    )

    Box(
      modifier = Modifier
        .clip(CircleShape)
        .size(32.dp)
        .align(alignment = Alignment.Center)
        .background(MaterialTheme.colorScheme.background)
    )
  }
}
