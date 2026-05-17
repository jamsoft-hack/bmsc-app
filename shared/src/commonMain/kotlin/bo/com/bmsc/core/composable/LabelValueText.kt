package bo.com.bmsc.core.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabelValueTextSmall(
  label: StringResource,
  value: String,
  fontWeight: FontWeight? = null,
  modifier: Modifier = Modifier,
  highContrast: Boolean = false,
  color: Color = Color.Unspecified,
) = LabelValueTextSmall(stringResource(label), value, fontWeight, modifier, highContrast, color)

@Composable
fun LabelValueTextSmall(
  label: String,
  value: String,
  fontWeight: FontWeight? = null,
  modifier: Modifier = Modifier,
  highContrast: Boolean = false,
  color: Color = Color.Unspecified,
) {
  Column(
    modifier,
    verticalArrangement = Arrangement.spacedBy(2.dp)
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodySmall,
      color = if (highContrast) Color.White else AppColors.reactive.textSubtlest,
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = fontWeight,
      color = color,
    )
  }
}

@Composable
fun LabelValueText(
  label: StringResource,
  value: String,
  fontWeight: FontWeight? = null,
  modifier: Modifier = Modifier,
  highContrast: Boolean = false,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) = LabelValueText(
  stringResource(label),
  value,
  fontWeight,
  modifier,
  highContrast,
  horizontalAlignment,
)

@Composable
fun LabelValueText(
  label: String,
  value: String,
  fontWeight: FontWeight? = null,
  modifier: Modifier = Modifier,
  highContrast: Boolean = false,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
  Column(
    modifier,
    verticalArrangement = Arrangement.spacedBy(2.dp),
    horizontalAlignment = horizontalAlignment,
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium,
      color = if (highContrast) Color.White else AppColors.reactive.textSubtlest,
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = fontWeight,
    )
  }
}
