package bo.com.bmsc.core.extension

import androidx.compose.material3.DividerDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity

fun Modifier.bottomBorder(
  strokeWidth: Dp,
  color: Color? = null,
) = composed(
  factory = {
    val density = LocalDensity.current
    val strokeWidthPx = density.run { strokeWidth.toPx() }
    val borderColor = color ?: DividerDefaults.color

    Modifier.drawBehind {
      val width = size.width
      val height = size.height - strokeWidthPx / 2

      drawLine(
        color = borderColor,
        start = Offset(x = 0f, y = height),
        end = Offset(x = width, y = height),
        strokeWidth = strokeWidthPx
      )
    }
  }
)

fun Modifier.topBorder(
  strokeWidth: Dp,
  color: Color? = null,
) = composed(
  factory = {
    val density = LocalDensity.current
    val strokeWidthPx = density.run { strokeWidth.toPx() }
    val borderColor = color ?: DividerDefaults.color

    Modifier.drawBehind {
      val width = size.width

      drawLine(
        color = borderColor,
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = width, y = 0f),
        strokeWidth = strokeWidthPx
      )
    }
  }
)

private val HorizontalScrollConsumer = object : NestedScrollConnection {
  override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
  override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

fun Modifier.disabledHorizontalPointerInputScroll(disabled: Boolean = true) =
  if (disabled) this.nestedScroll(HorizontalScrollConsumer) else this
