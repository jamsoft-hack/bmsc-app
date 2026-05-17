package bo.com.bmsc.gamification.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.small_pig_mascot
import bo.com.bmsc.app.theme.MentaOnBackground
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.StreakOrange
import bo.com.bmsc.gamification.domain.model.StreakInfo
import org.jetbrains.compose.resources.painterResource

@Composable
fun StreakHeroSection(
  streakInfo: StreakInfo,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(
      contentAlignment = Alignment.TopEnd
    ) {
      Box(
        modifier = Modifier
          .offset(x = 30.dp, y = (-8).dp)
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFF3B5998))
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Text(
          text = streakInfo.status,
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier.size(140.dp),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            painter = painterResource(Res.drawable.small_pig_mascot),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(120.dp)
          )

          Icon(
            imageVector = Icons.Filled.LocalFireDepartment,
            contentDescription = null,
            tint = StreakOrange,
            modifier = Modifier
              .size(40.dp)
              .offset(x = 35.dp, y = 35.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "${streakInfo.days} días",
      style = androidx.compose.material3.MaterialTheme.typography.displaySmall,
      fontSize = 42.sp,
      fontWeight = FontWeight.Bold,
      color = MentaOnSurface,
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = "Racha activa · ${streakInfo.groupName}",
      fontSize = 14.sp,
      color = MentaOnSurfaceVariant,
    )
  }
}
