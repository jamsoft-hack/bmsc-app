package bo.com.bmsc.home.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.small_pig_mascot
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.core.navigation.NavigationHelper
import bo.com.bmsc.app.theme.MentaOnBackground
import bo.com.bmsc.app.theme.MentaOnPrimary
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaPrimaryContainer
import bo.com.bmsc.app.theme.MentaSecondaryContainer
import bo.com.bmsc.app.theme.MentaSurface
import bo.com.bmsc.app.theme.MentaSurfaceContainerHighest
import bo.com.bmsc.app.theme.MentaTertiary
import bo.com.bmsc.app.theme.MentaTertiaryContainer
import bo.com.bmsc.app.theme.PiggyGreen
import bo.com.bmsc.app.theme.PiggyGreenContainer
import bo.com.bmsc.app.theme.StreakOrange
import bo.com.bmsc.app.theme.StreakOrangeContainer
import bo.com.bmsc.Route
import bo.com.bmsc.assets.BMSCVectors
import bo.com.bmsc.assets.bmscvectors.Card
import bo.com.bmsc.assets.bmscvectors.SmallPigMascot
import bo.com.bmsc.core.composable.card.BaseElevatedCard
import bo.com.bmsc.core.common.ResultState
import bo.com.bmsc.home.domain.model.AccountInfo
import bo.com.bmsc.home.domain.model.AccountType
import bo.com.bmsc.home.domain.model.HomeData
import bo.com.bmsc.home.domain.model.PromoBanner
import bo.com.bmsc.home.presentation.HomeViewModel
import bo.com.bmsc.home.presentation.composable.QuickActionsSection
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
  viewModel: HomeViewModel = koinViewModel(),
  navigationHelper: NavigationHelper = koinInject(),
  onMenuClick: () -> Unit = {},
  onNavItemClick: (String) -> Unit = {},
) {
  val homeState by viewModel.homeState.collectAsStateWithLifecycle()
  val isBalanceVisible by viewModel.isBalanceVisible.collectAsStateWithLifecycle()
  var selectedNavItem by rememberSaveable { mutableStateOf("home") }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MentaSurface)
  ) {
    when (val state = homeState) {
      is ResultState.Success -> {
        HomeContent(
          homeData = state.data,
          isBalanceVisible = isBalanceVisible,
          onMenuClick = onMenuClick,
          onToggleBalance = { viewModel.toggleBalanceVisibility() },
          onQuickActionClick = { viewModel.onQuickActionClick(it) },
          onAccountClick = { viewModel.onAccountClick(it) },
          onGamificationClick = { navigationHelper.navigateTo(Route.Gamification) },
          bottomNavPadding = 80.dp,
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
  homeData: HomeData,
  isBalanceVisible: Boolean,
  onMenuClick: () -> Unit,
  onToggleBalance: () -> Unit,
  onQuickActionClick: (String) -> Unit,
  onAccountClick: (String) -> Unit,
  onGamificationClick: () -> Unit,
  bottomNavPadding: Dp,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(bottom = bottomNavPadding)
  ) {
    Column(
      modifier = Modifier
        .background(
          brush = Brush.linearGradient(
            colors = listOf(
              Color(201, 224, 199),
              Color(221, 237, 220)
            )
          ),
          shape = RoundedCornerShape(
            bottomStart = 24.dp,
            bottomEnd = 24.dp
          )
        ).padding(horizontal = 20.dp)
    ) {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "Mercantil Santa Cruz",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
        },
        navigationIcon = {
          IconButton(
            onClick = onMenuClick,
            colors = IconButtonDefaults.iconButtonColors(
              containerColor = Color.White
            ),
            shape = CircleShape,
          ) {
            Icon(
              imageVector = Icons.Filled.Menu,
              contentDescription = "Menu",
            )
          }
        },
        actions = {
          IconButton(
            onClick = onToggleBalance,
            colors = IconButtonDefaults.iconButtonColors(
              containerColor = Color.White
            ),
            shape = CircleShape,
          ) {
            Icon(
              imageVector = if (isBalanceVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
              contentDescription = "Toggle balance",
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = Color.Transparent,
        )
      )

      Spacer(modifier = Modifier.height(16.dp))

      BalanceCard(
        totalBalance = homeData.totalBalance,
        loanAmount = homeData.loanAmount,
        isBalanceVisible = isBalanceVisible,
      )

      QuickActionsSection(
        actions = homeData.quickActions,
        onActionClick = onQuickActionClick,
      )
    }

    homeData.promoBanner?.let { banner ->
      Spacer(modifier = Modifier.height(12.dp))
      PromoBannerCard(
        banner = banner,
        onClick = onGamificationClick
      )
    }

    AccountSection(
      accounts = homeData.accounts,
      onAccountClick = onAccountClick,
    )

    homeData.offerBanner?.let { banner ->
      OfferBannerCard(banner = banner)
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}

@Composable
private fun BalanceCard(
  totalBalance: Double,
  loanAmount: Double,
  isBalanceVisible: Boolean,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = Modifier.fillMaxSize()
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      Text(
        text = "Saldo total",
        style = MaterialTheme.typography.bodyMedium
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = if (isBalanceVisible) formatAmount(totalBalance) else "••••••",
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Préstamos: " + if (isBalanceVisible) formatAmount(loanAmount) else "••••••",
        style = MaterialTheme.typography.labelSmall,
        color = Color(138, 139, 154)
      )
    }
  }
}

@Composable
private fun PromoBannerCard(
  banner: PromoBanner,
  onClick: () -> Unit = {}
) {
  BaseElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp),
    colors = CardDefaults.elevatedCardColors(
      containerColor = Color(0xFF6D63FF)
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
      Column(modifier = Modifier.weight(1f)) {
        Surface(
          color = Color.White.copy(alpha = 0.2f),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = banner.label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = banner.title,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = "Llevás",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
          )
          Text(
            text = "${banner.streakDays}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
          Text(
            text = "días",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "🔥",
            style = MaterialTheme.typography.bodyMedium
          )
        }
      }

      Image(
        painter = painterResource(Res.drawable.small_pig_mascot),
        contentDescription = null,
        modifier = Modifier
          .offset(15.dp, 15.dp)
          .size(90.dp)
      )
    }
  }
}

@Composable
private fun AccountSection(
  accounts: List<AccountInfo>,
  onAccountClick: (String) -> Unit,
) {
  Card(
    modifier = Modifier.fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 16.dp),
  ) {
    accounts.forEachIndexed { index, account ->
      if(index != 0) {
        HorizontalDivider(
          modifier = Modifier.height(1.dp),
          color = Color(210, 226, 208)
        )
      }

      AccountCard(
        account = account,
        onClick = { onAccountClick(account.id) }
      )
    }
  }
}

