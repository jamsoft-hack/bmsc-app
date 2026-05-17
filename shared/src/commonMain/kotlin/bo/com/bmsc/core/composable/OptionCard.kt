package bo.com.bmsc.core.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.core.composable.card.ColumnElevatedCard
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OptionCard(
  title: StringResource? = null,
  description: StringResource,
  image: DrawableResource,
  imageWidth: Dp = 56.dp,
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {},
) {
  val colors = AppColors.reactive

  ColumnElevatedCard(
    onClick = onClick,
    modifier = Modifier.fillMaxWidth(),
  ) {
    Box(
      modifier = modifier
        .drawBehind {
          val radius = 180.dp.toPx()

          drawCircle(
            color = colors.disabled,
            radius = radius,
            center = Offset(x = -(radius * .55f), this.center.y)
          )
        }
    ) {
      Row(
        Modifier.align(Alignment.Center).padding(start = 110.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        Column(
          modifier = Modifier.weight(2f),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          title?.let {
            Text(
              text = stringResource(title),
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary,
            )

            HorizontalDivider(Modifier.fillMaxWidth())
          }

          Text(
            text = stringResource(description),
            style = if (title == null) MaterialTheme.typography.bodyLarge
            else MaterialTheme.typography.bodyMedium,
            lineHeight = 24.sp,
          )
        }

        Text("Icon")
//        Icon(
//          imageVector = BarikIcons.KeyboardArrowRight,
//          contentDescription = null,
//          tint = AppColors.reactive.clearer,
//        )
      }

      Image(
        painter = painterResource(image),
        contentDescription = null,
        modifier = Modifier
          .offset(x = 12.dp)
          .width(imageWidth)
          .graphicsLayer { rotationZ = 30f },
        contentScale = ContentScale.FillWidth,
      )
    }
  }
}
