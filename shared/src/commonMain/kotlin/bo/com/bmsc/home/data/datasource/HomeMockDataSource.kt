package bo.com.bmsc.home.data.datasource

import bo.com.bmsc.assets.BMSCIcons
import bo.com.bmsc.assets.bmscicons.BarikNfc
import bo.com.bmsc.assets.bmscicons.ChangeCard
import bo.com.bmsc.assets.bmscicons.Coins
import bo.com.bmsc.assets.bmscicons.Document
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
        icon = BMSCIcons.Document,
      ),
      QuickAction(
        id = "qr",
        title = "QR",
        icon = BMSCIcons.BarikNfc,
      ),
      QuickAction(
        id = "transfer",
        title = "Transferir",
        icon = BMSCIcons.Coins,
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
        icon = BMSCIcons.Coins,
      ),
      AccountInfo(
        id = "credit_1",
        type = AccountType.CREDIT_CARD,
        number = "6266",
        balance = "Bs 2.100,00",
        availableAmount = 2100.00,
        icon = BMSCIcons.ChangeCard,
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
