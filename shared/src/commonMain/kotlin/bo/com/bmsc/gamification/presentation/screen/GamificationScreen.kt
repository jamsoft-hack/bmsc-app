package bo.com.bmsc.gamification.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.small_pig_mascot
import bo.com.bmsc.app.theme.MentaOnBackground
import bo.com.bmsc.app.theme.MentaOnPrimary
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurface
import bo.com.bmsc.core.common.ResultState
import bo.com.bmsc.core.navigation.NavigationHelper
import bo.com.bmsc.gamification.domain.model.GamificationData
import bo.com.bmsc.gamification.presentation.GamificationTab
import bo.com.bmsc.gamification.presentation.GamificationViewModel
import bo.com.bmsc.gamification.presentation.composable.BadgesTabContent
import bo.com.bmsc.gamification.presentation.composable.NextContributionCard
import bo.com.bmsc.gamification.presentation.composable.StreakHeroSection
import bo.com.bmsc.gamification.presentation.composable.WeeklyProgressCard
import org.koin.compose.viewmodel.koinViewModel

data class MenuButton(
  val label: String,
  val icon: @Composable () -> Unit
)

@Composable
fun MenuButtonCard(
  button: MenuButton,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    onClick = onClick,
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    color = Color.White,
  ) {
    Column(
      modifier = Modifier
        .padding(16.dp)
        .width(100.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .background( MentaPrimary.copy(alpha = 0.1f), CircleShape ),
        contentAlignment = Alignment.Center
      ) {
        CompositionLocalProvider(
          LocalContentColor provides  MentaPrimary
        ) {
          button.icon()
        }
      }
      Text(
        text = button.label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
      )
    }
  }
}

@Composable
fun MenuButtonGrid(
  buttons: List<MenuButton>,
  onButtonSelected: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  FlowRow(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    buttons.forEachIndexed { index, button ->
      MenuButtonCard(
        button = button,
        modifier = Modifier.weight(1f),
        onClick = { onButtonSelected(index) }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamificationScreen(
  viewModel: GamificationViewModel = koinViewModel(),
  navigationHelper: NavigationHelper = org.koin.compose.koinInject(),
  onBackClick: () -> Unit = {},
  onSettingsClick: () -> Unit = {},
  bottomNavPadding: Dp = 80.dp,
) {
  val gamificationState by viewModel.gamificationState.collectAsStateWithLifecycle()
  val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MentaSurface)
  ) {
    when (val state = gamificationState) {
      is ResultState.Success -> {
        Column(
          modifier = Modifier.fillMaxSize()
        ) {
          TopAppBar(
            title = {
              Text(
                text = "Mi chanchito",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
              )
            },
            navigationIcon = {
              IconButton(onClick = onBackClick) {
                Icon(
                  imageVector = Icons.Filled.ArrowBack,
                  contentDescription = "Back",
                )
              }
            },
            actions = {
              IconButton(onClick = onSettingsClick) {
                Icon(
                  imageVector = Icons.Filled.Settings,
                  contentDescription = "Settings",
                )
              }
            },
            colors = TopAppBarDefaults.topAppBarColors(
              containerColor = Color.Transparent,
            ),
          )

          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState())
              .padding(bottom = bottomNavPadding)
          ) {
            StreakHeroSection(
              streakInfo = state.data.streakInfo,
              modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            WeeklyProgressCard(
              weeklyProgress = state.data.weeklyProgress,
              modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            NextContributionCard(
              nextContribution = state.data.nextContribution,
              onContributeClick = { viewModel.onContributeClick() },
              modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            MenuButtonGrid(
              buttons = listOf(
                MenuButton("Crear racha") { Icon(Icons.Outlined.Group, contentDescription = null) },
                MenuButton("Insignias") { Icon(Icons.Outlined.Star, contentDescription = null) },
                MenuButton("Ranking") { Icon(Icons.Outlined.EmojiEvents, contentDescription = null) },
                MenuButton("Historial") { Icon(Icons.Outlined.History, contentDescription = null) }
              ),
              onButtonSelected = { index ->
                when (index) {
                  0 -> navigationHelper.navigateTo(bo.com.bmsc.Route.RachaOnboarding)
                  else -> {} // TODO: Other navigation
                }
              },
              modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
          }
        }
      }
      is ResultState.Loading, is ResultState.Idle -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator(color = MentaPrimary)
        }
      }
      is ResultState.Error -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Error al cargar los datos",
            color = MaterialTheme.colorScheme.error
          )
        }
      }
    }
  }
}
