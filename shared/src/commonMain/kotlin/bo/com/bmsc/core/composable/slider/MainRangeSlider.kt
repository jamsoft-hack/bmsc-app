package bo.com.bmsc.core.composable.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppDimens
//import bo.com.bmsc.assets.BarikVectors
//import bo.com.bmsc.assets.barikvectors.BarikThumb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRangeSlider(
  modifier: Modifier = Modifier,
  maxZones: Int,
  value: Pair<Int, Int>,
  disabled: Pair<Int, Int>? = null,
  onChange: (range: Pair<Int, Int>) -> Unit,
) {
  val rangeTopLimit = maxZones + 1f
  val rangeBottomLimit = 1f

  val state = remember {
    RangeSliderState(
      value.first.toFloat(),
      (value.second + 1f),
      steps = maxZones - 1,
      valueRange = rangeBottomLimit..rangeTopLimit,
    )
  }

  state.onValueChangeFinished = {
    val isInRange = disabled?.let {
      state.activeRangeStart > it.first || state.activeRangeEnd <= it.second
    } ?: false

    if (isInRange || state.activeRangeEnd == state.activeRangeStart) {
      state.activeRangeStart = value.first.toFloat()
      state.activeRangeEnd = value.second + 1f
    } else {
      onChange(Pair(state.activeRangeStart.toInt(), state.activeRangeEnd.toInt() - 1))
    }
  }

  Box(Modifier.fillMaxWidth().composed { modifier }) {
    Box(modifier = Modifier.height(106.dp)) {
      Box(
        modifier = Modifier.fillMaxWidth().height(80.dp)
          .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
      )
      Column(
        modifier = Modifier.padding(
          start = AppDimens.ContentPadding,
          top = AppDimens.ContentPadding,
          end = AppDimens.ContentPadding
        )
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
        ) {
          Box(
            modifier = Modifier.fillMaxSize()
              .background(MaterialTheme.colorScheme.background, RoundedCornerShape(4.dp))
          )

          val rangeStart = state.activeRangeStart
          val rangeEnd = state.activeRangeEnd
          val totalRange = state.valueRange.endInclusive - state.valueRange.start

          val rangeStartPercent = (rangeStart - state.valueRange.start) / totalRange
          val rangeEndPercent = (rangeEnd - state.valueRange.start) / totalRange

          Row(
            modifier = Modifier.fillMaxWidth().height(36.dp),
            horizontalArrangement = Arrangement.Start
          ) {
            val rangeValue = rangeEndPercent - rangeStartPercent
            val endSpacer = 1f - rangeEndPercent
            if (rangeStartPercent > 0) {
              Box(modifier = Modifier.weight(rangeStartPercent))
            }
            if (rangeValue > 0) {
              Box(
                modifier = Modifier.weight(rangeValue).height(36.dp)
                  .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
              )
            }
            if (endSpacer > 0) {
              Box(modifier = Modifier.weight(endSpacer))
            }
          }

          Row(
            Modifier.fillMaxWidth().height(36.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
          ) {
            for (i in rangeBottomLimit.toInt() until rangeTopLimit.toInt()) {
              Text(
                text = "$i",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (i.toFloat() in rangeStart..<rangeEnd) Color.White else Color.Unspecified,
              )
            }
          }
        }
      }
      RangeSlider(
        state = state,
        modifier = Modifier
          .fillMaxWidth()
          .height(80.dp)
          .offset(y = 20.dp)
          .padding(horizontal = 6.dp),
        startThumb = {
//          Image(
//            BarikVectors.BarikThumb,
//            contentDescription = null,
//            modifier = Modifier.offset(y = 16.dp).height(41.dp).shadow(
//              elevation = 4.dp,
//              shape = BarikThumbShadowShape(),
//              clip = false,
//            ),
//          )
        },
        endThumb = {
//          Image(
//            BarikVectors.BarikThumb,
//            contentDescription = null,
//            modifier = Modifier.offset(y = 16.dp).height(41.dp).shadow(
//              elevation = 4.dp,
//              shape = BarikThumbShadowShape(),
//              clip = false,
//            ),
//          )
        },
        track = {
          Box(Modifier.fillMaxWidth().height(36.dp).background(Color.Unspecified))
        },
      )
    }
  }
}

class BarikThumbShadowShape : Shape {
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
  ): Outline {
    val path = Path().apply {
      moveTo(size.width * (28.249f / 29.0f), size.height * (21.0f / 41.0f))
      lineTo(size.width * (14.124f / 29.0f), size.height * (0.0f / 41.0f))
      lineTo(size.width * (0.0f / 29.0f), size.height * (21.0f / 41.0f))
      lineTo(size.width * (0.125f / 29.0f), size.height * (21.0f / 41.0f))
      lineTo(size.width * (0.125f / 29.0f), size.height * (37.0f / 41.0f))

      // Curved bottom left
      arcTo(
        rect = Rect(
          left = 0f,
          top = size.height * (37.0f / 41.0f),
          right = size.width * (4.125f / 29.0f),
          bottom = size.height
        ), startAngleDegrees = 180f, sweepAngleDegrees = 90f, forceMoveTo = false
      )

      lineTo(size.width * (24.125f / 29.0f), size.height)

      // Curved bottom right
      arcTo(
        rect = Rect(
          left = size.width * (24.125f / 29.0f),
          top = size.height * (37.0f / 41.0f),
          right = size.width,
          bottom = size.height
        ), startAngleDegrees = 270f, sweepAngleDegrees = 90f, forceMoveTo = false
      )

      lineTo(size.width * (28.125f / 29.0f), size.height * (21.0f / 41.0f))
      lineTo(size.width * (28.249f / 29.0f), size.height * (21.0f / 41.0f))
      close()
    }
    return Outline.Generic(path)
  }
}