@Composable
private fun AccountCard(
  account: AccountInfo,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = Color.White
    ),
    shape = RectangleShape,
    onClick = onClick,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(52.dp)
          .background(
            color = when (account.type) {
              AccountType.SAVINGS -> MentaPrimaryContainer
              AccountType.CREDIT_CARD -> MentaTertiaryContainer
            },
            shape = RoundedCornerShape(14.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = account.icon,
          contentDescription = null,
          tint = when (account.type) {
            AccountType.SAVINGS -> MentaPrimary
            AccountType.CREDIT_CARD -> MentaTertiary
          },
          modifier = Modifier.size(26.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = when (account.type) {
            AccountType.SAVINGS -> "Caja de ahorro"
            AccountType.CREDIT_CARD -> "Tarjeta de crédito"
          },
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.SemiBold,
          color = MentaOnSurface
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "•••• ${account.number}",
          style = MaterialTheme.typography.bodySmall,
          color = MentaOnSurfaceVariant
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = when (account.type) {
            AccountType.SAVINGS -> "Disponible"
            AccountType.CREDIT_CARD -> "Por pagar"
          },
          style = MaterialTheme.typography.labelSmall,
          color = MentaOnSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = account.balance,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MentaOnSurface
        )
      }
    }
  }
}

@Composable
private fun OfferBannerCard(banner: bo.com.bmsc.home.domain.model.OfferBanner) {
  BaseElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp),
    colors = CardDefaults.elevatedCardColors(
      containerColor = Color(255, 227, 138)
    ),
    onClick = {}
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = banner.title,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MentaOnSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = banner.subtitle,
          style = MaterialTheme.typography.bodyMedium,
          color = MentaOnSurfaceVariant
        )
      }

      Text(
        text = banner.interestRate,
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = MentaOnSurface
      )
    }
  }
}

private fun formatAmount(amount: Double): String {
  val formatted = amount.toString()
  val parts = formatted.split(".")
  val integerPart = parts[0]
  val decimalPart = if (parts.size > 1) parts[1].take(2).padEnd(2, '0') else "00"

  val withCommas = buildString {
    var count = 0
    for (char in integerPart.reversed()) {
      if (count > 0 && count % 3 == 0) {
        append(',')
      }
      append(char)
      count++
    }
  }.reversed()

  return "Bs $withCommas.$decimalPart"
}
