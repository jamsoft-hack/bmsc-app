package bo.com.bmsc.gamification.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.StreakOrange
import bo.com.bmsc.core.composable.card.BaseElevatedCard
import bo.com.bmsc.gamification.domain.model.ParticipantStatus
import bo.com.bmsc.gamification.domain.model.WeeklyProgress

@Composable
fun WeeklyProgressCard(
  weeklyProgress: WeeklyProgress,
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
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = weeklyProgress.title,
          fontSize = 14.sp,
          color = MentaOnSurfaceVariant,
        )

        Text(
          text = "${weeklyProgress.completed} / ${weeklyProgress.total} cumplidos",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = MentaPrimary,
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      LinearProgressIndicator(
        progress = { weeklyProgress.completed.toFloat() / weeklyProgress.total.toFloat() },
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp)),
        color = MentaPrimary,
        trackColor = MentaPrimary.copy(alpha = 0.2f),
      )

      Spacer(modifier = Modifier.height(20.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        weeklyProgress.participants.forEach { participant ->
          ParticipantItem(participant = participant)
        }
      }
    }
  }
}

@Composable
private fun ParticipantItem(
  participant: bo.com.bmsc.gamification.domain.model.Participant,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    Box(
      modifier = Modifier.size(48.dp),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(
            if (participant.status == ParticipantStatus.COMPLETED) {
              MentaPrimary
            } else {
              Color(0xFFB8860B)
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = participant.initial,
          color = Color.White,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
        )
      }

      Box(
        modifier = Modifier
          .size(20.dp)
          .clip(CircleShape)
          .background(Color.White)
          .align(Alignment.BottomEnd),
        contentAlignment = Alignment.Center
      ) {
        when (participant.status) {
          ParticipantStatus.COMPLETED -> {
            Icon(
              imageVector = Icons.Filled.Check,
              contentDescription = null,
              tint = MentaPrimary,
              modifier = Modifier.size(14.dp)
            )
          }
          ParticipantStatus.PENDING -> {
            Icon(
              imageVector = Icons.Filled.AccessTime,
              contentDescription = null,
              tint = StreakOrange,
              modifier = Modifier.size(12.dp)
            )
          }
        }
      }
    }

    Text(
      text = participant.name,
      fontSize = 12.sp,
      color = MentaOnSurfaceVariant,
    )
  }
}
