package bo.com.bmsc.core.composable.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens

@Composable
fun BaseElevatedCard(
  modifier: Modifier = Modifier,
  colors: CardColors = CardDefaults.elevatedCardColors(),
  onClick: () -> Unit = {},
  enabled: Boolean = true,
  shape: Shape = CardDefaults.elevatedShape,
  content: @Composable ColumnScope.() -> Unit,
) {
  ElevatedCard(
    modifier = modifier.border(1.dp, AppColors.reactive.borderCard, MaterialTheme.shapes.medium),
    enabled = enabled,
    colors = colors,
    shape = shape,
    onClick = onClick,
    content = content,
  )
}

@Composable
fun RowElevatedCard(
  modifier: Modifier = Modifier,
  colors: CardColors = CardDefaults.elevatedCardColors(),
  onClick: () -> Unit = {},
  enabled: Boolean = true,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
  verticalAlignment: Alignment.Vertical = Alignment.Top,
  content: @Composable RowScope.() -> Unit,
) {
  ElevatedCard(
    modifier = Modifier.border(1.dp, AppColors.reactive.borderCard, MaterialTheme.shapes.medium),
    enabled = enabled,
    colors = colors,
    onClick = onClick,
  ) {
    Row(
      modifier = modifier,
      verticalAlignment = verticalAlignment,
      horizontalArrangement = horizontalArrangement,
      content = content,
    )
  }
}

@Composable
fun ColumnElevatedCard(
  modifier: Modifier = Modifier,
  colors: CardColors = CardDefaults.elevatedCardColors(),
  onClick: (() -> Unit)? = null,
  enabled: Boolean = true,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  content: @Composable ColumnScope.() -> Unit,
) {
  if(onClick == null) {
    ElevatedCard(
      modifier = Modifier
        .widthIn(0.dp, AppDimens.MaxContentWidth)
        .border(1.dp, AppColors.reactive.borderCard, MaterialTheme.shapes.medium),
      colors = colors,
    ) {
      Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content,
      )
    }
  } else {
    ElevatedCard(
      modifier = Modifier
        .widthIn(0.dp, AppDimens.MaxContentWidth)
        .border(1.dp, AppColors.reactive.borderCard, MaterialTheme.shapes.medium),
      enabled = enabled,
      colors = colors,
      onClick = onClick,
    ) {
      Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content,
      )
    }
  }
}
