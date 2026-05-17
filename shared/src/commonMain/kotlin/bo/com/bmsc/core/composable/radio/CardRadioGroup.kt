package bo.com.bmsc.core.composable.radio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bo.com.bmsc.core.extension.format
import bo.com.bmsc.core.locale.formatDate
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

@Composable
fun CardRadioGroup(
  additionDays: Int,
  startDate: Instant,
  selectedDate: Instant,
  onDateSelected: (Instant) -> Unit,
) {
  val colorScheme = MaterialTheme.colorScheme

  FlowRow(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    (0 until additionDays).forEach { i ->
      val date = startDate.plus(i.days)
      val isSelected = date.format("ddMM") == selectedDate.format("ddMM")

      ElevatedCard(
        modifier = Modifier.widthIn(min = 105.dp).width(IntrinsicSize.Max),
        colors = CardDefaults.cardColors(
          containerColor = if (isSelected) colorScheme.primaryContainer else colorScheme.surface,
          contentColor = if (isSelected) Color.White else colorScheme.onBackground,
        ),
        onClick = {
          onDateSelected(date)
        }
      ) {
        Column(
          modifier = Modifier.padding(12.dp).fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = formatDate(date.toEpochMilliseconds(), format = "EEEE d")
              .replaceFirstChar { it.titlecase() },
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
          )
          Text(
            text = formatDate(date.toEpochMilliseconds(), format = "MMMM yyyy"),
            style = MaterialTheme.typography.bodyMedium,
          )
        }
      }
    }
  }
}
