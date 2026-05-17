package bo.com.bmsc.gamification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.com.bmsc.core.common.ResultState
import bo.com.bmsc.gamification.data.datasource.GamificationMockDataSource
import bo.com.bmsc.gamification.domain.model.GamificationData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class GamificationTab {
  BADGES,
  RANKING,
}

class GamificationViewModel : ViewModel() {

  private val dataSource = GamificationMockDataSource()

  private val _gamificationState = MutableStateFlow<ResultState<GamificationData>>(ResultState.Idle)
  val gamificationState: StateFlow<ResultState<GamificationData>> = _gamificationState.asStateFlow()

  private val _selectedTab = MutableStateFlow(GamificationTab.BADGES)
  val selectedTab: StateFlow<GamificationTab> = _selectedTab.asStateFlow()

  init {
    loadGamificationData()
  }

  fun loadGamificationData() {
    viewModelScope.launch {
      _gamificationState.value = ResultState.Loading
      delay(500)
      _gamificationState.value = ResultState.Success(dataSource.getGamificationData())
    }
  }

  fun onTabClick(tab: GamificationTab) {
    _selectedTab.value = tab
  }

  fun onContributeClick() {
    // Handle contribution action
  }

  fun onBadgeClick(badgeId: String) {
    // Handle badge click
  }

  fun onRankingItemClick(userId: String) {
    // Handle ranking item click
  }
}
