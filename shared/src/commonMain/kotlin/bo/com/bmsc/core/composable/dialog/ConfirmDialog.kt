package bo.com.bmsc.core.composable.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.action_accept
import bmsc.shared.generated.resources.action_cancel
import bmsc.shared.generated.resources.dialog_confirmation_delete_proof_information
import bmsc.shared.generated.resources.dialog_confirmation_title_attention
import bo.com.bmsc.core.helper.parseMarkdownToAnnotatedString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmDialog(
  buttonLabel: StringResource,
  modifier: Modifier,
  onConfirm: () -> Unit = {},
) {
  val openAlertDialog = remember { mutableStateOf(false) }
  val confirmDialogDelete = parseMarkdownToAnnotatedString(Res.string.dialog_confirmation_delete_proof_information)

  when {
    openAlertDialog.value -> {
      BaseDialog(
        onDismiss = {
          openAlertDialog.value = false
        },
        onConfirm = {
          openAlertDialog.value = false

          onConfirm()
        },
        title = Res.string.dialog_confirmation_title_attention,
        message = confirmDialogDelete,
        confirmLabel = Res.string.action_accept,
        cancelLabel = Res.string.action_cancel,
      )
    }
  }

  TextButton(
    modifier = modifier,
    onClick = {
      openAlertDialog.value = true
    },
    shape = RectangleShape,
  ) {
    Text(
      text = stringResource(buttonLabel),
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center
    )
  }
}
