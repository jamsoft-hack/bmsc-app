package bo.com.bmsc.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.com.bmsc.core.common.ResultState
import bo.com.bmsc.home.data.datasource.HomeMockDataSource
import bo.com.bmsc.home.domain.model.HomeData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

  private val dataSource = HomeMockDataSource()

  private val _homeState = MutableStateFlow<ResultState<HomeData>>(ResultState.Idle)
  val homeState: StateFlow<ResultState<HomeData>> = _homeState.asStateFlow()

  private val _isBalanceVisible = MutableStateFlow(true)
  val isBalanceVisible: StateFlow<Boolean> = _isBalanceVisible.asStateFlow()

  init {
    loadHomeData()
  }

  fun loadHomeData() {
    viewModelScope.launch {
      _homeState.value = ResultState.Loading
      delay(500)
      _homeState.value = ResultState.Success(dataSource.getHomeData())
    }
  }

  fun toggleBalanceVisibility() {
    _isBalanceVisible.value = !_isBalanceVisible.value
  }

  fun onQuickActionClick(actionId: String) {
    // Handle quick action clicks
  }

  fun onAccountClick(accountId: String) {
    // Handle account clicks
  }
}
