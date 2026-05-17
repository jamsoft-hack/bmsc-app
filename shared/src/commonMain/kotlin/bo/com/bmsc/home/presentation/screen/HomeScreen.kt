package bo.com.bmsc.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurfaceContainerLow
import bo.com.bmsc.app.theme.PiggyGreen
import bo.com.bmsc.app.theme.PiggyGreenContainer
import bo.com.bmsc.app.theme.StreakOrange
import bo.com.bmsc.app.theme.StreakOrangeContainer
import bo.com.bmsc.assets.BMSCIcons
import bo.com.bmsc.assets.bmscicons.Coins
import bo.com.bmsc.core.composable.card.BaseElevatedCard
import bo.com.bmsc.core.common.ResultState
import bo.com.bmsc.home.domain.model.AccountInfo
import bo.com.bmsc.home.domain.model.AccountType
import bo.com.bmsc.home.domain.model.HomeData
import bo.com.bmsc.home.domain.model.PromoBanner
import bo.com.bmsc.home.presentation.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
  viewModel: HomeViewModel = koinViewModel(),
  onMenuClick: () -> Unit = {},
) {
  val homeState by viewModel.homeState.collectAsStateWithLifecycle()
  val isBalanceVisible by viewModel.isBalanceVisible.collectAsStateWithLifecycle()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MentaSurfaceContainerLow)
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
        )
      }
      is ResultState.Loading -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator(color = MentaPrimary)
        }
      }
      is ResultState.Idle -> {
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

@Composable
private fun HomeContent(
  homeData: HomeData,
  isBalanceVisible: Boolean,
  onMenuClick: () -> Unit,
  onToggleBalance: () -> Unit,
  onQuickActionClick: (String) -> Unit,
  onAccountClick: (String) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    HomeHeader(
      onMenuClick = onMenuClick,
      onToggleBalance = onToggleBalance,
      isBalanceVisible = isBalanceVisible,
    )

    BalanceSection(
      totalBalance = homeData.totalBalance,
      loanAmount = homeData.loanAmount,
      isBalanceVisible = isBalanceVisible,
    )

    QuickActionsSection(
      actions = homeData.quickActions,
      onActionClick = onQuickActionClick,
    )

    homeData.promoBanner?.let { banner ->
      PromoBannerCard(banner = banner)
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
private fun HomeHeader(
  onMenuClick: () -> Unit,
  onToggleBalance: () -> Unit,
  isBalanceVisible: Boolean,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(MentaPrimary)
      .padding(16.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = onMenuClick,
        modifier = Modifier.clip(CircleShape)
      ) {
        Icon(
          imageVector = Icons.Default.Menu,
          contentDescription = "Menu",
          tint = Color.White
        )
      }

      Text(
        text = "Mercantil Santa Cruz",
        color = Color.White,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )

      IconButton(
        onClick = onToggleBalance,
        modifier = Modifier.clip(CircleShape)
      ) {
        Icon(
          imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
          contentDescription = "Toggle balance",
          tint = Color.White
        )
      }
    }
  }
}

@Composable
private fun BalanceSection(
  totalBalance: Double,
  loanAmount: Double,
  isBalanceVisible: Boolean,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(MentaPrimary)
      .padding(horizontal = 20.dp, vertical = 20.dp)
  ) {
    Text(
      text = "Saldo total",
      color = Color.White.copy(alpha = 0.9f),
      style = MaterialTheme.typography.bodyMedium
    )

    Text(
      text = if (isBalanceVisible) formatAmount(totalBalance) else "••••••",
      color = Color.White,
      style = MaterialTheme.typography.displaySmall,
      fontWeight = FontWeight.Bold,
      fontSize = 40.sp
    )

    Text(
      text = "Préstamos: ${if (isBalanceVisible) formatAmount(loanAmount) else "••••••"}",
      color = Color.White.copy(alpha = 0.8f),
      style = MaterialTheme.typography.bodyMedium
    )
  }
}

@Composable
private fun QuickActionsSection(
  actions: List<bo.com.bmsc.home.domain.model.QuickAction>,
  onActionClick: (String) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      actions.forEach { action ->
        QuickActionButton(
          action = action,
          modifier = Modifier.weight(1f),
          onClick = { onActionClick(action.id) }
        )
      }
    }
  }
}

@Composable
private fun QuickActionButton(
  action: bo.com.bmsc.home.domain.model.QuickAction,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  Card(
    modifier = modifier
      .height(80.dp)
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(AppDimens.CardRadius),
    colors = CardDefaults.cardColors(
      containerColor = Color.White
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = action.icon,
        contentDescription = action.title,
        tint = MentaPrimary,
        modifier = Modifier.size(28.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = action.title,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun PromoBannerCard(banner: PromoBanner) {
  BaseElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp),
    colors = CardDefaults.elevatedCardColors(
      containerColor = PiggyGreenContainer
    ),
    onClick = {}
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
          color = PiggyGreen.copy(alpha = 0.3f),
          shape = RoundedCornerShape(4.dp)
        ) {
          Text(
            text = banner.label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = PiggyGreen,
            fontWeight = FontWeight.Bold
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = banner.title,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "${banner.subtitle} 🔥",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
      }
      Icon(
        imageVector = BMSCIcons.Coins,
        contentDescription = null,
        tint = PiggyGreen,
        modifier = Modifier.size(48.dp)
      )
    }
  }
}

@Composable
private fun AccountSection(
  accounts: List<AccountInfo>,
  onAccountClick: (String) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp)
  ) {
    Text(
      text = "Mis cuentas",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.padding(bottom = 12.dp)
    )

    accounts.forEach { account ->
      AccountCard(
        account = account,
        modifier = Modifier.padding(vertical = 4.dp),
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
  BaseElevatedCard(
    modifier = modifier.fillMaxWidth(),
    onClick = onClick
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
          .size(48.dp)
          .background(
            color = MentaSurfaceContainerLow,
            shape = CircleShape
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = account.icon,
          contentDescription = null,
          tint = MentaPrimary,
          modifier = Modifier.size(24.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = when (account.type) {
            AccountType.SAVINGS -> "Caja de ahorro"
            AccountType.CREDIT_CARD -> "Tarjeta de crédito"
          },
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.SemiBold
        )
        Text(
          text = "•••• ${account.number}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = when (account.type) {
            AccountType.SAVINGS -> "Disponible"
            AccountType.CREDIT_CARD -> "Por pagar"
          },
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
          text = account.balance,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MentaPrimary
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
      containerColor = StreakOrangeContainer
    ),
    onClick = {}
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = banner.title,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = banner.subtitle,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
      }
      Text(
        text = banner.interestRate,
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = StreakOrange
      )
    }
  }
}

private fun formatAmount(amount: Double): String {
  return "Bs ${String.format("%,.2f", amount)}"
}
