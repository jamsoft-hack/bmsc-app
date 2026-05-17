package bo.com.bmsc.home.data.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.CreditCard
import bo.com.bmsc.home.domain.model.AccountInfo
import bo.com.bmsc.home.domain.model.AccountType
import bo.com.bmsc.home.domain.model.HomeData
import bo.com.bmsc.home.domain.model.OfferBanner
import bo.com.bmsc.home.domain.model.PromoBanner
import bo.com.bmsc.home.domain.model.QuickAction

class HomeMockDataSource {

  fun getHomeData(): HomeData {
    return HomeData(
      totalBalance = 12458.30,
      loanAmount = 2100.00,
      quickActions = getQuickActions(),
      promoBanner = getPromoBanner(),
      accounts = getAccounts(),
      offerBanner = getOfferBanner(),
    )
  }

  private fun getQuickActions(): List<QuickAction> {
    return listOf(
      QuickAction(
        id = "services",
        title = "Servicios",
        icon = Icons.Outlined.Article,
      ),
      QuickAction(
        id = "qr",
        title = "QR",
        icon = Icons.Filled.QrCode,
      ),
      QuickAction(
        id = "transfer",
        title = "Transferir",
        icon = Icons.Filled.SwapHoriz,
      ),
    )
  }

  private fun getPromoBanner(): PromoBanner {
    return PromoBanner(
      label = "NUEVO",
      title = "Mi chanchito",
      subtitle = "Llevás 47 días de racha",
      streakDays = 47,
    )
  }

  private fun getAccounts(): List<AccountInfo> {
    return listOf(
      AccountInfo(
        id = "savings_1",
        type = AccountType.SAVINGS,
        number = "5525",
        balance = "Bs 10.358,30",
        availableAmount = 10358.30,
        icon = Icons.Outlined.AccountBalance,
      ),
      AccountInfo(
        id = "credit_1",
        type = AccountType.CREDIT_CARD,
        number = "6266",
        balance = "Bs 2.100,00",
        availableAmount = 2100.00,
        icon = Icons.Outlined.CreditCard,
      ),
    )
  }

  private fun getOfferBanner(): OfferBanner {
    return OfferBanner(
      title = "Cuenta express",
      subtitle = "Tu dinero rinde hasta",
      interestRate = "4,60%",
    )
  }
}
