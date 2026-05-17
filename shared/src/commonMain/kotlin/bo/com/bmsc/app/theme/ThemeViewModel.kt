package bo.com.bmsc.app.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.com.bmsc.app.enumerated.ThemeModeEnum
import bo.com.bmsc.core.di.koinInstance
import bo.com.bmsc.core.locale.AppLang
import bo.com.bmsc.core.locale.currentLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(
//  private val settingRepository: LocalSettingRepository = koinInstance.get()
) : ViewModel() {
  private val _themeMode = MutableStateFlow(ThemeModeEnum.SYSTEM)
  val themeMode: StateFlow<ThemeModeEnum> = _themeMode.onStart {
//    val name = settingRepository.getStringOrNull(AppSettingKey.THEME_MODE)

//    _themeMode.value = ThemeModeEnum.fromName(name) ?: ThemeModeEnum.SYSTEM
  }.onEach {
    ThemeHelper.changeTheme(_themeMode.value)
  }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000L),
    _themeMode.value
  )

  private val _appLocale = MutableStateFlow(value = currentLanguage())
  val appLocale = _appLocale
    .asSharedFlow()
    .stateIn(viewModelScope, SharingStarted.Eagerly, currentLanguage())

  companion object {
    val shared = ThemeViewModel()
  }

  fun setLocale(locale: AppLang) = viewModelScope.launch {
    _appLocale.value = locale
  }

  fun setThemeMode(mode: ThemeModeEnum) = viewModelScope.launch {
    _themeMode.value = mode
//    settingRepository.saveThemeSetting(mode)
  }
}
