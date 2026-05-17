package bo.com.bmsc.core.helper

import androidx.compose.runtime.State
import androidx.compose.runtime.*
import kotlinx.cinterop.*
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSValue
import platform.UIKit.*

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun keyboardHeightAsState(): State<Int> {
  val keyboardHeight = remember { mutableStateOf(0) }

  DisposableEffect(Unit) {
    val center = NSNotificationCenter.defaultCenter

    val showObserver = center.addObserverForName(
      name = UIKeyboardWillShowNotification,
      `object` = null,
      queue = null
    ) { notification: NSNotification? ->
      val height = (notification?.userInfo?.get(UIKeyboardFrameEndUserInfoKey) as? NSValue)
        ?.CGRectValue?.size ?: 0.0
      keyboardHeight.value = height.toInt()
    }

    val hideObserver = center.addObserverForName(
      name = UIKeyboardWillHideNotification,
      `object` = null,
      queue = null
    ) {
      keyboardHeight.value = 0
    }

    onDispose {
      center.removeObserver(showObserver)
      center.removeObserver(hideObserver)
    }
  }

  return keyboardHeight
}