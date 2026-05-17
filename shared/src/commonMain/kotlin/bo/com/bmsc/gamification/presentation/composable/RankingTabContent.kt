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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import bo.com.bmsc.gamification.domain.model.RankingItem

@Composable
fun RankingTabContent(
  ranking: List<RankingItem>,
  onRankingItemClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.padding(horizontal = 20.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    ranking.forEach { item ->
      RankingItemCard(
        item = item,
        onClick = { onRankingItemClick(item.id) }
      )
    }
  }
}

@Composable
private fun RankingItemCard(
  item: RankingItem,
  onClick: () -> Unit,
) {
  BaseElevatedCard(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.elevatedCardColors(
      containerColor = if (item.isCurrentUser) {
        MentaPrimary.copy(alpha = 0.15f)
      } else {
        Color.White
      }
    ),
    onClick = onClick
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier.size(32.dp),
          contentAlignment = Alignment.Center
        ) {
          when (item.position) {
            1 -> {
              Icon(
                imageVector = Icons.Filled.EmojiEvents,
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(28.dp)
              )
            }
            2 -> {
              Icon(
                imageVector = Icons.Filled.EmojiEvents,
                contentDescription = null,
                tint = Color(0xFFC0C0C0),
                modifier = Modifier.size(28.dp)
              )
            }
            3 -> {
              Icon(
                imageVector = Icons.Filled.EmojiEvents,
                contentDescription = null,
                tint = Color(0xFFCD7F32),
                modifier = Modifier.size(28.dp)
              )
            }
            else -> {
              Text(
                text = "${item.position}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MentaOnSurfaceVariant,
              )
            }
          }
        }

        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
              if (item.isCurrentUser) {
                MentaPrimary
              } else {
                MentaPrimary.copy(alpha = 0.6f)
              }
            ),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = item.initial,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
          )
        }

        Column {
          Text(
            text = item.name,
            fontSize = 15.sp,
            fontWeight = if (item.isCurrentUser) FontWeight.Bold else FontWeight.Normal,
            color = MentaOnSurface,
          )

          if (item.isCurrentUser) {
            Text(
              text = "Vos",
              fontSize = 12.sp,
              color = MentaPrimary,
            )
          }
        }
      }

      Text(
        text = "${item.score} pts",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MentaOnSurface,
      )
    }
  }
}
