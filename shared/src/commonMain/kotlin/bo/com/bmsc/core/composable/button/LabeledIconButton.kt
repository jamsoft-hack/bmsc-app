package bo.com.bmsc.core.composable.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppDimens
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabeledIconButton(
  text: StringResource? = null,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  icon: ImageVector,
  reverse: Boolean = true,
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(AppDimens.ButtonRadius))
      .clickable(onClick = onClick)
      .padding(8.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      if (reverse) {
        text?.let { text ->
          Text(
            text = stringResource(text),
            style = MaterialTheme.typography.labelSmall,
          )
        }
        Icon(
          imageVector = icon,
          contentDescription = text?.let { stringResource(text) },
        )
      } else {
        Icon(
          imageVector = icon,
          contentDescription = text?.let { stringResource(text) },
        )
        text?.let { text ->
          Text(
            text = stringResource(text),
            style = MaterialTheme.typography.labelSmall
          )
        }
      }
    }
  }
}
