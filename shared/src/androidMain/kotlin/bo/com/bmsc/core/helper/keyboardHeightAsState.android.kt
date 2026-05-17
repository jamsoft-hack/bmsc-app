package bo.com.bmsc.core.helper

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

@Composable
actual fun keyboardHeightAsState(): State<Int> {
  val keyboardHeight = remember { mutableIntStateOf(0) }
  val view = LocalView.current

  DisposableEffect(view) {
    val listener = ViewTreeObserver.OnGlobalLayoutListener {
      val rect = Rect()
      view.getWindowVisibleDisplayFrame(rect)
      val screenHeight = view.rootView.height
      val keypadHeight = screenHeight - rect.bottom
      keyboardHeight.intValue = if (keypadHeight > screenHeight * 0.15) keypadHeight else 0
    }

    view.viewTreeObserver.addOnGlobalLayoutListener(listener)
    onDispose {
      view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }
  }

  return keyboardHeight
}