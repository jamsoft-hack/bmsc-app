package bo.com.bmsc.core.helper

import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.generic_loading
import org.jetbrains.compose.resources.StringResource

object LoadingHelper {
  private var listener: (StringResource?) -> Unit = {}

  fun hideLoading() {
    listener(null)
  }

  fun showLoading(message: StringResource = Res.string.generic_loading) {
    listener(message)
  }

  fun setListener(listener: (StringResource?) -> Unit) {
    this.listener = listener
  }
}
