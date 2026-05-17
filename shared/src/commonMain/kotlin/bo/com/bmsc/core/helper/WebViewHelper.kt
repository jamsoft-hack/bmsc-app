package bo.com.bmsc.core.helper

import bo.com.bmsc.core.domain.CustomException
import org.jetbrains.compose.resources.StringResource

data class WebViewConfig(
  val url: String,
  val title: StringResource,
  val postData: String? = null,
  val closeConfirmLabel: StringResource? = null,
  val onClose: (error: CustomException?) -> Unit = {},
  val watchedParams: List<String> = emptyList(),
  val smallDialog: Boolean = false,
  val onCaughtParams: (Map<String, String>) -> Unit = {},
  val onLoaded: () -> Unit = {},
)

object WebViewHelper {
  var config: WebViewConfig? = null

  private var listener: (WebViewConfig?) -> Unit = {}
  private var onForceClose: () -> Unit = {}

  var hasBackPageListener: (Boolean) -> Unit = {}

  fun openWebDialog(properties: WebViewConfig?) {
    this.config = properties

    listener(properties)
  }

  fun forceClose() {
    onForceClose()
  }

  fun onChangeHasBackPage(canGoBack: Boolean) {
    hasBackPageListener(canGoBack)
  }

  fun close() {
    openWebDialog(null)
  }

  fun onForceClose(handler: () -> Unit) {
    onForceClose = handler
  }

  fun setListener(next: (WebViewConfig?) -> Unit) {
    listener = next
  }
}

fun openInAppWeb(
  url: String,
  title: StringResource,
  postData: String? = null,
  closeConfirmLabel: StringResource? = null,
  onClose: (error: CustomException?) -> Unit = {},
  onLoaded: () -> Unit = {},
) {
  WebViewHelper.openWebDialog(
    properties = WebViewConfig(
      url = url,
      postData = postData,
      title = title,
      closeConfirmLabel = closeConfirmLabel,
      onClose = onClose,
      onLoaded = onLoaded,
    )
  )
}

fun openInAppWeb(
  url: String,
  title: StringResource,
  smallDialog: Boolean = false,
  watchedParams: List<String> = emptyList(),
  onClose: (error: CustomException?) -> Unit = {},
  onLoaded: () -> Unit = {},
  onCaughtParams: (Map<String, String>) -> Unit = {},
) {
  WebViewHelper.openWebDialog(
    properties = WebViewConfig(
      url = url,
      title = title,
      smallDialog = smallDialog,
      watchedParams = watchedParams,
      onLoaded = onLoaded,
      onClose = onClose,
      onCaughtParams = onCaughtParams,
    )
  )
}
