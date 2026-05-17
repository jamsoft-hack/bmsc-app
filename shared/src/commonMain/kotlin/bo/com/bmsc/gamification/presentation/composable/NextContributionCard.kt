package bo.com.bmsc.gamification.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bo.com.bmsc.app.theme.MentaOnBackground
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.StreakOrange
import bo.com.bmsc.core.composable.card.BaseElevatedCard
import bo.com.bmsc.gamification.domain.model.NextContribution

@Composable
fun NextContributionCard(
  nextContribution: NextContribution,
  onContributeClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  BaseElevatedCard(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    onClick = {}
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
      Text(
        text = "Próximo aporte",
        fontSize = 14.sp,
        color = MentaOnSurfaceVariant,
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "${nextContribution.dayOfWeek} ${nextContribution.date}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MentaOnSurface,
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "${nextContribution.currency} ${nextContribution.amount}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MentaOnSurface,
          )
        }

        Icon(
          imageVector = Icons.Filled.LocalFireDepartment,
          contentDescription = null,
          tint = StreakOrange,
          modifier = Modifier.size(32.dp)
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Tu ahorro: ${nextContribution.currency} ${nextContribution.totalSavings}",
        fontSize = 14.sp,
        color = MentaOnSurfaceVariant,
      )

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onContributeClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
          containerColor = MentaPrimary
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text(
          text = "Aportar ahora",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White,
        )
      }
    }
  }
}
