package bo.com.bmsc.home.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import bo.com.bmsc.assets.BMSCIcons

data class HomeData(
  val totalBalance: Double,
  val loanAmount: Double,
  val quickActions: List<QuickAction>,
  val promoBanner: PromoBanner?,
  val accounts: List<AccountInfo>,
  val offerBanner: OfferBanner?,
)

data class QuickAction(
  val id: String,
  val title: String,
  val icon: ImageVector,
)

data class PromoBanner(
  val label: String,
  val title: String,
  val subtitle: String,
  val streakDays: Int,
)

data class AccountInfo(
  val id: String,
  val type: AccountType,
  val number: String,
  val balance: String,
  val availableAmount: Double,
  val icon: ImageVector,
)

enum class AccountType {
  SAVINGS,
  CREDIT_CARD,
}

data class OfferBanner(
  val title: String,
  val subtitle: String,
  val interestRate: String,
)
