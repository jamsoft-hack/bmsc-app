package bo.com.bmsc.core.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.core.composable.button.PrimaryButton
import bo.com.bmsc.core.composable.button.SecondaryButton
import bo.com.bmsc.core.helper.parseMarkdownToAnnotatedString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BaseCompactError(
  modifier: Modifier = Modifier,
  icon: ImageVector,
  title: StringResource,
  description: StringResource,
  arguments: List<Any> = emptyList(),
  onClose: () -> Unit,
  onRetry: () -> Unit,
) {
  val typography = MaterialTheme.typography

  Column(
    verticalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
      .fillMaxHeight()
      .padding(AppDimens.ContentPadding)
      .verticalScroll(rememberScrollState())
  ) {
    Column(
      Modifier.fillMaxWidth().padding(vertical = 32.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Icon(
        imageVector = icon,
        contentDescription = "Error Icon",
        tint = MaterialTheme.colorScheme.error,
      )

      Text(
        text = stringResource(title),
        style = typography.titleLarge,
        fontWeight = FontWeight.Bold,
      )

      Text(
        text = parseMarkdownToAnnotatedString(description, *arguments.toTypedArray()),
        style = typography.bodyLarge,
        textAlign = TextAlign.Center,
      )
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      PrimaryButton(
        "Retry",
        onClick = onRetry
      )
      SecondaryButton(
        "Close",
        onClick = onClose,
      )
    }
  }
}
