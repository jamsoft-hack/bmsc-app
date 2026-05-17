package bo.com.bmsc.gamification.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Button
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
import bo.com.bmsc.common.composable.PigState
import bo.com.bmsc.common.composable.SavingsPig
import bo.com.bmsc.gamification.domain.model.StreakInfo
import org.jetbrains.compose.resources.painterResource

@Composable
fun StreakHeroSection(
  streakInfo: StreakInfo,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    SavingsPig(
      modifier = Modifier.size(180.dp),
      state = PigState.Happy,
      savingsProgress = 0f,
    )
    Spacer(Modifier.height(16.dp))
    Text(
      text = "Tu cerdito está durmiendo",
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
    )
    Spacer(Modifier.height(8.dp))
    Text(
      text = "Empieza a ahorrar y verás cómo se llena de monedas",
      fontSize = 14.sp,
      color = Color.Gray,
    )
    Spacer(Modifier.height(24.dp))
    Button(onClick = {}) {
      Text("Empezar a ahorrar")
    }
  }
}
