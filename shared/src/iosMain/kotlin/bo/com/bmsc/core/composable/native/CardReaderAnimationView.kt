package bo.com.bmsc.core.composable.native

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.UIKitViewController
import bo.com.bmsc.LocalNativeViewFactory
import bo.com.bmsc.core.composable.native.toUIColor
import platform.UIKit.UIColor

@Composable
internal fun CardReaderNativeAnimationView() {
  val factory = LocalNativeViewFactory.current
  val color = MaterialTheme.colorScheme

  UIKitViewController(
    modifier = Modifier.fillMaxSize(),
    factory = {
      val controller = factory.createAnimation()

      controller.view.backgroundColor = color.background.toUIColor()

      controller
    }
  )
}

fun Color.toUIColor(): UIColor {
  return UIColor(
    red = red.toDouble(),
    green = green.toDouble(),
    blue = blue.toDouble(),
    alpha = alpha.toDouble(),
  )
}
