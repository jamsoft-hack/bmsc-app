package bo.com.bmsc.core.composable.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.action_accept
import bmsc.shared.generated.resources.action_cancel
import bmsc.shared.generated.resources.dialog_confirmation_title_attention
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.core.composable.button.PrimaryButton
import bo.com.bmsc.core.composable.button.SecondaryButton
import bo.com.bmsc.core.helper.parseMarkdownToAnnotatedString
import bo.com.bmsc.core.locale.AppLang
import bo.com.bmsc.core.locale.rememberAppLocale
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BaseDialog(
  onConfirm: () -> Unit,
  onCancel: () -> Unit = {},
  title: StringResource,
  message: AnnotatedString,
  onDismiss: () -> Unit,
  confirmLabel: StringResource? = null,
  cancelLabel: StringResource? = null,
) = BaseDialog(
  onConfirm = onConfirm,
  onCancel = onCancel,
  title = stringResource(title),
  message = {
    Text(
      text = message,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.padding(AppDimens.ContentPadding),
    )
  },
  onDismiss = onDismiss,
  confirmLabel = confirmLabel?.let { resource -> stringResource(resource) },
  cancelLabel = cancelLabel?.let { resource -> stringResource(resource) },
)

@Composable
fun BaseDialog(
  onConfirm: () -> Unit,
  onCancel: () -> Unit = {},
  title: String,
  message: String,
  onDismiss: () -> Unit,
  confirmLabel: String? = null,
  cancelLabel: String? = null,
) {
  BaseDialog(
    onConfirm = onConfirm,
    onCancel = onCancel,
    title = title,
    message = {
      Text(
        text = message,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(AppDimens.ContentPadding),
      )
    },
    onDismiss = onDismiss,
    confirmLabel = confirmLabel,
    cancelLabel = cancelLabel,
  )
}

@Composable
fun BaseDialog(
  onConfirm: () -> Unit,
  onCancel: () -> Unit = {},
  title: String? = null,
  message: @Composable () -> Unit,
  onDismiss: () -> Unit,
  secondaryButtonStyle: DialogActionStyle = DialogActionStyle.SECONDARY,
  confirmLabel: String? = null,
  cancelLabel: String? = null,
) {
  val measure = rememberTextMeasurer()

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(
      usePlatformDefaultWidth = false,
      dismissOnBackPress = cancelLabel != null,
    )
  ) {
    Surface(
      color = MaterialTheme.colorScheme.surface,
      shape = RoundedCornerShape(15.dp),
      modifier = Modifier.widthIn(320.dp, 450.dp)
        .padding(AppDimens.ContentPadding)
        .border(1.dp, AppColors.reactive.borderCard, MaterialTheme.shapes.medium),
    ) {
      Column {
        title?.let {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .background(MaterialTheme.colorScheme.primaryContainer)
              .fillMaxWidth()
              .padding(AppDimens.ContentPadding),
          ) {
            Text(
              text = it,
              color = Color.White,
              fontWeight = FontWeight.SemiBold,
              style = MaterialTheme.typography.titleLarge,
            )
//            Icon(
//              imageVector = BarikIcons.Close,
//              tint = Color.White,
//              contentDescription = cancelLabel,
//              modifier = Modifier
//                .align(Alignment.CenterEnd)
//                .clickable(onClick = onDismiss)
//            )
          }
        }

        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          message()

          HorizontalDivider()

          BoxWithConstraints {
            val boxPadding = with(LocalDensity.current) { 12.dp.toPx() }
            val buttonPadding = with(LocalDensity.current) { 20.dp.toPx() }
            val totalWidth = constraints.maxWidth - boxPadding * 2

            FlowRow(
              horizontalArrangement = Arrangement.spacedBy(12.dp),
              verticalArrangement = Arrangement.spacedBy(12.dp),
              itemVerticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(AppDimens.ContentPadding),
            ) {
              val fullWidthButton = confirmLabel?.let {
                val value = measure.measure(
                  confirmLabel.uppercase(),
                  style = MaterialTheme.typography.bodyLarge
                )

                val halfSize = value.size.width + buttonPadding * 2 + boxPadding
                println("SIZES: $totalWidth $halfSize $boxPadding $buttonPadding")

                halfSize > totalWidth / 2 || cancelLabel == null
              } ?: true

              val modifier =
                if (!fullWidthButton) Modifier.width(IntrinsicSize.Min).weight(1f) else Modifier

              if (fullWidthButton) {
                confirmLabel?.let {
                  PrimaryButton(
                    text = confirmLabel.uppercase(),
                    modifier = modifier,
                    onClick = onConfirm,
                  )
                }
              }

              cancelLabel?.let {
                if (secondaryButtonStyle == DialogActionStyle.SECONDARY) {
                  SecondaryButton(
                    text = cancelLabel,
                    modifier = modifier,
                    onClick = {
                      onDismiss.invoke()
                      onCancel.invoke()
                    },
                  )
                } else {
                  TextButton(
                    modifier = Modifier.height(48.dp).fillMaxWidth(),
                    onClick = {
                      onDismiss.invoke()
                      onCancel.invoke()
                    },
                  ) {
                    Text(
                      text = cancelLabel,
                      style = MaterialTheme.typography.bodyMedium,
                    )
                  }
                }
              }

              if (!fullWidthButton) {
                PrimaryButton(
                  text = confirmLabel.uppercase(),
                  modifier = modifier,
                  onClick = onConfirm,
                )
              }
            }
          }
        }
      }
    }
  }
}

enum class DialogActionStyle {
  SECONDARY,
  TEXT_PRIMARY
}

@Preview
@Composable
fun BaseDialogExample() {
  val annotatedString =
    parseMarkdownToAnnotatedString("Text Markown")
  val language = rememberAppLocale()

  BaseDialog(
    onConfirm = {
      language.changeLanguage(AppLang.Euskera.code)
    },
    onCancel = { },
    onDismiss = { },
    message = annotatedString,
    title = Res.string.dialog_confirmation_title_attention,
    confirmLabel = Res.string.action_accept,
    cancelLabel = Res.string.action_cancel,
  )
}
