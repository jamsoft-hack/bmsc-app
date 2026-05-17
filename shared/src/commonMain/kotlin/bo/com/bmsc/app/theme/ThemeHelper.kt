package bo.com.bmsc.app.theme

import bo.com.bmsc.app.enumerated.ThemeModeEnum

object ThemeHelper {
  private var previusValue: ThemeModeEnum = ThemeModeEnum.SYSTEM
  private var listener: (ThemeModeEnum) -> Unit = {}

  fun changeTheme(theme: ThemeModeEnum) {
    previusValue = theme

    listener(theme)
  }

  fun changeTemporarily(theme: ThemeModeEnum) {
    listener(theme)
  }

  fun restore() {
    listener(previusValue)
  }

  fun setListener(listener: (ThemeModeEnum) -> Unit) {
    this.listener = listener
  }
}