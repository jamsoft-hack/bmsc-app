package bo.com.bmsc.core.composable.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens

@Composable
fun SecondaryButton(
  text: String,
  modifier: Modifier = Modifier,
  style: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
  colors: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = AppColors.reactive.secondaryBackground,
    contentColor = MaterialTheme.colorScheme.onBackground,
    disabledContainerColor = AppColors.reactive.disabled,
    disabledContentColor = MaterialTheme.colorScheme.onBackground,
  ),
  prefixIcon: ImageVector? = null,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  Button(
    modifier = modifier.heightIn(min = AppDimens.ButtonHeight).fillMaxWidth(),
    colors = colors,
    enabled = enabled,
    onClick = onClick,
    shape = RoundedCornerShape(AppDimens.ButtonRadius)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      prefixIcon?.let {
        Icon(
          imageVector = it,
          contentDescription = null,
        )
      }
      Text(text = text.uppercase(), style = style)
    }
  }
}
