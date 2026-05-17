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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import bo.com.bmsc.gamification.domain.model.GamificationData
import bo.com.bmsc.gamification.presentation.GamificationTab
import bo.com.bmsc.gamification.presentation.GamificationViewModel
import bo.com.bmsc.gamification.presentation.composable.BadgesTabContent
import bo.com.bmsc.gamification.presentation.composable.NextContributionCard
import bo.com.bmsc.gamification.presentation.composable.RankingTabContent
import bo.com.bmsc.gamification.presentation.composable.StreakHeroSection
import bo.com.bmsc.gamification.presentation.composable.WeeklyProgressCard
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamificationScreen(
  viewModel: GamificationViewModel = koinViewModel(),
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

            TabRow(
              selectedTabIndex = if (selectedTab == GamificationTab.BADGES) 0 else 1,
              containerColor = Color.Transparent,
              contentColor = MentaPrimary,
              indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                  modifier = Modifier.tabIndicatorOffset(tabPositions[if (selectedTab == GamificationTab.BADGES) 0 else 1]),
                  color = MentaPrimary,
                  height = 3.dp,
                )
              },
              divider = {},
              modifier = Modifier.padding(horizontal = 20.dp)
            ) {
              Tab(
                selected = selectedTab == GamificationTab.BADGES,
                onClick = { viewModel.onTabClick(GamificationTab.BADGES) },
                selectedContentColor = MentaPrimary,
                unselectedContentColor = MentaOnSurfaceVariant,
              ) {
                Row(
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(vertical = 12.dp)
                ) {
                  Text(
                    text = "Insignias",
                    fontWeight = if (selectedTab == GamificationTab.BADGES) FontWeight.Bold else FontWeight.Normal,
                  )
                }
              }
              Tab(
                selected = selectedTab == GamificationTab.RANKING,
                onClick = { viewModel.onTabClick(GamificationTab.RANKING) },
                selectedContentColor = MentaPrimary,
                unselectedContentColor = MentaOnSurfaceVariant,
              ) {
                Row(
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(vertical = 12.dp)
                ) {
                  Text(
                    text = "Ranking",
                    fontWeight = if (selectedTab == GamificationTab.RANKING) FontWeight.Bold else FontWeight.Normal,
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
              GamificationTab.BADGES -> {
                BadgesTabContent(
                  badges = state.data.badges,
                  onBadgeClick = { viewModel.onBadgeClick(it) },
                )
              }
              GamificationTab.RANKING -> {
                RankingTabContent(
                  ranking = state.data.ranking,
                  onRankingItemClick = { viewModel.onRankingItemClick(it) },
                )
              }
            }

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
