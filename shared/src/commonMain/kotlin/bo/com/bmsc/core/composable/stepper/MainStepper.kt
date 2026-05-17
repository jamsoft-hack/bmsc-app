package bo.com.bmsc.core.composable.stepper

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bo.com.bmsc.app.theme.AppDimens

@Composable
fun MainStepper(
  modifier: Modifier = Modifier,
  steps: Int,
  current: Int,
) {
  val primaryColor = MaterialTheme.colorScheme.primaryContainer
  val grayColor = MaterialTheme.colorScheme.surfaceContainerHigh

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier.widthIn(max = AppDimens.MaxContentWidth),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      for (i in 1..steps) {
        val targetColor = if (i <= current) {
          Pair(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer
          )
        } else  {
          Pair(
            MaterialTheme.colorScheme.surfaceContainerHigh,
            MaterialTheme.colorScheme.onTertiaryContainer
          )
        }

        val animatedStepBackground by animateColorAsState(
          targetValue = targetColor.first,
          animationSpec = tween(durationMillis = 300)
        )
        val animatedStepColor by animateColorAsState(
          targetValue = targetColor.second,
          animationSpec = tween(durationMillis = 300)
        )

        StepCircle(
          stepNumber = i,
          size = 36.dp,
          background = animatedStepBackground,
          color = animatedStepColor
        )

        if (i < steps) {
          val targetConnectorColor = if (i < current) primaryColor else grayColor
          val animatedConnectorColor by animateColorAsState(
            targetValue = targetConnectorColor,
            animationSpec = tween(durationMillis = 300)
          )
          StepConnector(color = animatedConnectorColor, Modifier.height(4.dp).weight(1f))
        }
      }
    }
  }
}

@Composable
fun StepCircle(
  stepNumber: Int,
  size: Dp,
  background: Color,
  color: Color
) {
  Box(
    modifier = Modifier.size(size),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(size)) {
      drawCircle(color = background)
    }
    Text(
      text = stepNumber.toString(),
      style = TextStyle(
        color = color,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    )
  }
}

@Composable
fun StepConnector(
  color: Color,
  modifier: Modifier
) {
  Box(
    modifier = modifier
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      drawRect(color = color)
    }
  }
}