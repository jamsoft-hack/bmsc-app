package bo.com.bmsc.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens

enum class ToastVariant(
  var background: Color = Color.Unspecified,
  var border: Color = Color.Unspecified,
) {
  Success,
  Disabled,
  Warning,
  Danger,
}

@Composable
fun pickVariantColor(variant: ToastVariant): ToastVariant {
  when (variant) {
    ToastVariant.Success -> {
      variant.background = AppColors.reactive.toastSuccessContrast
      variant.border = AppColors.reactive.success
    }
    ToastVariant.Warning -> {
      variant.background = AppColors.reactive.toastWarningContrast
      variant.border = AppColors.reactive.warning
    }
    ToastVariant.Disabled -> {
      variant.background = AppColors.reactive.toastInfoContrast
      variant.border = AppColors.reactive.disabled
    }
    ToastVariant.Danger -> {
      variant.background = AppColors.reactive.toastDangerContrast
      variant.border = AppColors.reactive.danger
    }
  }

  return variant
}

@Composable
fun BarikToastAlert(
  message: String,
  variant: ToastVariant = ToastVariant.Success,
  onDismiss: () -> Unit,
) {
  val freshColor = pickVariantColor(variant)

  ElevatedCard(
    colors = CardDefaults.cardColors(
      contentColor = MaterialTheme.colorScheme.onBackground,
      containerColor = freshColor.background,
    ),
    modifier = Modifier.padding(horizontal = AppDimens.ContentPadding, vertical = 12.dp)
  ) {
    Row(
      Modifier.height(IntrinsicSize.Min).fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Box(Modifier.fillMaxHeight().width(8.dp).background(freshColor.border))

      Text(
        message,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
          .weight(2f)
          .padding(
            top = AppDimens.ContentPadding,
            bottom = AppDimens.ContentPadding,
            start = 16.dp,
            end = 4.dp,
          ),
      )

      IconButton(
        onClick = onDismiss
      ) {
//        Icon(
//          imageVector = BarikIcons.Close,
//          contentDescription = null,
//        )
      }
    }
  }
}
