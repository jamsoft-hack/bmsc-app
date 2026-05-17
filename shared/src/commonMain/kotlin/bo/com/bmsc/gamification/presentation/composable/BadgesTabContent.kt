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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
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
import bo.com.bmsc.app.theme.MentaOnBackground
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.StreakOrange
import bo.com.bmsc.gamification.domain.model.Badge

@Composable
fun BadgesTabContent(
  badges: List<Badge>,
  onBadgeClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.padding(horizontal = 20.dp)
    ) {
      items(badges) { badge ->
        BadgeCard(
          badge = badge,
          onClick = { onBadgeClick(badge.id) }
        )
      }
    }
  }
}

@Composable
private fun BadgeCard(
  badge: Badge,
  onClick: () -> Unit,
) {
  Card(
    modifier = Modifier.width(160.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (badge.isUnlocked) {
        MentaPrimary.copy(alpha = 0.1f)
      } else {
        Color(0xFFF5F5F5)
      }
    ),
    onClick = onClick
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier.size(64.dp),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(
              if (badge.isUnlocked) {
                MentaPrimary.copy(alpha = 0.2f)
              } else {
                Color.Gray.copy(alpha = 0.1f)
              }
            ),
          contentAlignment = Alignment.Center
        ) {
          if (badge.isUnlocked) {
            Icon(
              imageVector = Icons.Filled.Star,
              contentDescription = null,
              tint = if (badge.isUnlocked) MentaPrimary else Color.Gray,
              modifier = Modifier.size(32.dp)
            )
          } else {
            Icon(
              imageVector = Icons.Filled.Lock,
              contentDescription = null,
              tint = Color.Gray,
              modifier = Modifier.size(24.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = badge.name,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MentaOnSurface,
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = badge.description,
        fontSize = 11.sp,
        color = MentaOnSurfaceVariant,
        minLines = 2
      )

      if (!badge.isUnlocked && badge.progress > 0f) {
        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
          progress = { badge.progress },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = MentaPrimary,
          trackColor = Color.Gray.copy(alpha = 0.2f),
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "${(badge.progress * 100).toInt()}%",
          fontSize = 11.sp,
          color = MentaOnSurfaceVariant,
        )
      }
    }
  }
}
