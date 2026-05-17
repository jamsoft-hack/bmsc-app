package bo.com.bmsc.core.composable.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.core.extension.topBorder
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class BoxVariant(
  var background: Color = Color.Unspecified,
  var border: Color = Color.Unspecified,
  var title: Color = Color.Unspecified,
) {
  SUCCESS,
  DEFAULT
}

@Composable
private fun getColorByVariant(variant: BoxVariant): BoxVariant {
  when (variant) {
    BoxVariant.SUCCESS -> {
      variant.background = AppColors.reactive.toastSuccessContrast
      variant.border = AppColors.reactive.successMid
      variant.title = AppColors.reactive.successMid
    }

    BoxVariant.DEFAULT -> {
      variant.background = AppColors.reactive.toastInfoContrast
      variant.border = AppColors.reactive.clearer
      variant.title = AppColors.reactive.textSubtle
    }
  }

  return variant
}

@Composable
fun BaseBox(
  title: StringResource,
  message: AnnotatedString,
  modifier: Modifier = Modifier,
  variant: BoxVariant = BoxVariant.DEFAULT,
  content: @Composable () -> Unit
) {
  val color = getColorByVariant(variant)

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier =
      modifier.fillMaxWidth()
        .background(color.background, RectangleShape)
        .widthIn(max = AppDimens.MaxContentWidth)
        .topBorder(2.dp, color.border)
        .padding(AppDimens.ContentPadding)
  ) {
    Text(
      text = stringResource(title),
      color = color.title,
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold,
      style = MaterialTheme.typography.titleLarge,
    )
    Text(
      text = message,
      modifier = Modifier.widthIn(max = AppDimens.MaxContentWidth),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )

    content()
  }
}