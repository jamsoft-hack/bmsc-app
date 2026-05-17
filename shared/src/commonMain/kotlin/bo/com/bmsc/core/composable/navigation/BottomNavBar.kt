package bo.com.bmsc.core.composable.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurface

data class NavItem(
  val id: String,
  val icon: ImageVector,
  val label: String,
)

@Composable
fun BottomNavBar(
  items: List<NavItem>,
  selectedItem: String,
  onItemClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier,
    color = MentaSurface,
    shadowElevation = 8.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .height(64.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      items.forEach { item ->
        NavBarItem(
          item = item,
          isSelected = item.id == selectedItem,
          onClick = { onItemClick(item.id) },
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
private fun NavBarItem(
  item: NavItem,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val scale by animateFloatAsState(
    targetValue = if (isSelected) 1f else 0.9f,
    animationSpec = tween(durationMillis = 200),
    label = "navScale"
  )

  val interactionSource = remember { MutableInteractionSource() }

  Box(
    modifier = modifier
      .clickable(
        onClick = onClick,
        interactionSource = interactionSource,
        indication = null
      ),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier.scale(scale),
        contentAlignment = Alignment.Center
      ) {
        if (isSelected) {
          SelectedNavItem(
            icon = item.icon,
            label = item.label
          )
        } else {
          UnselectedNavItem(
            icon = item.icon,
            label = item.label
          )
        }
      }
    }
  }
}

@Composable
private fun SelectedNavItem(
  icon: ImageVector,
  label: String,
) {
  Box(
    modifier = Modifier
      .width(80.dp)
      .height(48.dp),
    contentAlignment = Alignment.Center
  ) {
    Surface(
      color = MentaPrimary.copy(alpha = 0.15f),
      shape = RoundedCornerShape(16.dp)
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = icon,
          contentDescription = label,
          tint = MentaPrimary,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = label,
          color = MentaPrimary,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

@Composable
private fun UnselectedNavItem(
  icon: ImageVector,
  label: String,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = MentaOnSurfaceVariant,
      modifier = Modifier.size(24.dp)
    )
    Text(
      text = label,
      color = MentaOnSurfaceVariant,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium
    )
  }
}
