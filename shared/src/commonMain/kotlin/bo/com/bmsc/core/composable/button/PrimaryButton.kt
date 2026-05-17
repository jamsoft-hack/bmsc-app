package bo.com.bmsc.core.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrimaryButton(
  text: String,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  fontWeight: FontWeight = FontWeight.Bold,
  style: TextStyle = MaterialTheme.typography.bodyLarge,
  height: Dp = AppDimens.ButtonHeight,
  maxLines: Int = Int.MAX_VALUE,
  border: BorderStroke? = null,
  onClick: () -> Unit,
) = BasePrimaryButton(
  modifier = modifier.heightIn(min = height).fillMaxWidth(),
  enabled = enabled,
  border = border,
  onClick = onClick,
) {
  Text(
    text,
    style = style,
    fontWeight = fontWeight,
    maxLines = maxLines,
    color = Color.White,
  )
}

@Composable
fun PrimaryButton(
  text: StringResource,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  loading: Boolean = false,
  onClick: () -> Unit,
) = BasePrimaryButton(
  modifier = modifier.heightIn(min = AppDimens.ButtonHeight).fillMaxWidth(),
  enabled = enabled && !loading,
  onClick = onClick,
) {
  Text(
    stringResource(text).uppercase(),
    style = MaterialTheme.typography.bodyLarge,
    fontWeight = FontWeight.Bold,
  )

  if (loading) {
    Spacer(Modifier.width(8.dp))
    CircularProgressIndicator(
      color = MaterialTheme.colorScheme.onPrimary,
      strokeWidth = 3.dp,
      modifier = Modifier.size(26.dp)
    )
  }
}

@Composable
fun BasePrimaryButton(
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  border: BorderStroke? = null,
  onClick: () -> Unit,
  content: @Composable RowScope.() -> Unit
) {
  Button(
    modifier = modifier.fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
      disabledContainerColor = AppColors.DarkPrimary,
      disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
    enabled = enabled,
    onClick = onClick,
    shape = RoundedCornerShape(AppDimens.ButtonRadius),
    content = content,
    border = border,
  )
}
