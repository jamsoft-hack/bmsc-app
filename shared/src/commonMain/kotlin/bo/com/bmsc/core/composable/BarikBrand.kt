package bo.com.bmsc.core.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

enum class BarikBrandVariant {
  Normal,
  HighContrast,
  Dark,
}

@Composable
fun BarikBrand(
  modifier: Modifier = Modifier,
  variant: BarikBrandVariant = BarikBrandVariant.Normal,
) {
  val annotatedString = buildAnnotatedString {
    append("ba")
    withStyle(
      style = SpanStyle(
        color = when (variant) {
          BarikBrandVariant.HighContrast, BarikBrandVariant.Dark -> Color.White
          else -> MaterialTheme.colorScheme.primary
        },
        fontWeight = FontWeight.ExtraBold
      )
    ) {
      append("rik")
    }
  }

  Text(
    text = annotatedString,
    style = MaterialTheme.typography.headlineMedium,
    color = when (variant) {
      BarikBrandVariant.HighContrast -> Color.Black
      BarikBrandVariant.Dark -> Color.White
      else -> MaterialTheme.colorScheme.onSurfaceVariant
    },
    modifier = modifier,
  )
}
